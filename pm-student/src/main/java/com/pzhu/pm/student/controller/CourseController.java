package com.pzhu.pm.student.controller;

import com.pzhu.pm.student.common.Cache;
import com.pzhu.pm.student.common.Result;
import com.pzhu.pm.student.config.Swagger2Config;
import com.pzhu.pm.student.pojo.Course;
import com.pzhu.pm.student.pojo.StudentCourse;
import com.pzhu.pm.student.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    private RedisTemplate redisTemplate;

    public static final String pre = "course";

    @GetMapping("course")
    @ApiOperation(value = "查询学生可选课程，根据学生年级与专业编号")
    public Result findCourse(Integer majorNo, Integer grade) {

        List<Course> courseList = courseService.findCourse(grade, majorNo);
        return Result.ok().data("courseList", courseList);
    }

    @PutMapping("course")
    @ApiOperation(value = "选课")
    public Result takeCourse(String studentNo, Integer courseNo) {

//        List<Course> courses = (List<Course>) Cache.getCourse("course" + studentNo);
//        boolean flag = false;
//        if (courses != null && courses.size() > 0) {
//            for (Course course : courses) {
//                if (course.getCourseNo().equals(courseNo)) {
//                    flag = true;
//                }
//            }
//        }
//
//        if(!flag){
//            return Result.error().message("该学生不能选这门课");
//        }

        StudentCourse studentCourse = courseService.takeCourse(studentNo, courseNo);
        if(studentCourse==null){
            return Result.error().message("重复选课");
        }
        return Result.ok().data("studentCourse", studentCourse);
    }


    @GetMapping("major")
    @ApiOperation(value = "查询所有专业")
    public Result allMajor() {
//        courseService.findMajors();
        return Result.ok();
    }

    @DeleteMapping("/stuCourse")
    @ApiOperation(value = "退选课程")
    public Result removeCourseByStuNo(@RequestParam String courseNo, @RequestParam String studentNo) {

        StudentCourse studentCourse = courseService.removeCourseByStu(courseNo, studentNo);
        if (studentCourse == null) {
            return Result.error().message("课程不存在，或已有成绩");
        }
        return Result.ok().data("deleteCourse", studentCourse);
    }
}
