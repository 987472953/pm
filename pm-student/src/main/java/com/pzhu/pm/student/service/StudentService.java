package com.pzhu.pm.student.service;

import com.pzhu.pm.student.pojo.SMember;
import com.pzhu.pm.student.pojo.Student;
import com.pzhu.pm.student.pojo.StudentInfoVO;

import java.util.List;

/**
 *
 * @author QYstart
 * @since 2021-04-04
 */
public interface StudentService {

    /**
     * 登录验证
     * @param account
     * @param password
     * @return
     */
    SMember login(String account, String password);

    /**
     * 根据学号查询学生详细信息
     * @param studentNo
     * @return
     */
    List<StudentInfoVO> selectInfo(String studentNo);
    public Student test();
}
