package com.pzhu.pm.student.mapper;

import com.pzhu.pm.student.pojo.Teacher;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface TeacherMapper extends Mapper<Teacher> {
    /**
     * 根据课程号查询老师
     * @param courseNo
     * @return
     */
    List<Teacher> selectByCourseNo(Integer courseNo);
}
