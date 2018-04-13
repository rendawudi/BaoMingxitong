package com.example.rd.jk1504mvvm.httpException

/**
 * Created by rd on 2017/9/22.
 */

public class ServerException: RuntimeException
{
    var code: Int
    var msg: String

    constructor(code: Int,msg: String)
    {
        this.code = code
        this.msg = msg
    }
}