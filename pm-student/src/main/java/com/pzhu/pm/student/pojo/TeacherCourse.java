package com.pzhu.pm.student.pojo;

import lombok.Data;
import lombok.ToString;
import org.assertj.core.util.CanIgnoreReturnValue;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author QYstart
 * @date 2021/4/25
 */
@Data
@ToString
public class TeacherCourse implements Serializable {

    @Column
    private Integer teacherNo;

    @Column
    private Integer courseNo;

    @Column
    private Integer limitNum;

    @Column
    private Integer count;

}
