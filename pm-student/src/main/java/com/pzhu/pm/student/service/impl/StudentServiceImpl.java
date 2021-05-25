package com.pzhu.pm.student.service.impl;

import cn.hutool.core.date.DateTime;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import com.pzhu.pm.student.config.ConstantPropertiesUtil;
import com.pzhu.pm.student.mapper.CGroupMapper;
import com.pzhu.pm.student.mapper.SMemberMapper;
import com.pzhu.pm.student.mapper.StudentCourseMapper;
import com.pzhu.pm.student.mapper.StudentMapper;
import com.pzhu.pm.student.pojo.*;
import com.pzhu.pm.student.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author QYstart
 * @since 2021-04-04
 */
@Service
@Slf4j
public class StudentServiceImpl implements StudentService {
    private static final String[] TYPESTR = {"doc", "docx"};
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private SMemberMapper sMemberMapper;

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Autowired
    private CGroupMapper cGroupMapper;

    @Autowired
    private ThreadPoolExecutor uploadExecutorPool;

    @Override
    public SMember login(String account, String password) {

        SMember smember = new SMember();
        smember.setAccount(account);
        smember.setPassword(password);
        SMember result = sMemberMapper.selectOne(smember);
        if (result != null) {
            result.setPassword("");
        }
        return result;
    }

    @Override
    public List<StudentInfoVO> selectInfo(String studentNo) {
        List<StudentInfoVO> studentInfoVO = studentMapper.selectInfo(studentNo);
        return studentInfoVO;
    }

    @Override
    public Integer toSign(String studentNo, Integer courseNo) {

        //获得待签到学生
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentNo(studentNo);
        studentCourse.setCourseNo(courseNo);
        StudentCourse result = studentCourseMapper.selectOne(studentCourse);

        Integer signCount = result.getSignCount();
        if (result != null) {
            //更新签到次数
            studentCourse.setId(result.getId());
            studentCourse.setSignCount(++signCount);
            studentCourseMapper.updateByPrimaryKeySelective(studentCourse);
            return signCount;
        }

        return -1;
    }

    @Override
    public Student getSudentById(String studentNo) {
        Student student = new Student();

        student.setStudentNo(studentNo);
        return studentMapper.selectOne(student);
    }

    @Override
    public CGroup getGroupByStu(String studentNo, Integer courseNo) {

        CGroup cGroup = new CGroup();
        cGroup.setStudentNo(studentNo);
        cGroup.setCourseNo(courseNo);
        return cGroupMapper.selectOne(cGroup);
    }

    @Override
    public CGroup insertLeadGroup(String studentNo, Integer courseNo) {

        CGroup cGroup = new CGroup();
        cGroup.setStudentNo(studentNo);
        cGroup.setCourseNo(courseNo);
        cGroup.setLead((short) 2);
        cGroupMapper.insertSelective(cGroup);
        return cGroup;
    }

    @Override
    public CGroup insertGroup(String studentNo, Integer courseNo, Integer group) {

        CGroup cGroup = new CGroup();
        cGroup.setStudentNo(studentNo);
        cGroup.setCourseNo(courseNo);
        cGroup.setGroup(group);
        cGroup.setLead((short) 0);
        cGroupMapper.insertSelective(cGroup);

        return cGroup;
    }

    @Override
    public String upload(String studentNo, Integer courseNo, MultipartFile file) {

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setCourseNo(courseNo);
        studentCourse.setStudentNo(studentNo);
        StudentCourse updateSC = studentCourseMapper.selectOne(studentCourse);
        log.debug("学生存在检测");
        if (updateSC == null) {
            return null;
        }

        log.debug("文件同步上传");
        OSS ossClient = null;
        String url = null;
        boolean flag = true;
        int i = 0;
        try {
            //创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(ConstantPropertiesUtil.END_POINT
                    , ConstantPropertiesUtil.ACCESS_KEY_ID
                    , ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            //判断文件格式
            for (String type : TYPESTR) {
                if (StringUtils.endsWithIgnoreCase(file.getOriginalFilename(), type)) {
                    flag = false;
                    break;
                }
                i++;
            }
            if (flag) {
                return "格式不正确！";
            }

            //获得文件保存路径
            String filename = file.getOriginalFilename();
            String ext = filename.substring(filename.lastIndexOf('.'));
            String newName = UUID.randomUUID().toString() + ext;
            String datePath = new DateTime().toString("yyyy/MM/dd");
            String newPath = ConstantPropertiesUtil.FILE_HOST + "/" + datePath + "/" + newName;

            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(ConstantPropertiesUtil.BUCKET_NAME, newPath, inputStream);

            log.debug("文件异步转换开始");
            uploadPdf(file.getInputStream(), newName.substring(0, newName.lastIndexOf(".")) + ".pdf",
                    newPath.substring(0, newPath.lastIndexOf(".")) + ".pdf", i == 0, ossClient);
            log.debug("文件异步等待结束");

            url = "https://" + ConstantPropertiesUtil.BUCKET_NAME + "." + ConstantPropertiesUtil.END_POINT + "/" + newPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("文件同步上传完成");

        //更新数据库
        updateSC.setProject(url);
        updateSC.setProjectName(file.getOriginalFilename());
        studentCourseMapper.updateByPrimaryKeySelective(updateSC);
        log.debug("更新数据库完成");
        // TODO 重复上传删除OSS上旧文件
        return url;
    }

    private void uploadPdf(InputStream inputStream, String newName, String newPath, boolean isDoc, OSS ossClient) {
        uploadExecutorPool.execute(() -> {
//            String tmpPdf = filename.substring(0, newName.lastIndexOf(".")) + ".pdf";
//            String pdfPath = ConstantPropertiesUtil.FILE_HOST + "/" + datePath + "/" + tmpPdf;
            File outputFile = new File(newName);
            try {
                OutputStream outputStream = new FileOutputStream(outputFile);
                IConverter converter = LocalConverter.builder().build();
                if (isDoc) {
                    converter.convert(inputStream).as(DocumentType.DOC).to(outputStream).as(DocumentType.PDF).execute();
                } else {
                    converter.convert(inputStream).as(DocumentType.DOCX).to(outputStream).as(DocumentType.PDF).execute();
                }
                ossClient.putObject(ConstantPropertiesUtil.BUCKET_NAME, newPath, outputFile);
                outputStream.close();
                outputFile.delete();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 关闭OSSClient。
                ossClient.shutdown();
            }
            log.debug("文件异步处理结束");

        });
    }
}
