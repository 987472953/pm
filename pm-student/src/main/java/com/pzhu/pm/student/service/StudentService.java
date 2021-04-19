package com.pzhu.pm.student.service;

import com.pzhu.pm.student.pojo.SMember;

/**
 *
 * @author QYstart
 * @since 2021-04-04
 */
public interface StudentService {

    /**
     * 登录验证
     * @param username
     * @param password
     * @return
     */
    SMember login(String username, String password);
}
