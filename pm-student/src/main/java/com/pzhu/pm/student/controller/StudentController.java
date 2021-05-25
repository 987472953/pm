package com.pzhu.pm.student.controller;


import com.pzhu.pm.student.config.ConstantPropertiesUtil;
import com.pzhu.pm.student.common.Result;
import com.pzhu.pm.student.pojo.CGroup;
import com.pzhu.pm.student.service.StudentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author QYstart
 * @since 2021-04-04
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("sign")
    @ApiOperation("课程签到")
    public Result sign(String studentNo, Integer courseNo) {
        Integer signCount = studentService.toSign(studentNo, courseNo);
        if (signCount > 0) {
            return Result.ok().message("签到成功").data("signCount", signCount);
        } else {
            return Result.error().message("签到失败");
        }
    }

    @ApiOperation("查询分组信息")
    @GetMapping("/group")
    public Result getGroup(String studentNo, Integer courseNo) {

        CGroup cGroup = studentService.getGroupByStu(studentNo, courseNo);
        return Result.ok().data("group", cGroup);
    }

    @ApiOperation("竞选组长")
    @PutMapping("/groupLead")
    public Result saveGroupLead(String studentNo, Integer courseNo) {

        CGroup cGroup = studentService.insertLeadGroup(studentNo, courseNo);
        return Result.ok().data("group", cGroup);
    }

    @ApiOperation("普通成员选组")
    @PutMapping("/group")
    public Result saveGroup(String studentNo, Integer courseNo, Integer group) {

        CGroup cGroup = studentService.insertGroup(studentNo, courseNo, group);
        return Result.ok().data("group", cGroup);
    }

//    @ApiOperation("个人成果上传Word")
//    @PutMapping("/upResult")
//    public Result uploadOwnResult(String studentNo, Integer courseNo, String project){
//        String result = studentService.insertResult();
//        return ;
//    }


    @ApiOperation("Word文件上传")
    @PostMapping("file/upload")
    public Result upload(
            String studentNo, Integer courseNo,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "host", required = false) String host) {

        /*<embed width="800" height="600" src="https://pm-school.oss-cn-shanghai.aliyuncs.com/student/2021/05/25/实验三.pdf" type="">*/
        if (!StringUtils.isEmpty(host)) {
            ConstantPropertiesUtil.FILE_HOST = host;
        } else {
            ConstantPropertiesUtil.FILE_HOST = "student";
        }
        String url = studentService.upload(studentNo, courseNo, file);
        return Result.ok().data("url", url);
    }

}

