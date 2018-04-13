package com.example.rd.baomingxitong.Http

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.rd.baomingxitong.FileAndShipinView.MainActivity
import com.example.rd.baomingxitong.GreenDao.LTXiangmuDao
import com.example.rd.baomingxitong.Model.TaskJoinedModel
import com.example.rd.baomingxitong.Model.TaskModel
import com.example.rd.baomingxitong.Model.UserModel
import com.example.rd.baomingxitong.ViewModel.*
import com.example.rd.baomingxitong.base.BaseActivity
import com.example.rd.baomingxitong.base.GreenDaoUse
import com.example.rd.baomingxitong.base.MyApp
import com.example.rd.baomingxitong.entity.*
import com.example.rd.baomingxitong.entity.LiaoTian.LTXiangmu
import com.example.rd.baomingxitong.entity.LiaoTian.LTXmHttp
import com.example.rd.baomingxitong.entity.Task.*
import com.example.rd.baomingxitong.entity.Xuexiao.Banji
import com.example.rd.baomingxitong.entity.Xuexiao.Pici
import com.example.rd.baomingxitong.entity.Xuexiao.Xueyuan
import com.example.rd.baomingxitong.views.LoginActivityXs
import com.example.rd.baomingxitong.views.RegisterActivity
import com.example.rd.jk1504mvvm.httpException.ApiException
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.example.rd.baomingxitong.entity.Xuexiao.Zhuanye
import com.example.rd.baomingxitong.fuzhu.ProgressObservable
import com.example.rd.baomingxitong.fuzhu.SocketClient
import io.reactivex.Observable
import io.reactivex.Observer
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by rd on 2017/9/27.
 */

class RetrofitHelper
{
    companion object {
        private val DEFAULT_TIMEOUT: Long = 10
        public val insance: RetrofitHelper = RetrofitHelper()
    }

    private var retrofit: Retrofit
    private var userApi: UserApi
    private var taskApi: TaskApi
    private var builder: OkHttpClient.Builder

    constructor()
    {
        builder = OkHttpClient.Builder()

        var logInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor(HttpLogger())
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        builder.connectTimeout(RetrofitHelper.DEFAULT_TIMEOUT,TimeUnit.SECONDS)
                .addNetworkInterceptor(logInterceptor)
                .addInterceptor(LoadInterceptor())
                .addInterceptor(ReceiveInterceptor())

        retrofit = Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(UserApi.BASE_URL)
                .build()

        userApi = retrofit.create(UserApi::class.java)
        taskApi = retrofit.create(TaskApi::class.java)
    }

    fun getUserLogin(observer: ProgressObservable<String?>, userLogin: UserLogin)
    {
        var gson: Gson = Gson()
        var userJson = gson.toJson(userLogin)
        Log.d("json",userJson)
        var requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),userJson)
        userApi.getUserLogin(requestBody)
                .map {
                    t ->
                    if (t.code ==200)
                    {
                        t.data = "200"
                        return@map t.data
                    }
                    else if (t.code == 201)
                    {
                        t.data = "201"
                        return@map t.data
                    }
                    else
                        throw ApiException(101,ApiException.getApiMsg(101))
                }
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (observer)
    }
    fun getUserMsg(observer: ProgressObservable<UserHttpLoginGet?>)
    {
        userApi.getUserMsg()
                .map {
                    t ->
                    if (t.code ==200)
                    {
                        return@map t.data
                    }
                    else
                    {
                        throw ApiException(t.code,ApiException.getApiMsg(t.code))
                    }
                }
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (observer)
    }

    fun UpdataUser(observer: ProgressObservable<String?>,userRsend: UserHttpRegisterSend)
    {

        var gson: Gson = Gson()
        var registerJson = gson.toJson(userRsend)
        var requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),registerJson)
        userApi.updataUser(requestBody)
                .map {
                    t ->
                    if (t.code ==200)
                    {
                        return@map t.data
                    }
                    else
                        throw ApiException(103,ApiException.getApiMsg(103))
                }
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (observer)
    }

    fun getTeacherMsg(observer: ProgressObservable<TeacherHttpLoginGet?>)
    {
        userApi.getTeacherMsg()
                .map {
                    t ->
                    if (t.code ==200)
                    {
                        return@map t.data
                    }
                    else
                    {
                        throw ApiException(t.code,ApiException.getApiMsg(t.code))
                    }
                }
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (observer)
    }

    fun UserRigister(observer: ProgressObservable<String?>,userRsend: UserHttpRegisterSend)
    {

        var gson: Gson = Gson()
        var registerJson = gson.toJson(userRsend)
        var requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),registerJson)
        userApi.UserRegister(requestBody)
                .map {
                    t ->
                    if (t.code ==200)
                    {
                        t.data = "200"
                        return@map t.data
                    }

                    else
                        throw ApiException(t.code,ApiException.getApiMsg(t.code))
                }
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (observer)
    }
    fun getXueYuans()
    {
        userApi.getXueyuans()
                .map {
                    t ->
                    if (t.code ==200)
                    {
                        return@map t.data
                    }
                    else
                        throw ApiException(t.code,ApiException.getApiMsg(t.code))
                }
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    t: List<Xueyuan>? ->
                    UserViewModel.xueYuanXs(t)
                }
    }

    fun getZhuanyes(xuyuanId: Int)
    {
        userApi.getZhuanyes(xuyuanId)
                .map {
                    t ->
                    if (t.code ==200)
                    {
                        return@map t.data
                    }
                    else
                        throw ApiException(t.code,ApiException.getApiMsg(t.code))
                }
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    t: List<Zhuanye>? ->
                    UserViewModel.zhuanYEXs(t)
                }
    }

    fun getBanjis(zhuanyeId: Int)
    {
        userApi.getBanjis(zhuanyeId)
                .map {
                    t ->
                    if (t.code ==200)
                    {
                        return@map t.data
                    }
                    else
                        throw ApiException(t.code,ApiException.getApiMsg(t.code))
                }
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    t: List<Banji>? ->
                    UserViewModel.banJiXs(t)
                }
    }



    fun getSimpleTasks(xueyuanId: Int?)
    {
        if (xueyuanId!=null)
        taskApi.getsptasks(xueyuanId)
                .map {
                    t ->
                    if (t.code ==200)
                    {
                        return@map t.data
                    }
                    else
                        throw ApiException(t.code,ApiException.getApiMsg(t.code))
                }
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    t: List<Pici>? ->
                    TaskModel.chuliTask(t)
                }
    }

    fun getTaskMainList(taskIdList: TaskIdList, taskSpList: List<Pici>?)
    {
        var gson: Gson = Gson()
        var taskJson = gson.toJson(taskIdList)
        var requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),taskJson)
        taskApi.getTaskMains(requestBody)
                .map {
                    t ->
                    if (t.code ==200)
                    {
                        return@map t.data
                    }
                    else
                        throw ApiException(t.code,ApiException.getApiMsg(t.code))
                }
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    t: List<TaskmainGet>? -> TaskListViewModel.TaskMainXs(t, taskSpList)
                }
    }

    fun getPermission(xiangmuId: Int, piciId: Int, activity: MainActivity)
    {
            taskApi.getPermission(xiangmuId, piciId)
                    .map {
                        t ->
                        if (t.code ==200)
                        {
                            return@map t.data
                        }
                        else
                            throw ApiException(t.code,ApiException.getApiMsg(t.code))
                    }
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        t: Boolean? ->
                        //更改项目加入还是填写项目表
                        activity.setJiaruButton(t);
                    }
    }

    fun qiangXM(xiangmuId: Int, piciId: Int, activity: MainActivity)
    {
        taskApi.qiangXM(xiangmuId, piciId)
                .map {
                    t ->
                    if (t.code ==200)
                    {
                        return@map t.data
                    }
                    else
                        throw ApiException(t.code,ApiException.getApiMsg(t.code))
                }
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    t: Int? ->
                    //是否报名成功
                    activity.pdSfSucess(t)
                }
    }

    fun InsertXsTaskList(taskXsList: List<TaskDuiyuan>)
    {
        var gson: Gson = Gson()
        var taskJson = gson.toJson(taskXsList)
        var requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),taskJson)
        taskApi.insertStdXiaoDui(requestBody)
                .map {
                    t ->
                    if (t.code ==200)
                    {
                        return@map t.data
                    }
                    else
                        throw ApiException(t.code,ApiException.getApiMsg(t.code))
                }
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    t: List<TaskDuiyuan>? -> TaskXiaoDuiViewModel.ErrorTixing(t)
                         },
                        {
                            t: Throwable? -> TaskXiaoDuiViewModel.ErrorTixing(ArrayList<TaskDuiyuan>())
                        })
    }

    fun getPici()
    {
        userApi.getPicis()
                .map {
                    t ->
                    if (t.code ==200)
                    {
                        return@map t.data
                    }
                    else
                        throw ApiException(t.code,ApiException.getApiMsg(t.code))
                }
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    t: List<Pici>? ->
                    TaskViewModel.PiciXs(t)
                }
    }

    fun InsertXsXiangmu(task: TaskXueShengXiangMu)
    {
        var gson: Gson = Gson()
        var taskJson = gson.toJson(task)
        var requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),taskJson)
        taskApi.insertStdXiangmu(requestBody)
                .map {
                    t ->
                    if (t.code ==200)
                    {
                        return@map t.data
                    }
                    else
                        throw ApiException(t.code,ApiException.getApiMsg(t.code))
                }
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    t: List<TaskDuiyuan>? -> TaskViewModel.ErrorTixing(t)
                },
                        {
                            t: Throwable? ->
                        })
    }

    fun getTaskJoined(xuehao: String)
    {
        taskApi.getTaskJoinedMsg(xuehao)
                .map {
                    t ->
                    if (t.code ==200)
                    {
                        return@map t.data
                    }
                    else
                        throw ApiException(t.code,ApiException.getApiMsg(t.code))
                }
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                        {
                            t: LTXmHttp? -> TaskJoinedModel.setTaskJoined(t)
                        }
                )
    }

    fun getXD(xiangmuId: Int,piciId: Int, duizhangxuehao: String)
    {
        taskApi.getXDMsg(xiangmuId, piciId, duizhangxuehao)
                .map {
                    t ->
                    if (t.code ==200)
                    {
                        return@map t.data
                    }
                    else
                    {
                        throw ApiException(t.code,ApiException.getApiMsg(t.code))
                    }
                }
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                        {
                            t -> TaskViewModelXS.jiazai(t)
                        }
                )
    }
    fun updataLSYJ(context: Context,taskUpdataLSYJ: TaskUpdataLSYJ)
    {
        var gson: Gson = Gson()
        var taskJson = gson.toJson(taskUpdataLSYJ)
        var requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),taskJson)
        taskApi.updataLSYJ(requestBody)
                .map {
                    t ->
                    if (t.code ==200)
                    {
                        return@map t.data
                    }
                    else
                    {
                        throw ApiException(t.code,ApiException.getApiMsg(t.code))
                    }
                }
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                        {
                            t: String? -> Toast.makeText(context,"更新意见成功",Toast.LENGTH_SHORT).show()
                        }
                )
    }
    fun updataZhuangTai(context: Context,xiangmuId: Int,piciId: Int, duizhangxuehao: String)
    {
        taskApi.updataZhuangTai(xiangmuId, piciId, duizhangxuehao)
                .map {
                    t ->
                    if (t.code ==200)
                    {
                        return@map t.data
                    }
                    else
                    {
                        throw ApiException(t.code,ApiException.getApiMsg(t.code))
                    }
                }
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                        {
                            t: String? ->
                            Toast.makeText(context,"项目通过审核",Toast.LENGTH_SHORT).show()
                            var xiangmu = GreenDaoUse.mDaoSession.queryBuilder(LTXiangmu::class.java).where(LTXiangmuDao.Properties.XiangmuId.eq(xiangmuId)).unique()
                            xiangmu.zhuangtai = 1
                            GreenDaoUse.mTaskLT.update(xiangmu)
                            TaskJoinedListVM.setJoinedTasks()
                        }
                )
    }
    fun delXD(context: Context,xiangmuId: Int,piciId: Int, duizhangxuehao: String)
    {
        taskApi.delXD(xiangmuId, piciId, duizhangxuehao)
                .map {
                    t ->
                    if (t.code ==200)
                    {
                        return@map t.data
                    }
                    else
                    {
                        throw ApiException(t.code,ApiException.getApiMsg(t.code))
                    }
                }
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                        {
                            t: String? ->
                            Toast.makeText(context,"项目删除成功，并将错误发送给队长",Toast.LENGTH_SHORT).show()
                            var xiangmu = GreenDaoUse.mDaoSession.queryBuilder(LTXiangmu::class.java).where(LTXiangmuDao.Properties.XiangmuId.eq(xiangmuId)).unique()
                            GreenDaoUse.mTaskLT.delete(xiangmu)
                            TaskJoinedListVM.setJoinedTasks()
                        })
    }
}
class HttpLogger: HttpLoggingInterceptor.Logger
{
    override fun log(message: String?) {
        Log.d("请求和响应",message)
    }
}

