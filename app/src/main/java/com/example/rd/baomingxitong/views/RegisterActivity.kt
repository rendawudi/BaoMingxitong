package com.example.rd.baomingxitong.views

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.rd.baomingxitong.R
import com.example.rd.baomingxitong.ViewModel.UserViewModel
import com.example.rd.baomingxitong.base.BaseActivity
import com.example.rd.baomingxitong.databinding.ActivityRegisterBinding
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

class RegisterActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: ActivityRegisterBinding = DataBindingUtil.setContentView(this,R.layout.activity_register)
        var userViewModel: UserViewModel = UserViewModel
        userViewModel.init(binding,this)
    }
}
