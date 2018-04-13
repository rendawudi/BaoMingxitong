package com.example.rd.baomingxitong.Http

import com.example.rd.baomingxitong.entity.HttpResult
import com.example.rd.baomingxitong.entity.LiaoTian.LTXmHttp
import com.example.rd.baomingxitong.entity.Task.TaskDuiyuan
import com.example.rd.baomingxitong.entity.Task.TaskSpMain
import com.example.rd.baomingxitong.entity.Task.TaskmainGet
import com.example.rd.baomingxitong.entity.UserHttpLoginGet
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
interface TaskApi
{

    @POST("studenttask/getsptasks")
    @FormUrlEncoded
    fun getsptasks(@Field("xueyuanId")xueyuanId: Int): Observable<HttpResult<List<Pici>>>

    @POST("studenttask/gettasks")
    fun getTaskMains(@Body body: RequestBody): Observable<HttpResult<List<TaskmainGet>>>

    @POST("studenttask/insertStdXiaoDui")
    fun insertStdXiaoDui(@Body body: RequestBody): Observable<HttpResult<List<TaskDuiyuan>>>

    @POST("studenttask/getPermission")
    @FormUrlEncoded
    fun getPermission(@Field("xiangmuId")xiangmuId: Int, @Field("piciId")piciId: Int): Observable<HttpResult<Boolean>>

    @POST("studenttask/qiangXM")
    @FormUrlEncoded
    fun qiangXM(@Field("xiangmuId")xiangmuId: Int, @Field("piciId")piciId: Int): Observable<HttpResult<Int>>


    @POST("studenttask/insertStdXiangMu")
    fun insertStdXiangmu(@Body body: RequestBody): Observable<HttpResult<List<TaskDuiyuan>>>

    @POST("studenttask/getMsg")
    @FormUrlEncoded
    fun getTaskJoinedMsg(@Field("xuehao")xuehao: String): Observable<HttpResult<LTXmHttp>>

    @POST("studenttask/getXDMsg")
    @FormUrlEncoded
    fun getXDMsg(@Field("xiangmuId")xiangmuId: Int, @Field("piciId")piciId: Int, @Field("duizhangxuehao")duizhangxuehao: String): Observable<HttpResult<List<TaskDuiyuan>>>

    @POST("studenttask/updataLSYJ")
    fun updataLSYJ(@Body body: RequestBody): Observable<HttpResult<String>>

    @POST("studenttask/updataZhuangTai")
    @FormUrlEncoded
    fun updataZhuangTai(@Field("xiangmuId")xiangmuId: Int, @Field("piciId")piciId: Int, @Field("duizhangxuehao")duizhangxuehao: String): Observable<HttpResult<String>>

    @POST("studenttask/delXD")
    @FormUrlEncoded
    fun delXD(@Field("xiangmuId")xiangmuId: Int, @Field("piciId")piciId: Int, @Field("duizhangxuehao")duizhangxuehao: String): Observable<HttpResult<String>>
}