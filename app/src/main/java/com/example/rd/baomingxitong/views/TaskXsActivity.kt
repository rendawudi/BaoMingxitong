package com.example.rd.baomingxitong.views

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.rd.baomingxitong.R
import com.example.rd.baomingxitong.ViewModel.TaskXiaoDuiViewModel
import com.example.rd.baomingxitong.databinding.ActivityTaskXsBinding

class TaskXsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var taskXsBingding: ActivityTaskXsBinding = DataBindingUtil.setContentView(this,R.layout.activity_task_xs)
        TaskXiaoDuiViewModel.init(taskXsBingding,this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
