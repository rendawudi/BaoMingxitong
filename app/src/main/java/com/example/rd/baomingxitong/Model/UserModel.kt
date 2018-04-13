package com.example.rd.baomingxitong.Model

import android.content.Context
import android.content.SharedPreferences
import com.example.rd.baomingxitong.Http.RetrofitHelper
import com.example.rd.baomingxitong.ViewModel.StudentViewModel
import com.example.rd.baomingxitong.base.MyApp
import com.example.rd.baomingxitong.entity.UserHttpLoginGet
import com.example.rd.baomingxitong.views.LoginActivityXs
import com.google.gson.Gson

/**
 * Created by rd on 2017/9/27.
 */
object UserModel
{
    lateinit var loginActivity: LoginActivityXs
    lateinit var studentViewModel: StudentViewModel

    fun init(loginActivityXs: LoginActivityXs,studentViewM: StudentViewModel)
    {
        loginActivity = loginActivityXs
        studentViewModel = studentViewM
    }

}
