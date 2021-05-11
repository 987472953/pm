package com.pzhu.pm.student.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author QYstart
 * @date 2021/4/21
 */
@Data
public class StudentCourse implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String studentNo;

    @Column
    private Integer courseNo;

    @Column
    private Integer signCount;

    @Column
    private Double mark;

    @Column
    private String project;

    @Column
    private String schoolTime;

    @Transient
    private String courseName;

}
