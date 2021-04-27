package com.pzhu.pm.student.service.impl;

import com.pzhu.pm.student.mapper.CourseMapper;
import com.pzhu.pm.student.pojo.Course;
import com.pzhu.pm.student.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author QYstart
 * @date 2021/4/25
 */
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> findCourse(Integer grade, Integer majorNo) {

        Example example = new Example(Course.class);
        Example.Criteria criteria = example.createCriteria();
        if (grade != null) {
            criteria.andEqualTo("grade", grade);
        }
        if (majorNo != null) {
            criteria.andEqualTo("majorNo", majorNo);
        }
        return courseMapper.selectByExample(example);
    }

}
