package com.pzhu.pm.student.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author QYstart
 * @date 2021/5/11
 */
public class Cache {

    public static Map<Object, Object> courseCache = new HashMap<Object, Object>();

    public static Map<Object, Object> coursedCache = new HashMap<Object, Object>();

    public static void setCache(String key, Object value) {
        courseCache.put(key, value);
    }

    public static Object getCourse(String key) {
        return courseCache.get(key);
    }

    public static void setCached(String key, Object value) {
        coursedCache.put(key, value);
    }

    public static Object getCoursed(String key) {
        return coursedCache.get(key);
    }

}
