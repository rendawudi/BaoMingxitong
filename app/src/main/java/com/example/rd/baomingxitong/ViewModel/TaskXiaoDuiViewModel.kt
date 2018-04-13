package com.example.rd.baomingxitong.ViewModel

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View
import android.widget.Toast
import com.example.rd.baomingxitong.Http.RetrofitHelper
import com.example.rd.baomingxitong.Model.TaskChuli
import com.example.rd.baomingxitong.base.MyApp
import com.example.rd.baomingxitong.databinding.ActivityTaskXsBinding
import com.example.rd.baomingxitong.entity.Task.TaskDuiyuan
import com.example.rd.baomingxitong.entity.Task.TaskMsgXsInView
import com.example.rd.baomingxitong.entity.Task.TaskmainGet
import com.example.rd.baomingxitong.views.TaskXsActivity

/**
 * Created by rd on 2017/10/1.
 */
object TaskXiaoDuiViewModel
{
    lateinit var taskMain: TaskmainGet
    lateinit var binding: ActivityTaskXsBinding
    lateinit var xsActivity: TaskXsActivity
    lateinit var taskXsInView: TaskMsgXsInView

    fun init(binding: ActivityTaskXsBinding,xsActivity: TaskXsActivity)
    {
        this.binding = binding
        this.xsActivity = xsActivity
        binding.xsTjModel = this
        taskXsInView = TaskMsgXsInView()
        xsActivity.supportActionBar?.setHomeButtonEnabled(true)
        xsActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        inittaskMsg()
    }

    fun inittaskMsg()
    {
        taskXsInView.xiangmu.set(taskMain.xiangmu)
        taskXsInView.laoshi.set(taskMain.laoshi)
        taskXsInView.yiyourenshu.set(taskMain.dqrenshu)
        taskXsInView.renshuxianzhi.set(taskMain.renshu)
        taskXsInView.jianjie.set(taskMain.jianjie)

    }

    fun setTask(task: TaskmainGet)
    {
        taskMain = task
    }

    fun onClickQueDing(view: View)
    {
        var tiXing: AlertDialog.Builder = AlertDialog.Builder(xsActivity)
        tiXing.setTitle("提交")
        tiXing.setMessage("请确定后提交")
        tiXing.setPositiveButton("确定",{
            dialogInterface, i ->  tijiao()
        })
        tiXing.setNegativeButton("取消",null)
        var dialog: AlertDialog = tiXing.create()
        dialog.show()

    }

    fun tijiao()
    {
        var taskDuiyuanList: ArrayList<TaskDuiyuan> = ArrayList()
        if (!taskXsInView.xuehao1.get().equals(""))
        {
            var taskDuiyuan: TaskDuiyuan = TaskDuiyuan()
            taskDuiyuan.xuehao = taskXsInView.xuehao1.get()
            taskDuiyuan.fengong = taskXsInView.fengong1.get()
            taskDuiyuan.gonghao = taskMain.gonghao
            taskDuiyuan.duizhangmingzi = taskXsInView.shenbaoren.get()
            taskDuiyuan.banjimingzi = taskXsInView.banji.get()
            taskDuiyuanList.add(taskDuiyuan)
        }
        if (!taskXsInView.xuehao2.get().equals(""))
        {
            var taskDuiyuan: TaskDuiyuan = TaskDuiyuan()
            taskDuiyuan.xuehao = taskXsInView.xuehao2.get()
            taskDuiyuan.fengong = taskXsInView.fengong2.get()
            taskDuiyuanList.add(taskDuiyuan)
        }
        if (!taskXsInView.xuehao3.get().equals(""))
        {
            var taskDuiyuan: TaskDuiyuan = TaskDuiyuan()
            taskDuiyuan.xuehao = taskXsInView.xuehao3.get()
            taskDuiyuan.fengong = taskXsInView.fengong3.get()
            taskDuiyuanList.add(taskDuiyuan)
        }
        if (!taskXsInView.xuehao4.get().equals(""))
        {
            var taskDuiyuan: TaskDuiyuan = TaskDuiyuan()
            taskDuiyuan.xuehao = taskXsInView.xuehao4.get()
            taskDuiyuan.fengong = taskXsInView.fengong4.get()
            taskDuiyuanList.add(taskDuiyuan)
        }
        if (!taskXsInView.xuehao5.get().equals(""))
        {
            var taskDuiyuan: TaskDuiyuan = TaskDuiyuan()
            taskDuiyuan.xuehao = taskXsInView.xuehao5.get()
            taskDuiyuan.fengong = taskXsInView.fengong5.get()
            taskDuiyuanList.add(taskDuiyuan)
        }
        TaskChuli.TaskXsChuli(taskMain, taskXsInView,taskDuiyuanList)
    }

    fun ErrorTixing(taskDuiyuanList: List<TaskDuiyuan>?)
    {
        if (taskDuiyuanList!=null)
        {
            if (!taskDuiyuanList.size.equals(0))
            {
                var tiXing: AlertDialog.Builder = AlertDialog.Builder(xsActivity)
                var buffer: StringBuilder = StringBuilder()
                buffer.append("以下学号已经报名过项目：\n")
                for (i in taskDuiyuanList)
                {
                    buffer.append(i.xuehao).append("\n")
                }
                tiXing.setTitle("报名失败")
                tiXing.setMessage(buffer)
                tiXing.setPositiveButton("确定",null)
                var dialog: AlertDialog = tiXing.create()
                dialog.show()
            }
            else
            {
                var tiXing: AlertDialog.Builder = AlertDialog.Builder(xsActivity)
                tiXing.setTitle("报名成功")
                tiXing.setMessage("项目报名成功")
                tiXing.setPositiveButton("确定",null)
                var dialog: AlertDialog = tiXing.create()
                dialog.show()
                if (MyApp.instances.sftecher)
                    RetrofitHelper.insance.getTaskJoined(MyApp.instances.teacher!!.gonghao!!)
                else
                    RetrofitHelper.insance.getTaskJoined(MyApp.instances.user!!.xuehao!!)
            }
        }
        else
        {
            var tiXing: AlertDialog.Builder = AlertDialog.Builder(xsActivity)
            tiXing.setTitle("报名成功")
            tiXing.setMessage("项目报名成功")
            tiXing.setPositiveButton("确定",null)
            var dialog: AlertDialog = tiXing.create()
            dialog.show()
            if (MyApp.instances.sftecher)
                RetrofitHelper.insance.getTaskJoined(MyApp.instances.teacher!!.gonghao!!)
            else
                RetrofitHelper.insance.getTaskJoined(MyApp.instances.user!!.xuehao!!)
        }
    }
}