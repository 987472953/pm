package com.pzhu.pm.student.controller;

import com.pzhu.pm.student.common.Result;
import com.pzhu.pm.student.config.Swagger2Config;
import com.pzhu.pm.student.pojo.SMember;
import com.pzhu.pm.student.pojo.StudentCourse;
import com.pzhu.pm.student.pojo.StudentInfoVO;
import com.pzhu.pm.student.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author QYstart
 * @date 2021/4/4
 */
@Controller
@Api(tags = Swagger2Config.TAG_1)
@CrossOrigin
public class UserController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/login")
    @ApiOperation(value = "重定向到登录页面")
    public String login() {
        return "login";
    }

    @ApiOperation(value = "输入用户名与密码登录")
    @PostMapping("/login")
    @ResponseBody
    public Result doLogin(@RequestParam String account,
                          @RequestParam String password) {
        if (account == null || password == null) return Result.error().message("用户名或密码为空");

        //TODO 加密，将错误信息放入model重新转到login并显示错误
        SMember smember = studentService.login(account, password);

        if (smember == null) {
            return Result.error().message("用户不存在");
        } else {
            return Result.ok().data("user", smember);
        }
    }

    /**
     * 学生已选课程
     */
    @ApiOperation("根据学生ID查询课程信息")
    @GetMapping("/course/{studentNo}")
    @ResponseBody
    public Result getCourse(@PathVariable String studentNo) {
        if (studentNo == null || studentNo.isEmpty())
            return Result.error().message("学号为空");
        List<StudentInfoVO> studentInfo = studentService.selectInfo(studentNo);
        return Result.ok().data("studentInfo", studentInfo);
    }





}

