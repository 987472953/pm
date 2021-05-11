package com.pzhu.pm.student.pojo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @author QYstart
 * @date 2021/4/25
 */
@Data
@ToString
public class Teacher implements Serializable {
    @Column
    private Integer id;

    @Column
    private Integer teacherNo;

    @Column
    private String name;

    @Column
    private String title;
}
