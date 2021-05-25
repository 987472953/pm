package com.pzhu.pm.student.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.pzhu.pm.student.common.Cache;
import com.pzhu.pm.student.common.Result;
import com.pzhu.pm.student.config.Swagger2Config;
import com.pzhu.pm.student.pojo.SMember;
import com.pzhu.pm.student.pojo.Student;
import com.pzhu.pm.student.pojo.StudentCourse;
import com.pzhu.pm.student.pojo.StudentInfoVO;
import com.pzhu.pm.student.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

//    @GetMapping("/login")
////    @ApiOperation(value = "重定向到登录页面")
////    public String login() {
////        return "login";
////    }

    @ApiOperation(value = "输入用户名与密码登录")
    @PostMapping("/login")
    @ResponseBody
    public Result doLogin(@RequestParam String account,
                          @RequestParam String password) {
        if (account == null || password == null) return Result.error().message("用户名或密码为空");

        //TODO 加密
        SMember smember = studentService.login(account, password);

        if (smember == null) {
            return Result.error().message("用户不存在");
        } else {
            //登录成功
            //stringRedisTemplate.opsForValue().set("user:" + smember.getStudentNo() + ":login", JSONUtil.toJsonStr(smember));

            long startTime = System.currentTimeMillis();
            Student student = studentService.getSudentById(smember.getStudentNo());
            stringRedisTemplate.opsForValue().set("user:" + student.getStudentNo() + ":member", JSONUtil.toJsonStr(student), 1, TimeUnit.HOURS);
            long endTime = System.currentTimeMillis();
            System.out.println("Redis存储时间：" + (endTime - startTime) + "ms");

            return Result.ok().data("user", smember);
        }
    }

}

