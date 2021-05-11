package com.pzhu.pm.student.controller;


import com.pzhu.pm.student.common.Result;
import com.pzhu.pm.student.pojo.Student;
import com.pzhu.pm.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author QYstart
 * @since 2021-04-04
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("sign")
    public Result sign(String studentNo, Integer courseNo){
        Integer signCount = studentService.toSign(studentNo, courseNo);
        if(signCount>0){
            return Result.ok().message("签到成功").data("signCount", signCount);
        }else{
            return Result.error().message("签到失败");
        }
    }

}

