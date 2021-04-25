package com.pzhu.pm.student.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一返回结果
 * @author 98747
 */
@Data
public class Result {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回数据
     */
    private Map<String, Object> data = new HashMap<String, Object>();

    public Result() {
    }

    public static Result ok() {
        Result r = new Result();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS.getCode());
        r.setMessage(ResultCode.SUCCESS.getStatus());
        return r;
    }

    public static Result error() {
        Result r = new Result();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR.getCode());
        r.setMessage(ResultCode.ERROR.getStatus());
        return r;
    }

    public Result message(String message) {
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }

    public Result data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public Result data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }
}