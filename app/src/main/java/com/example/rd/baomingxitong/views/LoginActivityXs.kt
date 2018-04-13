package com.example.rd.baomingxitong.views

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.example.rd.baomingxitong.R
import com.example.rd.baomingxitong.ViewModel.StudentViewModel
import com.example.rd.baomingxitong.base.MyApp
import com.example.rd.baomingxitong.databinding.ActivityLoginXsBinding
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import kotlin.concurrent.thread

class LoginActivityXs : RxAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: ActivityLoginXsBinding = DataBindingUtil.setContentView(this,R.layout.activity_login_xs)
        StudentViewModel.init(binding,this)
        if (!MyApp.instances.sftecher)
        MyApp.instances.pdLogin(this)
        else
        MyApp.instances.pdLoginT(this)
    }
}
