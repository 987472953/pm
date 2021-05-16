package com.pzhu.pm.student.service.impl;

import com.pzhu.pm.student.mapper.CourseMapper;
import com.pzhu.pm.student.mapper.StudentCourseMapper;
import com.pzhu.pm.student.pojo.Course;
import com.pzhu.pm.student.pojo.StudentCourse;
import com.pzhu.pm.student.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author QYstart
 * @date 2021/4/25
 */
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private StudentCourseMapper studentCourseMapper;

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

    @Override
    public StudentCourse removeCourseByStu(String courseNo, String studentNo) {

        StudentCourse studentCourse = courseMapper.selectSC(Integer.valueOf(courseNo), studentNo);

        //课程存在但课程有成绩时不能删除，课程存在且成绩为初始值时可退
        if (studentCourse != null &&
                (studentCourse.getMark() == null || studentCourse.getMark() <= 0)) {
            int delete = studentCourseMapper.delete(studentCourse);
            if (delete > 0) return studentCourse;
        }
        return null;
    }

    @Override
    public StudentCourse takeCourse(String studentNo, Integer courseNo) {

        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            StudentCourse checked = courseMapper.selectSC(courseNo, studentNo);
            if (checked != null) {
                //已经选了该课程
                return null;
            }
            Course course = new Course();
            course.setCourseNo(courseNo);
            course = courseMapper.selectOne(course);
            if (course.getCount() >= course.getLimitNumber()) {
                //选课人数达上限
                return null;
            }

            //选课
            StudentCourse studentCourse = new StudentCourse();
            studentCourse.setSignCount(0);
            studentCourse.setCourseNo(courseNo);
            studentCourse.setStudentNo(studentNo);
            studentCourse.setMark(0.0);
            int insert = studentCourseMapper.insertSelective(studentCourse);
            return studentCourse;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return null;
    }
}
