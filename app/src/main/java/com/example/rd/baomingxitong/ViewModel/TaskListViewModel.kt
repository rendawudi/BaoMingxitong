package com.example.rd.baomingxitong.ViewModel

import android.content.Intent
import android.databinding.ObservableField
import android.view.View
import com.example.rd.baomingxitong.BR
import com.example.rd.baomingxitong.FileAndShipinView.MainActivity
import com.example.rd.baomingxitong.Model.TaskModel
import com.example.rd.baomingxitong.R
import com.example.rd.baomingxitong.RecyclerView.ItemViewHolder
import com.example.rd.baomingxitong.RecyclerView.MyAdapter
import com.example.rd.baomingxitong.base.MyApp
import com.example.rd.baomingxitong.databinding.ListlayoutBinding
import com.example.rd.baomingxitong.entity.Task.TaskMsgInView
import com.example.rd.baomingxitong.entity.Task.TaskmainGet
import com.example.rd.baomingxitong.entity.Xuexiao.Pici
import com.example.rd.baomingxitong.views.TaskListActivity
import com.example.rd.baomingxitong.views.TaskXsActivity
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by rd on 2017/9/29.
 */
object TaskListViewModel {
    var binding: ListlayoutBinding ?=null
    lateinit var mActivity: TaskListActivity
    var myAdapter: MyAdapter<TaskMsgInView>?=null

    fun init(binding: ListlayoutBinding, activity: TaskListActivity) {
        this.binding = binding
        this.mActivity = activity
        this.binding!!.listModel = this
        TaskModel.init(this)
        if (myAdapter!=null)
        {
            this.binding!!.lvUserlist.adapter = myAdapter
            this.binding!!.lvUserlist.adapter.notifyDataSetChanged()
        }
    }

    fun TaskMainXs(tasks: List<TaskmainGet>?, taskSpList: List<Pici>?) {
        if (tasks != null) {
            var Tasklist: ArrayList<TaskMsgInView> = ArrayList()
            for (i in tasks) {
                var task: TaskMsgInView = TaskMsgInView(ObservableField(i.xiangmu), ObservableField(i.jianjie))
                Tasklist.add(task)
            }
            val variabaleId: Int = BR.Task
            myAdapter = object : MyAdapter<TaskMsgInView>(R.layout.taskitem, variabaleId) {
                override fun convert(holder: ItemViewHolder, t: TaskMsgInView, position: Int) {
                    holder.setBinding(variabaleId, t)
                    holder.itemView.setOnClickListener {
                        if (taskSpList!=null)
                        for (xx in taskSpList)
                        {
                            if (xx.piciId.equals(tasks[position].piciId))
                            {
                                var t = Date(xx.endTime.toLong())
                                tasks[position].endTime = t
                                var s = Date(xx.startTime.toLong())
                                tasks[position].startTime = s
                                break
                            }
                        }
                        TaskXiaoDuiViewModel.setTask(tasks[position])
                        MyApp.instances.xiangmuXs = tasks[position]
                        mActivity.startActivity(Intent(mActivity.activity,MainActivity::class.java))
                    }
                }
            }
            myAdapter!!.setDatas(Tasklist)
            if (this.binding!=null)
            {
                this.binding!!.lvUserlist.adapter = myAdapter
                this.binding!!.lvUserlist.adapter.notifyDataSetChanged()
            }
        }
    }

    fun piciIdSelect(view: View)
    {

    }

}