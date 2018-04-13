package com.example.rd.baomingxitong.views

import android.app.Fragment
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rd.baomingxitong.R
import com.example.rd.baomingxitong.ViewModel.TaskViewModel
import com.example.rd.baomingxitong.databinding.TasklayoutBinding

class TaskActivity :  Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var taskbind: TasklayoutBinding = DataBindingUtil.inflate(inflater,R.layout.tasklayout,container,false)
        TaskViewModel.init(taskbind,this)
        return taskbind.root
    }

}
