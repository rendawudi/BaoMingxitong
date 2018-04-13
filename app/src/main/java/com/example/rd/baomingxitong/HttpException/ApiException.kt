package com.example.rd.jk1504mvvm.httpException

/**
 * Created by rd on 2017/9/22.
 */

public class ApiException: Exception
{
    var code: Int
    lateinit var msg: String
    companion object {
        val USER_NOT_EXIST = 100
        val WRONG_PASSWORD = 101
        public fun getApiMsg(code: Int): String
        {
            when(code)
            {
                100 -> return "注册失败"
                101 -> return "登录失败"
                151 -> return "学院获取失败"
                152 -> return "专业获取失败"
                153 -> return "班级获取失败"
                else -> return "未知错误"
            }
        }
    }
    constructor(throwable: Throwable,code: Int):super(throwable)
    {
        this.code = code
    }
    constructor(code: Int,msg: String)
    {
        this.code = code
        this.msg = msg
    }

}