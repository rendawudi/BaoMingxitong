package com.example.rd.baomingxitong.views

import android.app.Fragment
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.rd.baomingxitong.BR
import com.example.rd.baomingxitong.Model.TaskModel
import com.example.rd.baomingxitong.R
import com.example.rd.baomingxitong.RecyclerView.ItemViewHolder
import com.example.rd.baomingxitong.RecyclerView.MyAdapter
import com.example.rd.baomingxitong.ViewModel.TaskListViewModel
import com.example.rd.baomingxitong.ViewModel.TaskViewModel
import com.example.rd.baomingxitong.ViewModel.TaskXiaoDuiViewModel
import com.example.rd.baomingxitong.base.BaseActivity
import com.example.rd.baomingxitong.base.MyApp
import com.example.rd.baomingxitong.databinding.ListlayoutBinding
import com.example.rd.baomingxitong.entity.Task.TaskMsgInView
import kotlin.concurrent.thread

class TaskListActivity : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var listBinding: ListlayoutBinding = DataBindingUtil.inflate(inflater, R.layout.listlayout,container,false)
        listBinding.lvUserlist.layoutManager = LinearLayoutManager(activity)
        TaskListViewModel.init(listBinding,this)
        Log.d("TaskListActivity:","onCreate")
        return listBinding.root
    }

    override fun onResume() {
        super.onResume()
    }
}
