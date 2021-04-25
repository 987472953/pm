package com.pzhu.pm.student.pojo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * <p>
 *
 * </p>
 *
 * @author QYstart
 * @since 2021-04-04
 */
@Data
@ToString
public class Student implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String studentNo;

    @Column
    private Integer majorNo;

    @Column
    private Integer grade;

    @Column
    private Integer age;

    @Column
    private Integer sex;
}
