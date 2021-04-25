package com.pzhu.pm.student.controller;

import com.pzhu.pm.student.common.Result;
import com.pzhu.pm.student.pojo.SMember;
import com.pzhu.pm.student.pojo.StudentInfoVO;
import com.pzhu.pm.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author QYstart
 * @date 2021/4/4
 */
@Controller
public class UserController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public Result doLogin(@RequestParam String account,
                          @RequestParam String password) {
        if (account == null || password == null) return Result.error().message("用户名或密码为空");

        //TODO 加密，将错误信息放入model重新转到login并显示错误
        SMember smember = studentService.login(account, password);

        return Result.ok().data("user", smember);
    }

    /**
     * 学生已选课程
     */
    @GetMapping("/course/{studentNo}")
    @ResponseBody
    public Result getCourse(@PathVariable String studentNo) {
        if (studentNo == null || studentNo.isEmpty())
            return Result.error().message("学号为空");
        List<StudentInfoVO> studentInfo = studentService.selectInfo(studentNo);
        return Result.ok().data("studentInfo", studentInfo);
    }

}

