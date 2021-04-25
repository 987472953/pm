package com.pzhu.pm.student.pojo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;

/**
 * @author QYstart
 * @date 2021/4/25
 */
@Data
@ToString
public class Major {

    @Column
    private Integer id;

    @Column
    private Integer majorNo;

    @Column
    private String name;

}
