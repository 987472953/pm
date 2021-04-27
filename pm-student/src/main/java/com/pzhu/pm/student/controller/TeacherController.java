package com.pzhu.pm.student.controller;

import com.pzhu.pm.student.common.Result;
import com.pzhu.pm.student.config.Swagger2Config;
import com.pzhu.pm.student.pojo.Teacher;
import com.pzhu.pm.student.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 98747
 */
@Api(tags = Swagger2Config.TAG_3)
@RestController
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value = "查询该课程的上课老师")
    @GetMapping("/teacher/{courseNo}")
    public Result getTeacher(@PathVariable String courseNo){
        if(courseNo==null || courseNo.isEmpty()){
            return Result.error().message("课程号为空");
        }

        List<Teacher> teacherList = teacherService.findTeacherByCourseNo(courseNo);

        if(teacherList==null){
            return Result.error().message("课程号不正确或没有老师教这个课");
        }else{
            return Result.ok().data("teacherList", teacherList);
        }
    }
}
