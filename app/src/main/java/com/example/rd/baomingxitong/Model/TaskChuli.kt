package com.example.rd.baomingxitong.Model

import com.example.rd.baomingxitong.Http.RetrofitHelper
import com.example.rd.baomingxitong.entity.Task.*
import com.google.gson.Gson

/**
 * Created by rd on 2017/10/2.
 */
object TaskChuli
{
    fun TaskXsChuli(taskMain: TaskmainGet,taskInView: TaskMsgXsInView,taskSendList: List<TaskDuiyuan>)
    {
        taskSendList[0].duizhang = 1
        taskSendList[0].xiangmu = taskMain.xiangmu
        var taskJson: TaskJson = TaskJson()
        taskJson.banji = taskInView.banji.get()
        taskJson.chengguo = taskInView.chengguo.get()
        taskJson.fangan = taskInView.fangan.get()
        taskJson.jianjie = taskInView.jianjie.get()
        taskJson.jindu = taskInView.jindu.get()
        taskJson.jingfei = taskInView.jingfei.get()
        taskJson.laoshi = taskInView.laoshi.get()
        taskJson.renshu= taskInView.renshu.get()
        taskJson.shenbaoren= taskInView.shenbaoren.get()
        taskJson.tese= taskInView.tese.get()
        taskJson.xuehao= taskInView.xuehao.get()
        taskJson.xiangmu = taskInView.xiangmu.get()
        var gson: Gson = Gson()
        taskSendList[0].taksJson = gson.toJson(taskJson)
        for (i in taskSendList)
        {
            i.piciId = taskMain.piciId
            i.xiangmuId = taskMain.xiangmuId
            i.duizhangxuehao = taskSendList[0].xuehao
        }
        RetrofitHelper.insance.InsertXsTaskList(taskSendList)
    }

    fun TaskXsXiangMu(taskMain: TaskMainInHttp, taskInView: TaskByStudentInView, taskSendList: List<TaskDuiyuan>)
    {
        taskSendList[0].duizhang = 1;
        var taskJson: TaskJson = TaskJson()
        taskJson.banji = taskInView.banji.get()
        taskJson.chengguo = taskInView.chengguo.get()
        taskJson.fangan = taskInView.fangan.get()
        taskJson.jianjie = taskInView.jianjie.get()
        taskJson.jindu = taskInView.jindu.get()
        taskJson.jingfei = taskInView.jingfei.get()
        taskJson.laoshi = taskInView.laoshi.get()
        taskJson.renshu= taskInView.renshu.get()
        taskJson.shenbaoren= taskInView.shenbaoren.get()
        taskJson.tese= taskInView.tese.get()
        taskJson.xuehao= taskInView.xuehao.get()
        taskJson.xiangmu = taskInView.xiangmu.get()
        var gson: Gson = Gson()
        taskSendList[0].taksJson = gson.toJson(taskJson)
        for (i in taskSendList)
        {
            i.piciId = taskMain.piciId
            i.duizhangxuehao = taskSendList[0].duizhangxuehao
        }
        var tasksend: TaskXueShengXiangMu = TaskXueShengXiangMu(taskMain,taskSendList)
        RetrofitHelper.insance.InsertXsXiangmu(tasksend)
    }
}