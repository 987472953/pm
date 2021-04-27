package com.pzhu.pm.student.controller;

import com.pzhu.pm.student.common.Result;
import com.pzhu.pm.student.config.Swagger2Config;
import com.pzhu.pm.student.pojo.Course;
import com.pzhu.pm.student.pojo.Student;
import com.pzhu.pm.student.pojo.StudentCourse;
import com.pzhu.pm.student.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author QYstart
 * @date 2021/4/25
 */
@RestController
@CrossOrigin
@Api(tags = Swagger2Config.TAG_2)
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("course")
    @ApiOperation(value = "查询学生可选课程，根据学生年级与专业编号")
    public Result findCourse(Student student) {
        List<Course> courseList = courseService.findCourse(student.getGrade(), student.getMajorNo());

        return Result.ok().data("courseList", courseList);
    }



    @GetMapping("major")
    @ApiOperation(value = "查询所有专业")
    public Result allMajor(){
//        courseService.findMajors();
        return Result.ok();
    }

    @DeleteMapping("/stuCourse")
    @ApiOperation(value = "退选课程")
    public Result removeCourseByStuNo(@RequestParam String courseNo, @RequestParam String studentNo){

        StudentCourse studentCourse =  courseService.removeCourseByStu(courseNo, studentNo);

        return Result.ok();
    }
}
