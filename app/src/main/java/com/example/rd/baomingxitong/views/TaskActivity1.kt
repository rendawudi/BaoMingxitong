package com.example.rd.baomingxitong.views

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.rd.baomingxitong.R
import com.example.rd.baomingxitong.ViewModel.TaskViewModel
import com.example.rd.baomingxitong.ViewModel.TaskViewModelXS
import com.example.rd.baomingxitong.databinding.TasklayoutBinding
import com.example.rd.baomingxitong.databinding.TasklayoutxsBinding

class TaskActivity1 :  AppCompatActivity() {

    lateinit var binding: TasklayoutxsBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
       binding = DataBindingUtil.setContentView(this,R.layout.tasklayoutxs)
        TaskViewModelXS.init(binding,this)
    }

    override fun onResume() {
        super.onResume()
        binding = DataBindingUtil.setContentView(this,R.layout.tasklayoutxs)
        TaskViewModelXS.init(binding,this)
        TaskViewModelXS.shuaxin()
    }
}
