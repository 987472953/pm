package com.pzhu.pm.student.pojo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * @author QYstart
 * @date 2021/4/25
 */
@Data
@ToString
public class TMember implements Serializable {
    @Column
    private Integer id;

    @Column
    private String teacherId;

    @Column
    private String account;

    @Column
    private String password;

    @Column
    private Date createTime;
}
