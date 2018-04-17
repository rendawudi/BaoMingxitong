package com.example.rd.baomingxitong.Http

import com.example.rd.baomingxitong.entity.HttpResult
import com.example.rd.baomingxitong.entity.Task.TaskSpMain
import com.example.rd.baomingxitong.entity.Task.TaskmainGet
import com.example.rd.baomingxitong.entity.TeacherHttpLoginGet
import com.example.rd.baomingxitong.entity.UserHttpLoginGet
import com.example.rd.baomingxitong.entity.UserLogin
import com.example.rd.baomingxitong.entity.Xuexiao.Banji
import com.example.rd.baomingxitong.entity.Xuexiao.Pici
import com.example.rd.baomingxitong.entity.Xuexiao.Xueyuan
import com.example.rd.baomingxitong.entity.Xuexiao.Zhuanye
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * Created by rd on 2017/9/27.
 */
interface UserApi
{
    companion object {
        var BASE_URL = "http://192.168.1.231:8080/" //http://10.0.2.2:8080/   http://111.230.17.38/baomingxitong/
    }

    @POST("student/login")
    fun getUserLogin(@Body body: RequestBody): Observable<HttpResult<String>>

    @POST("student/getUserMsg")
    fun getUserMsg(): Observable<HttpResult<UserHttpLoginGet>>

    @POST("updataUser")
    fun updataUser(@Body body: RequestBody): Observable<HttpResult<String>>

    @POST("student/getTeacherMsg")
    fun getTeacherMsg(): Observable<HttpResult<TeacherHttpLoginGet>>


    @POST("student/register")
    fun UserRegister(@Body body: RequestBody): Observable<HttpResult<String>>

    @GET("student/getXueyuans")
    fun getXueyuans(): Observable<HttpResult<List<Xueyuan>>>

    @GET("student/getZhuanyes")
    fun getZhuanyes(@Query("xueyuanId")xueyuanId: Int): Observable<HttpResult<List<Zhuanye>>>

    @GET("student/getBanjis")
    fun getBanjis(@Query("zhuanyeId")zhuanyeId: Int): Observable<HttpResult<List<Banji>>>

    @GET("student/getPicis")
    fun getPicis(): Observable<HttpResult<List<Pici>>>

}