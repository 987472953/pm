package com.pzhu.pm.student;

import com.pzhu.pm.student.config.Config;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
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