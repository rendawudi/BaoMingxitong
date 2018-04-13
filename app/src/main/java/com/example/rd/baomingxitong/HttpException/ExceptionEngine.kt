package com.example.rd.jk1504mvvm.httpException

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ParseException
import android.widget.Toast
import com.example.rd.baomingxitong.base.MyApp
import com.example.rd.baomingxitong.fuzhu.ProgressHandler
import com.example.rd.baomingxitong.views.LoginActivityXs
import com.example.rd.baomingxitong.views.RegisterActivity
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Created by rd on 2017/9/22.
 */
class ExceptionEngine
{
    companion object {
        val UN_KONWN_ERRO = 1000//未知错误
        val ANALYTIC_SERVER_DATA_ERROR = 1001//解析(服务器)数据错误
        val ANALYTIC_CLIENT_DATA_ERROR = 1002//解析(客户端)数据错误
        val CONNECT_ERROR = 1003//网络连接错误
        val TIME_OUT_ERROR = 1004//网络连接超时
        fun handleException(e:Throwable,mactivity: Context)
        {
            if (e is HttpException)
            {
                Toast.makeText(MyApp.instances.context,"网络错误：Http",Toast.LENGTH_SHORT).show()
                ProgressHandler.disMissProgressDialog()
            }
            else if (e is ServerException)
            {
                Toast.makeText(MyApp.instances.context,"网络错误：Server",Toast.LENGTH_SHORT).show()
                ProgressHandler.disMissProgressDialog()
            }
            else if (e is JsonParseException
                    || e is JSONException
                    || e is ParseException
                    || e is MalformedJsonException)
            {
                Toast.makeText(MyApp.instances.context,"解析错误",Toast.LENGTH_SHORT).show()
                ProgressHandler.disMissProgressDialog()
            }
            else if (e is ConnectException) {//连接网络错误
                Toast.makeText(MyApp.instances.context,"连接网络错误",Toast.LENGTH_SHORT).show()
                ProgressHandler.disMissProgressDialog()
            } else if (e is SocketTimeoutException) {//网络超时
                Toast.makeText(MyApp.instances.context,"网络超时",Toast.LENGTH_SHORT).show()
                ProgressHandler.disMissProgressDialog()
            }
            else if (e is ApiException)
            {
                Toast.makeText(MyApp.instances.context,e.msg,Toast.LENGTH_SHORT).show()
                ProgressHandler.disMissProgressDialog()
            }
            else {  //未知错误
                Toast.makeText(MyApp.instances.context,"未知错误：XXX",Toast.LENGTH_SHORT).show()
                ProgressHandler.disMissProgressDialog()
            }
        }

        fun ActionJudge(mactivity: Context)
        {
            if (mactivity is RegisterActivity)
            {
                MyApp.instances.sfLgg(false)
                mactivity.startActivity(Intent(mactivity,LoginActivityXs::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            }
        }

    }
}