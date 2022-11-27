package com.example.vue3.common;

//借口返回数据模版
public class Result<T> {
    private String code;        //返回的状态码
    private String msg;         //错误信息
    private T data;             //返回的数据

    public Result(T data) {
        this.data = data;
    }

    public Result() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    //标志成功
    public static Result success() {
        Result result = new Result();
        result.setCode("0");
        result.setMsg("success");
        return result;
    }

    //标志成功附带数据
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>(data);
        result.setCode("0");
        result.setMsg("success");
        return result;
    }

    //标志失败
    public static Result error(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}
