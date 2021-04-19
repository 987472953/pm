package com.pzhu.pm.student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import tk.mybatis.spring.annotation.MapperScan;


/**
 * @author QYstart
 * @date 2021/4/4
 */
@SpringBootApplication
@MapperScan(basePackages = "com.pzhu.pm.student.mapper")
public class StudentMain {
    public static void main(String[] args) {

        SpringApplication.run(StudentMain.class, args);
    }
}
