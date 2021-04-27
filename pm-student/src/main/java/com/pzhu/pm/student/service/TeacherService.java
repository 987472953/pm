package com.pzhu.pm.student.service;

import com.pzhu.pm.student.pojo.Teacher;

import java.util.List;

public interface TeacherService {
    /**
     * 根据课程号获得该课程的老师
     * @param courseNo
     * @return
     */
    List<Teacher> findTeacherByCourseNo(String courseNo);
}
