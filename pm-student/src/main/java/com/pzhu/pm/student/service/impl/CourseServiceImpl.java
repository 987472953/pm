package com.pzhu.pm.student.service.impl;

import com.pzhu.pm.student.mapper.CourseMapper;
import com.pzhu.pm.student.mapper.StudentCourseMapper;
import com.pzhu.pm.student.mapper.TeacherCourseMapper;
import com.pzhu.pm.student.pojo.Course;
import com.pzhu.pm.student.pojo.StudentCourse;
import com.pzhu.pm.student.pojo.TeacherCourse;
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

    @Autowired
    private TeacherCourseMapper teacherCourseMapper;

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
    public StudentCourse removeCourseByStu(Integer courseNo, String studentNo) {

        StudentCourse studentCourse = courseMapper.selectSC(courseNo, studentNo);

        //课程存在但课程有成绩时不能删除，课程存在且成绩为初始值时可退
        if (studentCourse != null &&
                (studentCourse.getMark() == null || studentCourse.getMark() <= 0)) {

            Integer teacherNo = studentCourse.getTeacherNo();
            //退课减选课人数
            TeacherCourse teacherCourse = new TeacherCourse();
            teacherCourse.setTeacherNo(teacherNo);
            teacherCourse.setCourseNo(courseNo);
            TeacherCourse tc = teacherCourseMapper.selectOne(teacherCourse);
            Integer count = tc.getCount() - 1;
            updateCountByCourseNo(teacherNo, courseNo, count);

            int delete = studentCourseMapper.delete(studentCourse);
            if (delete > 0) return studentCourse;
        }
        return null;
    }

    private void updateCountByCourseNo(Integer teacherNo, Integer courseNo, Integer newCount) {

        //教师课程表选课人数-1，可异步
        Example example = new Example(TeacherCourse.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("courseNo", courseNo).andEqualTo("teacherNo", teacherNo);
        TeacherCourse course = new TeacherCourse();
        course.setCount(newCount);
        teacherCourseMapper.updateByExampleSelective(course, example);
    }

    @Override
    public StudentCourse takeCourse(String studentNo, Integer courseNo, Integer teacherNo) {

        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            //教师号不相同也认为选了该课
            StudentCourse checked = courseMapper.selectSC(courseNo, studentNo);
            if (checked != null) {
                //已经选了该课程
                return null;
            }

            TeacherCourse teacherCourse = new TeacherCourse();
            teacherCourse.setCourseNo(courseNo);
            teacherCourse.setTeacherNo(teacherNo);
            TeacherCourse nowCourse = teacherCourseMapper.selectOne(teacherCourse);
            if(nowCourse == null){
                //该老师没有教这门课
                return null;
            }
            int i = nowCourse.getCount() + 1;
            if (nowCourse.getCount() >= nowCourse.getLimitNum()) {
                //选课人数达上限
                return null;
            } else {
                Example example = new Example(TeacherCourse.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("courseNo", courseNo).andEqualTo("teacherNo", teacherNo);
                TeacherCourse course = new TeacherCourse();
                course.setCount(i);
                teacherCourseMapper.updateByExampleSelective(course, example);
            }

            //选课
            StudentCourse studentCourse = new StudentCourse();
            studentCourse.setSignCount(0);
            studentCourse.setCourseNo(courseNo);
            studentCourse.setStudentNo(studentNo);
            studentCourse.setTeacherNo(teacherNo);
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
