package com.pzhu.pm.student.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
public enum ResultCode {

    SUCCESS(200,"成功"),

    FAIL(400,"数据错误"),

    ERROR(500,"服务器暂时不可用");

    @Getter
    private Integer code;
    @Getter
    private String status;
}
