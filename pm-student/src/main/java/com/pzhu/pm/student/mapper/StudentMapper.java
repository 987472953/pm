package com.pzhu.pm.student.mapper;

import com.pzhu.pm.student.pojo.Student;
import com.pzhu.pm.student.pojo.StudentInfoVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 *
 * @author QYstart
 * @since 2021-04-04
 */
public interface StudentMapper extends Mapper<Student> {

    List<StudentInfoVO> selectInfo(@Param("studentNo") String studentNo);

    Student selectSS();
}
