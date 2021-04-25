package com.pzhu.pm.student.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author QYstart
 * @date 2021/4/21
 */
@Data
public class Course {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer courseNo;

    @Column
    private String name;

    @Column
    private Integer count;

    @Column
    private Integer grade;

    @Column
    private Integer majorNo;

    @Column
    private Integer limit;

    @Column
    private Date endTime;

    @Column
    private Boolean must;
}
