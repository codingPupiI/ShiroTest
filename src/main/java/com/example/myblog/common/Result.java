package com.example.myblog.common;

import com.example.myblog.entity.MUser;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class Result implements Serializable {
    /**
     * 200:正常
     */
    private int code;
    private String msg;
    private Object data;

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success(Object data) {
        return new Result(200, "操作成功", data);
    }

    public static Result fail(String msg, Object data) {
        return new Result(400, msg, data);
    }

    public static Result fail(Object data) {
        return new Result(400, "操作失败", data);
    }
}
