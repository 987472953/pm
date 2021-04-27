package com.pzhu.pm.student.mapper;

import com.pzhu.pm.student.pojo.Course;
import com.pzhu.pm.student.pojo.StudentCourse;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author QYstart
 * @date 2021/4/25
 */
public interface CourseMapper extends Mapper<Course> {

    StudentCourse selectSC(String courseNo, String studentNo);
}
