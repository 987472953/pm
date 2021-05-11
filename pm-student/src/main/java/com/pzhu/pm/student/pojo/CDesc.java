package com.pzhu.pm.student.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

/**
 * @author QYstart
 * @date 2021/4/21
 */
@Data
public class CDesc implements Serializable {

    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseNo;

    @Column
    private Integer desc;
}
