package com.pzhu.pm.student.service;

import com.pzhu.pm.student.pojo.Course;
import com.pzhu.pm.student.pojo.StudentCourse;

import java.util.List;

/**
 * @author QYstart
 * @date 2021/4/25
 */
public interface CourseService {

    /**
     * 根据年级号与专业号查找课程
     * @return
     */
    List<Course> findCourse(Integer grade, Integer majorNo);
    /**
     * 根据学号与课程号进行退选操作
     *  @param courseNo
     * @param studentNo
     */
    StudentCourse removeCourseByStu(String courseNo, String studentNo);
}
