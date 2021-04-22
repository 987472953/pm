package com.pzhu.pm.student.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author QYstart
 * @date 2021/4/21
 */
@Data
public class CGroup {

    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentNo;

    @Column
    private Integer courseNo;

    @Column
    private String project;

    @Column
    private Integer group;

    @Column
    private Boolean lead;
}
