package com.example.rd.baomingxitong.entity;

import java.io.Serializable;

/**
 * Created by mick2017 on 2018/4/12.
 */

public class myHttpResult<T> implements Serializable{
    private int code;
    private String msg;
    private T data;

    public myHttpResult() {
        super();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
}
