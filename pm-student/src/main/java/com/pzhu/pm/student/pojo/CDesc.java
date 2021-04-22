package com.pzhu.pm.student.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * @author QYstart
 * @date 2021/4/21
 */
@Data
public class CDesc {

    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseNo;

    @Column
    private Integer desc;
}
