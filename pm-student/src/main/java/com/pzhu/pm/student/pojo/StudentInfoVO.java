package com.pzhu.pm.student.pojo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * @author QYstart
 * @date 2021/4/21
 */
@Data
@ToString
public class StudentInfoVO implements Serializable {
    private String studentNo;

    private Integer courseNo;

    private Integer teacherNo;

    private String teacherName;

    private Integer signCount;

    private Double mark;

    private String project;

    private String courseName;
}
