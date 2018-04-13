package com.example.rd.baomingxitong.base

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.util.SparseArray
import android.widget.Toast
import cn.finalteam.okhttpfinal.OkHttpFinal
import cn.finalteam.okhttpfinal.OkHttpFinalConfiguration
import cn.finalteam.okhttpfinal.Part
import com.example.rd.baomingxitong.Http.RetrofitHelper
import com.example.rd.baomingxitong.Model.TaskJoinedModel
import com.example.rd.baomingxitong.Model.TaskModel
import com.example.rd.baomingxitong.Model.UserModel
import com.example.rd.baomingxitong.ViewModel.StudentViewModel
import com.example.rd.baomingxitong.ViewModel.TaskJoinedListVM
import com.example.rd.baomingxitong.entity.FileAndShipin.Lesson
import com.example.rd.baomingxitong.entity.LiaoTian.LTXiangmu
import com.example.rd.baomingxitong.entity.Task.TaskmainGet
import com.example.rd.baomingxitong.entity.TeacherHttpLoginGet
import com.example.rd.baomingxitong.entity.UserHttpLoginGet
import com.example.rd.baomingxitong.fuzhu.ProgressHandler
import com.example.rd.baomingxitong.fuzhu.ProgressObservable
import com.example.rd.baomingxitong.fuzhu.SubscribeOnNextListener
import com.example.rd.baomingxitong.views.TaskListActivity
import com.example.rd.baomingxitong.views.zhujiemian
import com.google.gson.Gson
import okhttp3.Headers
import okhttp3.Interceptor
import java.util.ArrayList

/**
 * Created by rd on 2017/9/27.
 */
class MyApp: Application()
{
    lateinit var lesson: Lesson
    lateinit var xiangmuXs: TaskmainGet

    lateinit var context: Context
    var userlogined = false
    @Volatile var user: UserHttpLoginGet ?=null
    @Volatile var teacher: TeacherHttpLoginGet ?= null
    @Volatile var sftecher = false
    var NetBoolean = false
    override fun onCreate() {
        super.onCreate()
        GreenDaoUse.init(this)
        context = applicationContext
        instances = this
        net()
        usercunzai()
        initOkHttpFinal()
    }

    fun net()
    {
        var connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetInfo: NetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetInfo!=null)
        {
            NetBoolean = true
        }
    }

    fun usercunzai()
    {
        var sharedPreferences1: SharedPreferences = context.getSharedPreferences("usersessionid", Context.MODE_PRIVATE)
        var  xx = sharedPreferences1.getBoolean("sfteacher",false)
        sftecher = xx
        userlogined = true
    }

    fun querenuser(sf: Boolean)
    {
        userlogined = true
        var sharedPreferences1: SharedPreferences = context.getSharedPreferences("usersessionid", Context.MODE_PRIVATE)
        var editor1: SharedPreferences.Editor = sharedPreferences1.edit()
        editor1.putBoolean("usersessionid",true)
        editor1.putBoolean("sfteacher",sf)
        editor1.commit()
        Log.d("share:",sharedPreferences1.getBoolean("usersessionid",false).toString())
        Log.d("share:",sharedPreferences1.getBoolean("sfteacher",false).toString())
    }

    fun sfLgg(sfLg: Boolean)
    {
        if (sfLg)
        {
            getUser()
            userlogined=true
            if (user==null&&teacher==null)
            {
                ClearUser()
                Toast.makeText(context,"请登录",Toast.LENGTH_LONG).show()
                this.startActivity(android.content.Intent(this,com.example.rd.baomingxitong.views.LoginActivityXs::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            }
                if (sftecher)
                    RetrofitHelper.insance.getTaskJoined(MyApp.instances.teacher!!.gonghao!!)
                else
                    RetrofitHelper.insance.getTaskJoined(MyApp.instances.user!!.xuehao!!)
        }
        else
        {
            ClearUser()
            Toast.makeText(context,"请登录",Toast.LENGTH_LONG).show()
        }
    }

    fun getUser()
    {
        if (sftecher)
        teacher = HuodeUserJsonT()
        else
        user = HuodeUserJson()
    }

    companion object {
        lateinit var instances: MyApp
    }

    fun ChuliUserJson(userHttpLoginGet: UserHttpLoginGet?) {
        if (userHttpLoginGet == null)
        {
            userlogined = false
            sfLgg(false)
        }
        else {
            userlogined = true
            var gson: Gson = Gson()
            var userJson = gson.toJson(userHttpLoginGet)
            var sharedPreferences: SharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)
            var editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("user", userJson)
            editor.commit()
            sfLgg(true)
            context.startActivity(Intent(context, zhujiemian::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    fun ChuliUserJsonT(userHttpLoginGet: TeacherHttpLoginGet?) {
        if (userHttpLoginGet == null)
        {
            userlogined = false
            sfLgg(false)
        }
        else {
            userlogined = true
            var gson: Gson = Gson()
            var userJson = gson.toJson(userHttpLoginGet)
            var sharedPreferences: SharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)
            var editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("user", userJson)
            editor.commit()
            sfLgg(true)
            context.startActivity(Intent(context, zhujiemian::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    fun HuodeUserJson(): UserHttpLoginGet?
    {
        var gson: Gson = Gson()
        var sharePreferences: SharedPreferences = context.getSharedPreferences("user",Context.MODE_PRIVATE)
        var userJson = sharePreferences.getString("user","")
        if (userJson!= null&&!userJson.equals(""))
        {
            var userHttp = gson.fromJson(userJson,UserHttpLoginGet::class.java)
            return userHttp
        }
        else
        {
            return null
        }
    }

    fun HuodeUserJsonT(): TeacherHttpLoginGet?
    {
        var gson: Gson = Gson()
        var sharePreferences: SharedPreferences = context.getSharedPreferences("user",Context.MODE_PRIVATE)
        var userJson = sharePreferences.getString("user","")
        if (userJson!= null&&!userJson.equals(""))
        {
            var userHttp = gson.fromJson(userJson,TeacherHttpLoginGet::class.java)
            return userHttp
        }
        else
        {
            return null
        }
    }

    fun pdLogin(acontext: Context?)
    {
            if (NetBoolean&&userlogined)
            {
                ProgressHandler.initProgressDialog(acontext!!)
                var mOnNextListener = object : SubscribeOnNextListener<UserHttpLoginGet?>
                {
                    override fun onNext(t: UserHttpLoginGet?) {
                        MyApp.instances.ChuliUserJson(t)
                    }

                    override fun onComplete() {
                        ProgressHandler.disMissProgressDialog()
                        TaskModel.getSpTask()
                        acontext.startActivity(Intent(acontext,zhujiemian::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                    }

                    override fun onStart() {
                        ProgressHandler.showProgressDialog()
                    }
                }
                RetrofitHelper.insance.getUserMsg(ProgressObservable(this,mOnNextListener))
            }
        }

    fun pdLoginT(acontext: Context?)
    {
        if (NetBoolean&&userlogined)
        {
            ProgressHandler.initProgressDialog(acontext!!)
            var mOnNextListener = object : SubscribeOnNextListener<TeacherHttpLoginGet?>
            {
                override fun onNext(t: TeacherHttpLoginGet?) {
                    MyApp.instances.ChuliUserJsonT(t)
                }

                override fun onComplete() {
                    ProgressHandler.disMissProgressDialog()
                    TaskModel.getSpTask()
                    acontext.startActivity(Intent(acontext,zhujiemian::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                }

                override fun onStart() {
                    ProgressHandler.showProgressDialog()
                }
            }
            RetrofitHelper.insance.getTeacherMsg(ProgressObservable(this,mOnNextListener))
        }
    }

    fun ClearUser()
    {
        var sharedPreferences: SharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.commit()
        var sharedPreferences1: SharedPreferences = context.getSharedPreferences("usersessionid", Context.MODE_PRIVATE)
        var editor1: SharedPreferences.Editor = sharedPreferences1.edit()
        editor1.putBoolean("usersessionid",false)
        editor1.putBoolean("sfteacher",false)
        editor1.commit()
        userlogined = false
    }


    private fun initOkHttpFinal() {
        val commomParams = ArrayList<Part>()
        val commonHeaders = Headers.Builder().build()

        val interceptorList = ArrayList<Interceptor>()
        val builder = OkHttpFinalConfiguration.Builder()
                .setCommenParams(commomParams)
                .setCommenHeaders(commonHeaders)
                .setTimeout(35000)
                .setInterceptors(interceptorList)
                //.setCookieJar(CookieJar.NO_COOKIES)
                //.setCertificates(...)
                //.setHostnameVerifier(new SkirtHttpsHostnameVerifier())
                .setDebug(true)
        //        addHttps(builder);
        OkHttpFinal.getInstance().init(builder.build())
    }


}