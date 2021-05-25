package com.pzhu.pm.student.controller;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.pzhu.pm.student.common.Result;
import com.pzhu.pm.student.config.Swagger2Config;
import com.pzhu.pm.student.pojo.*;
import com.pzhu.pm.student.service.CourseService;
import com.pzhu.pm.student.service.StudentService;
import com.pzhu.pm.student.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author QYstart
 * @date 2021/4/25
 */
@RestController
@CrossOrigin
@Api(tags = Swagger2Config.TAG_2)
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    public static final String pre = "course";

    @GetMapping("selectCourse")
    @ApiOperation(value = "查询学生 可选课程，根据学生年级与专业编号")
    public Result findCourse(Integer majorNo, Integer grade) {

        List<Course> courseList = courseService.findCourse(grade, majorNo);
        return Result.ok().data("courseList", courseList);
    }

    /**
     * 可选课程
     *
     * @param studentNo
     * @return
     */
    @GetMapping("course")
    @ApiOperation(value = "查询可选课程，学生学号查询")
    public Result findCourse(String studentNo) {
        // TODO Redis 可能报错
        String strStudent = stringRedisTemplate.opsForValue().get("user:" + studentNo + ":member");
        Student student = JSONUtil.toBean(strStudent, Student.class);
        if (student == null) {
            return Result.error().message("没有学生信息");
        }
        List<Course> courseList = courseService.findCourse(student.getGrade(), student.getMajorNo());
        //去掉已选课程
        String str = stringRedisTemplate.opsForValue().get("user:" + studentNo + ":checked");
        //TODO 判断str为空
        JSONArray jsonArray = new JSONArray(str);

        for (int j = 0; j < jsonArray.size(); j++) {
            JSONObject jsonObject = jsonArray.getJSONObject(j);
            StudentInfoVO studentInfoVO = JSONUtil.toBean(jsonObject, StudentInfoVO.class);
            for (int i = 0; i < courseList.size(); i++) {
                if (studentInfoVO.getCourseNo().equals(courseList.get(i).getCourseNo())) {
                    courseList.remove(i);
                    i--;
                }
            }
        }
        stringRedisTemplate.opsForValue().set("user:" + studentNo + ":check", JSONUtil.toJsonStr(courseList), 1, TimeUnit.HOURS);
        return Result.ok().data("courseList", courseList);
    }


    @PutMapping("course")
    @ApiOperation(value = "选课")
    public Result takeCourse(String studentNo, Integer courseNo, Integer teacherNo) {

        //从缓存中拿取可选课程，进行判断
        String str = stringRedisTemplate.opsForValue().get("user:" + studentNo + ":check");
        JSONArray jsonArray = new JSONArray(str);

        boolean flag = false;
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Course course = JSONUtil.toBean(jsonObject, Course.class);
            if (course.getCourseNo().equals(courseNo)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            return Result.error().message("该学生不能选该课");
        }

        StudentCourse studentCourse = courseService.takeCourse(studentNo, courseNo, teacherNo);
        if (studentCourse == null) {
            return Result.error().message("选课失败");
        }

        // 将新选课加入checked缓存，更新缓存中该课程选课人数
        List<StudentInfoVO> studentInfo = studentService.selectInfo(studentNo);
        stringRedisTemplate.opsForValue().set("user:" + studentNo + ":checked", JSONUtil.toJsonStr(studentInfo));
        return Result.ok().data("studentCourse", studentCourse);
    }


//    @GetMapping("major")
//    @ApiOperation(value = "查询所有专业")
//    public Result allMajor() {
////        courseService.findMajors();
//        return Result.ok();
//    }

    @DeleteMapping("/course")
    @ApiOperation(value = "退课")
    public Result removeCourseByStuNo(@RequestParam Integer courseNo, @RequestParam String studentNo) {


        Integer teacherNo = null;
        String s = stringRedisTemplate.opsForValue().get("user:" + studentNo + ":checked");
        JSONArray jsonArray = new JSONArray(s);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            StudentCourse studentCourse = jsonObject.toBean(StudentCourse.class);
            if (studentCourse.getCourseNo().equals(courseNo)) {
                //该学生已选课中有该课程
                teacherNo = studentCourse.getTeacherNo();
                break;
            }
        }
        if (teacherNo == null) {
            return Result.error().message("没有选择该课程");
        }

        StudentCourse studentCourse = courseService.removeCourseByStu(courseNo, studentNo);
        if (studentCourse == null) {
            return Result.error().message("课程不存在，或已有成绩");
        }
        return Result.ok().data("deleteCourse", studentCourse);
    }

    /**
     * 学生已选课程
     */
    @ApiOperation("查询已选课程信息")
    @GetMapping("/coursed")
    @ResponseBody
    public Result getCourse(@RequestParam String studentNo) {
        if (studentNo == null || studentNo.isEmpty())
            return Result.error().message("学号为空");
        List<StudentInfoVO> studentInfo = studentService.selectInfo(studentNo);
        stringRedisTemplate.opsForValue().set("user:" + studentNo + ":checked", JSONUtil.toJsonStr(studentInfo));
        return Result.ok().data("studentInfo", studentInfo);
    }



}
