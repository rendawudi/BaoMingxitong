package com.example.rd.baomingxitong.ViewModel

import android.content.Intent
import android.view.View
import android.widget.Toast
import com.example.rd.baomingxitong.Http.RetrofitHelper
import com.example.rd.baomingxitong.Model.UserModel
import com.example.rd.baomingxitong.base.MyApp
import com.example.rd.baomingxitong.databinding.ActivityLoginXsBinding
import com.example.rd.baomingxitong.entity.UserInfo
import com.example.rd.baomingxitong.entity.UserLogin
import com.example.rd.baomingxitong.fuzhu.ProgressHandler
import com.example.rd.baomingxitong.fuzhu.ProgressObservable
import com.example.rd.baomingxitong.fuzhu.SubscribeOnNextListener
import com.example.rd.baomingxitong.views.LoginActivityXs
import com.example.rd.baomingxitong.views.RegisterActivity
import com.example.rd.baomingxitong.views.TaskListActivity
import retrofit2.Retrofit

/**
 * Created by rd on 2017/9/27.
 */

object StudentViewModel
{
    lateinit var userInfo: UserInfo
    lateinit var binding: ActivityLoginXsBinding
    lateinit var loginXsActivity: LoginActivityXs

    fun init(activityLoginXsBinding: ActivityLoginXsBinding,activityXs: LoginActivityXs)
    {
        binding = activityLoginXsBinding
        loginXsActivity = activityXs
        binding.xsModel = this
        userInfo = UserInfo()

    }

    fun onClickXuesheng(view: View)
    {
        var student: UserLogin = UserLogin(userInfo.xuehao.get(), userInfo.mima.get())
        ProgressHandler.initProgressDialog(loginXsActivity)
        var mOnNextListener = object :SubscribeOnNextListener<String?>
        {
            override fun onNext(t: String?) {
                if (t.equals("200")) {
                    MyApp.instances.querenuser(false)
                    MyApp.instances.pdLogin(loginXsActivity)
                    MyApp.instances.sftecher = false
                }
                else if(t.equals("201"))
                {
                    MyApp.instances.querenuser(true)
                    MyApp.instances.pdLoginT(loginXsActivity)
                    MyApp.instances.sftecher = true
                }
                else {
                    MyApp.instances.ClearUser()
                }
            }

            override fun onComplete() {
            }

            override fun onStart() {
            }
        }
        RetrofitHelper.insance.getUserLogin(ProgressObservable<String?>(loginXsActivity,mOnNextListener),student)
    }

    fun onClickLaoshi(view: View)
    {

    }

    fun onClickXsDl(view: View)
    {
        loginXsActivity.startActivity(Intent(loginXsActivity,RegisterActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    fun tiaozhuan()
    {
        loginXsActivity.startActivity(Intent(loginXsActivity,TaskListActivity::class.java))
    }
}