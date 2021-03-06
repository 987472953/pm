package com.pzhu.pm.student.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author QYstart
 * @date 2021/4/21
 */
@Data
public class Course implements Serializable {

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
    private Integer majorNo;

    @Column
    private Integer limitNumber;

    @Column
    private Date endTime;

    @Column
    private Boolean must;

    @Column
    private Integer grade;

}
