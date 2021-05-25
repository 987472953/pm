package com.pzhu.pm.student.service;

import com.pzhu.pm.student.pojo.CGroup;
import com.pzhu.pm.student.pojo.SMember;
import com.pzhu.pm.student.pojo.Student;
import com.pzhu.pm.student.pojo.StudentInfoVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *
 * @author QYstart
 * @since 2021-04-04
 */
public interface StudentService {

    /**
     * 登录验证
     * @param account
     * @param password
     * @return
     */
    SMember login(String account, String password);

    /**
     * 根据学号查询学生详细信息
     * @param studentNo
     * @return
     */
    List<StudentInfoVO> selectInfo(String studentNo);

    /**
     * 签到接口
     * @param studentNo
     * @return
     */
    Integer toSign(String studentNo, Integer courseNo);

    /**
     * 查询学生表
     * @param studentNo
     * @return
     */
    Student getSudentById(String studentNo);

    /**
     * 获得该课程分组信息
     * @param studentNo
     * @param courseNo
     * @return
     */
    CGroup getGroupByStu(String studentNo, Integer courseNo);

    /**
     * 添加组长信息
     * @param studentNo
     * @param courseNo
     * @return
     */
    CGroup insertLeadGroup(String studentNo, Integer courseNo);

    /**
     * 添加普通组员
     * @param studentNo
     * @param courseNo
     * @return
     */
    CGroup insertGroup(String studentNo, Integer courseNo, Integer group);

    /**
     * 上传文件
     * @param file
     * @return
     */
    String upload(String studentNo, Integer courseNo, MultipartFile file);
}
