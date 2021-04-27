package com.pzhu.pm.student.service.impl;

import com.pzhu.pm.student.common.Convert;
import com.pzhu.pm.student.mapper.TeacherMapper;
import com.pzhu.pm.student.pojo.Teacher;
import com.pzhu.pm.student.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public List<Teacher> findTeacherByCourseNo(String courseNo) {
        Integer integer = Convert.toInteger(courseNo);
        if (integer == null) {
            return null;
        }
        return teacherMapper.selectByCourseNo(integer);
    }
}
