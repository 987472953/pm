package com.pzhu.pm.student.mapper;

import com.pzhu.pm.student.pojo.Course;
import com.pzhu.pm.student.pojo.StudentCourse;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author QYstart
 * @date 2021/4/25
 */
public interface CourseMapper extends Mapper<Course> {

    StudentCourse selectSC(@Param("courseNo") Integer courseNo, @Param("studentNo") String studentNo);
}
