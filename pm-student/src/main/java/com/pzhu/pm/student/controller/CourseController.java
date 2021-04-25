package com.pzhu.pm.student.controller;

import com.pzhu.pm.student.common.Result;
import com.pzhu.pm.student.pojo.Course;
import com.pzhu.pm.student.pojo.Student;
import com.pzhu.pm.student.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author QYstart
 * @date 2021/4/25
 */
@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("course")
    public Result findCourse(Student student) {
        List<Course> courseList = courseService.findCourse(student.getGrade(), student.getMajorNo());

        return Result.ok().data("courseList", courseList);
    }
}
