package com.example.rd.baomingxitong.ViewModel

import android.app.AlertDialog
import android.databinding.ObservableField
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.rd.baomingxitong.Http.RetrofitHelper
import com.example.rd.baomingxitong.Model.TaskChuli
import com.example.rd.baomingxitong.R
import com.example.rd.baomingxitong.base.MyApp
import com.example.rd.baomingxitong.databinding.TasklayoutBinding
import com.example.rd.baomingxitong.entity.Task.*
import com.example.rd.baomingxitong.entity.UserHttpLoginGet
import com.example.rd.baomingxitong.entity.Xuexiao.Pici
import com.example.rd.baomingxitong.views.TaskActivity
import com.google.gson.Gson

/**
 * Created by rd on 2017/9/24.
 */

object TaskViewModel
{
    lateinit var task: TaskByStudentInView
    lateinit var binding: TasklayoutBinding
    lateinit var mActivity: TaskActivity
    var user: UserHttpLoginGet ?=null
    var piciId: Int = 0
    var piciMingcheng: ObservableField<String> = ObservableField("")

    fun init(tasklayoutBinding: TasklayoutBinding,taskActivity: TaskActivity)
    {
        binding = tasklayoutBinding
        mActivity = taskActivity
        binding.taskModel = this
        task = TaskByStudentInView()
    }
    fun jiazai()
    {

    }

    fun onClickTijiao(view: View)
    {
        if (MyApp.instances.sftecher)
            Toast.makeText(mActivity.activity,"老师无权限提交",Toast.LENGTH_SHORT).show()
        else {
            var queRenDialog: AlertDialog.Builder = AlertDialog.Builder(mActivity.activity)
            queRenDialog.setTitle("提交项目")
            queRenDialog.setMessage("请确认后提交")
            queRenDialog.setNegativeButton("取消", null)
            queRenDialog.setPositiveButton("确认", { dialogInterface, i ->
                onTijiao()
            })
            queRenDialog.show()
        }
    }

    fun onTijiao()
    {
        Log.d("TaskXiangmu::", task.toString())
        var taskDuiyuanList: ArrayList<TaskDuiyuan> = ArrayList()
        var gson: Gson = Gson()
        if (!task.xuehao1.get().equals(""))
        {
            var taskDuiyuan: TaskDuiyuan = TaskDuiyuan()
            taskDuiyuan.xuehao = task.xuehao1.get()
            taskDuiyuan.fengong = task.fengong1.get()
            taskDuiyuan.gonghao = task.laoshigonghao.get()
            taskDuiyuan.banjimingzi = task.banji.get()
            taskDuiyuan.duizhangmingzi = task.shenbaoren.get()
            taskDuiyuan.xiangmu = task.xiangmu.get()
            taskDuiyuanList.add(taskDuiyuan)
        }
        if (!task.xuehao2.get().equals(""))
        {
            var taskDuiyuan: TaskDuiyuan = TaskDuiyuan()
            taskDuiyuan.xuehao = task.xuehao2.get()
            taskDuiyuan.fengong = task.fengong2.get()
            taskDuiyuanList.add(taskDuiyuan)
        }
        if (!task.xuehao3.get().equals(""))
        {
            var taskDuiyuan: TaskDuiyuan = TaskDuiyuan()
            taskDuiyuan.xuehao = task.xuehao3.get()
            taskDuiyuan.fengong = task.fengong3.get()
            taskDuiyuanList.add(taskDuiyuan)
        }
        if (!task.xuehao4.get().equals(""))
        {
            var taskDuiyuan: TaskDuiyuan = TaskDuiyuan()
            taskDuiyuan.xuehao = task.xuehao4.get()
            taskDuiyuan.fengong = task.fengong4.get()
            taskDuiyuanList.add(taskDuiyuan)
        }
        if (!task.xuehao5.get().equals(""))
        {
            var taskDuiyuan: TaskDuiyuan = TaskDuiyuan()
            taskDuiyuan.xuehao = task.xuehao5.get()
            taskDuiyuan.fengong = task.fengong5.get()
            taskDuiyuanList.add(taskDuiyuan)
        }
        var taskMian: TaskMainInHttp = TaskMainInHttp()
        taskMian.dqrenshu = 1;
        taskMian.jianjie = task.jianjie.get()
        taskMian.laoshi = task.laoshi.get()
        taskMian.piciId = piciId
        taskMian.renshu = 1;
        taskMian.xiangmu = task.xiangmu.get()
        var banjilist: BanjiList = BanjiList()
            banjilist.BanjiIds.add(MyApp.instances.user!!.banjiId)
        taskMian.banjiId = gson.toJson(banjilist)
        taskMian.xueyuanId = MyApp.instances.user?.xueyuanId
        var zhuanyelist: ZhuanyeList = ZhuanyeList()
        zhuanyelist.zhuanyeIds.add(MyApp.instances.user!!.zhuanyeId)
        taskMian.zhuanyeId = gson.toJson(zhuanyelist)
        TaskChuli.TaskXsXiangMu(taskMian, task,taskDuiyuanList)
    }

    fun ErrorTixing(taskDuiyuanList: List<TaskDuiyuan>?)
    {
        if (taskDuiyuanList!=null)
        {
            if (!taskDuiyuanList.size.equals(0))
            {
                var tiXing: AlertDialog.Builder = AlertDialog.Builder(mActivity.activity)
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
                var tiXing: AlertDialog.Builder = AlertDialog.Builder(mActivity.activity)
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
            var tiXing: AlertDialog.Builder = AlertDialog.Builder(mActivity.activity)
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

    fun onClickPici(view: View)
    {
        RetrofitHelper.insance.getPici()
    }

    fun PiciXs(xueYuanList:List<Pici>?)
    {
        var dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(mActivity.activity)
        dialogBuilder.setIcon(R.drawable.huda)
        dialogBuilder.setTitle("批次")
        var xueYuast:ArrayList<String> = ArrayList()
        var xueyuanIdList: ArrayList<Int> = ArrayList()
        if (xueYuanList!=null)
        {
            for (i in 0..(xueYuanList.size-1))
            {
                xueYuast.add(xueYuanList[i].piciMingcheng)
                xueyuanIdList.add(xueYuanList[i].piciId)
            }
            var xyList: Array<CharSequence> = xueYuast.toTypedArray()

            dialogBuilder.setItems(xyList,{
                dialogInterface, i ->
                piciMingcheng.set(xyList[i].toString())
                piciId = xueyuanIdList[i]
            })
            dialogBuilder.show()
        }
    }
}