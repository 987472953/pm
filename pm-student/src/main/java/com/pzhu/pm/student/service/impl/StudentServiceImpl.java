package com.pzhu.pm.student.service.impl;

import com.pzhu.pm.student.mapper.SMemberMapper;
import com.pzhu.pm.student.mapper.StudentMapper;
import com.pzhu.pm.student.pojo.SMember;
import com.pzhu.pm.student.pojo.Student;
import com.pzhu.pm.student.pojo.StudentInfoVO;
import com.pzhu.pm.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author QYstart
 * @since 2021-04-04
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private SMemberMapper sMemberMapper;

    @Override
    public SMember login(String username, String password) {

        SMember smember = new SMember();
        smember.setAccount(username);
        smember.setPassword(password);
        SMember result = sMemberMapper.selectOne(smember);
        if (result != null){
            result.setPassword("");
        }
        return result;
    }

    @Override
    public List<StudentInfoVO> selectInfo(String studentNo) {
        List<StudentInfoVO> studentInfoVO = studentMapper.selectInfo(studentNo);
        return studentInfoVO;
    }

    @Override
    public Student test(){
        return studentMapper.selectSS();
    }
}
