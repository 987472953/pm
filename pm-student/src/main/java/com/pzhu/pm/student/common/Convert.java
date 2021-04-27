package com.pzhu.pm.student.common;

/**
 * @author QYstart
 * @date 2021/4/27
 */
public class Convert {

    public static Integer toInteger(String str){
        try {
            return Integer.valueOf(str);
        }catch (Exception e){
            return null;
        }
    }
}
