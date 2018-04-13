package com.example.rd.baomingxitong.views

import android.app.Fragment
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rd.baomingxitong.Http.RetrofitHelper
import com.example.rd.baomingxitong.Model.TaskJoinedModel
import com.example.rd.baomingxitong.R
import com.example.rd.baomingxitong.ViewModel.TaskJoinedListVM
import com.example.rd.baomingxitong.base.MyApp
import com.example.rd.baomingxitong.databinding.ActivityTaskJoinedBinding

class TaskJoinedActivity : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var taskbind: ActivityTaskJoinedBinding = DataBindingUtil.inflate(inflater,R.layout.activity_task_joined,container,false)
        taskbind.lvUserlist.layoutManager = LinearLayoutManager(activity)
        TaskJoinedListVM.init(taskbind,this,activity)
        return taskbind.root
    }


    override fun onResume() {
        super.onResume()
        TaskJoinedListVM.setJoinedTasks()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}
