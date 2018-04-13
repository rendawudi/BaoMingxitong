package com.example.rd.baomingxitong.ViewModel

import android.app.AlertDialog
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.rd.baomingxitong.Http.RetrofitHelper
import com.example.rd.baomingxitong.base.MyApp
import com.example.rd.baomingxitong.databinding.TasklayoutxsBinding
import com.example.rd.baomingxitong.entity.Task.*
import com.example.rd.baomingxitong.views.TaskActivity
import com.example.rd.baomingxitong.views.TaskActivity1
import com.google.gson.Gson

/**
 * Created by rd on 2017/9/24.
 */

object TaskViewModelXS
{
    lateinit var task: TaskByStudentInView
    lateinit var binding: TasklayoutxsBinding
    lateinit var mActivity: TaskActivity1
    lateinit var laoshiyijian: ObservableField<String>
    lateinit var bumenyijian: ObservableField<String>
    lateinit var zhongxinyijian: ObservableField<String>
    lateinit var zhanjiayijian: ObservableField<String>
    var steacher: ObservableBoolean = ObservableBoolean(MyApp.instances.sftecher)
    var xiangmuId: Int = 0
    var piciId: Int = 0
    var duizhangxuehao: String = ""
    var piciMingcheng: ObservableField<String> = ObservableField("")

    fun init(tasklayoutBinding: TasklayoutxsBinding,taskActivity: TaskActivity1)
    {
        binding = tasklayoutBinding
        mActivity = taskActivity
        binding.taskModelXS = this
        task = TaskByStudentInView()
        laoshiyijian = ObservableField<String>("")
        bumenyijian = ObservableField<String>("")
        zhongxinyijian = ObservableField<String>("")
        zhanjiayijian = ObservableField<String>("")
    }

    fun shuaxin()
    {
        RetrofitHelper.insance.getXD(xiangmuId, piciId, duizhangxuehao)
    }
    fun jiazai(xiaoduiS: List<TaskDuiyuan>?)
    {
        if (xiaoduiS!=null)
        {
            steacher.set(MyApp.instances.sftecher)
            var duizhangDY: TaskDuiyuan = TaskDuiyuan()
            for (i in xiaoduiS)
            {
                var layout = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                var xuehao1: TextView = TextView(mActivity)
                xuehao1.text = i.xuehao
                var sz = 17
                xuehao1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, sz.toFloat())
                xuehao1.layoutParams = layout
                var fengong1: TextView = TextView(mActivity)
                fengong1.text = i.fengong
                fengong1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,sz.toFloat())
                fengong1.layoutParams = layout
                binding.taskXsZhujiemian.addView(xuehao1)
                binding.taskXsZhujiemian.addView(fengong1)
                if (i.duizhang.equals(1))
                    duizhangDY = i
            }

            var taskJson: TaskJson = Gson().fromJson(duizhangDY.taksJson,TaskJson::class.java)

            xiangmuId = duizhangDY.xiangmuId
            duizhangxuehao = duizhangDY.duizhangxuehao
            piciId = duizhangDY.piciId
            task.xuehao.set(duizhangDY.xuehao)
            task.banji.set(duizhangDY.banjimingzi)
            task.bumenyijian.set(duizhangDY.bumenyijian)
            task.zhanjiayijian.set(duizhangDY.zhanjiayijian)
            task.zhidaolsyijian.set(duizhangDY.zhidaolsyijian)
            task.zhongxinyijian.set(duizhangDY.zhongxinyijian)
            task.chengguo.set(taskJson.chengguo)
            task.fangan.set(taskJson.fangan)
            task.jianjie.set(taskJson.jianjie)
            task.jindu.set(taskJson.jindu)
            task.jingfei.set(taskJson.jingfei)
            task.laoshi.set(taskJson.laoshi)
            task.laoshigonghao.set(duizhangDY.gonghao)
            task.shenbaoren.set(taskJson.shenbaoren)
            task.tese.set(taskJson.tese)
            task.xiangmu.set(duizhangDY.xiangmu)
            task.renshu.set(taskJson.renshu)
            laoshiyijian.set(duizhangDY.zhidaolsyijian)
            bumenyijian.set(duizhangDY.bumenyijian)
            zhongxinyijian.set(duizhangDY.zhongxinyijian)
            zhanjiayijian.set(duizhangDY.zhanjiayijian)
            piciMingcheng.set(duizhangDY.piciId.toString())

        }
        else
        {
            //刷新
        }

    }

    fun onClickTijiao(view: View?)
    {
            var queRenDialog: AlertDialog.Builder = AlertDialog.Builder(mActivity)
            queRenDialog.setTitle("保存意见")
            queRenDialog.setMessage("请确认后提交")
            queRenDialog.setNegativeButton("取消", null)
            queRenDialog.setPositiveButton("确认", { dialogInterface, i ->
                onTijiao()
            })
            queRenDialog.show()
    }

    fun onTijiao()
    {
        //retro
        var tijiao: TaskUpdataLSYJ = TaskUpdataLSYJ(laoshiyijian.get(), xiangmuId, piciId, duizhangxuehao)
        RetrofitHelper.insance.updataLSYJ(mActivity,tijiao)
    }

    fun ErrorTixing(taskDuiyuanList: List<TaskDuiyuan>?)
    {
        if (taskDuiyuanList!=null)
        {
            if (!taskDuiyuanList.size.equals(0))
            {
                var tiXing: AlertDialog.Builder = AlertDialog.Builder(mActivity)
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
                var tiXing: AlertDialog.Builder = AlertDialog.Builder(mActivity)
                tiXing.setTitle("报名成功")
                tiXing.setMessage("项目报名成功")
                tiXing.setPositiveButton("确定",null)
                var dialog: AlertDialog = tiXing.create()
                dialog.show()
            }
        }
        else
        {
            var tiXing: AlertDialog.Builder = AlertDialog.Builder(mActivity)
            tiXing.setTitle("报名成功")
            tiXing.setMessage("项目报名成功")
            tiXing.setPositiveButton("确定",null)
            var dialog: AlertDialog = tiXing.create()
            dialog.show()
        }
    }

}