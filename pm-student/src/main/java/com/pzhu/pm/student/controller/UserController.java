package com.pzhu.pm.student.controller;

import com.pzhu.pm.student.pojo.SMember;
import com.pzhu.pm.student.pojo.Student;
import com.pzhu.pm.student.pojo.StudentInfoVO;
import com.pzhu.pm.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String doLogin(@RequestParam String username,
                          @RequestParam String password, Model model) {
        if (username == null || password == null) return "login";

        SMember smember = studentService.login(username, password);

        if (smember!=null) {
            model.addAttribute("smember", smember);
            return "index";
        } else {
            return "login";
        }
    }

    @GetMapping("/test")
    @ResponseBody
    public String findUser() {
        Student test = studentService.test();
        return test.toString();
    }

    @GetMapping("/course/{studentNo}")
    @ResponseBody
    public String getCourse(@PathVariable String studentNo){
        List<StudentInfoVO> studentInfo = studentService.selectInfo(studentNo);
        return studentInfo.toString();
    }

}
