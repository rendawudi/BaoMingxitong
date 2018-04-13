package com.example.rd.baomingxitong.Model

import android.util.Log
import com.example.rd.baomingxitong.Http.RetrofitHelper
import com.example.rd.baomingxitong.ViewModel.TaskListViewModel
import com.example.rd.baomingxitong.base.MyApp
import com.example.rd.baomingxitong.entity.Task.BanjiList
import com.example.rd.baomingxitong.entity.Task.TaskIdList
import com.example.rd.baomingxitong.entity.Task.TaskSpMain
import com.example.rd.baomingxitong.entity.Task.ZhuanyeList
import com.example.rd.baomingxitong.entity.Xuexiao.Pici
import com.google.gson.Gson
import kotlin.concurrent.thread

/**
 * Created by rd on 2017/9/29.
 */
object TaskModel
{
    lateinit var viewAndModel: TaskListViewModel
    fun init(taskListViewModel: TaskListViewModel)
    {
        viewAndModel = taskListViewModel
    }
    fun getSpTask()
    {
                var XueyuanId: Int ?=null
        if (MyApp.instances.sftecher)
                XueyuanId = MyApp.instances.teacher?.xueyuanId
        else
            XueyuanId = MyApp.instances.user?.xueyuanId
        if (MyApp.instances.user!=null||MyApp.instances.teacher!=null)
                RetrofitHelper.insance.getSimpleTasks(XueyuanId)
    }

    fun chuliTask(taskSpList: List<Pici>?)
    {
        var ZhuanyeId: Int ?=null
        var BanjiId: Int ?=null
        if (!MyApp.instances.sftecher)
        {
            ZhuanyeId = MyApp.instances.user?.zhuanyeId
            BanjiId = MyApp.instances.user?.banjiId
            var zhuanyeList: ZhuanyeList
            var banjiList: BanjiList
            var gson: Gson = Gson()
            var xiangmuIds: ArrayList<Int> = ArrayList()
            if (taskSpList!=null)
            {
                for (i in taskSpList)
                {
                    var zhuanyeJson = i.zhuanyeId
                    var banjiJson = i.banjiId
                    zhuanyeList = gson.fromJson(zhuanyeJson,ZhuanyeList::class.java)
                    banjiList = gson.fromJson(banjiJson,BanjiList::class.java)
                    if (zhuanyeList!=null&&zhuanyeList.zhuanyeIds.size!=0)
                    {
                        if (zhuanyeList.zhuanyeIds.contains(ZhuanyeId))
                        {
                            if (banjiList!=null&&banjiList.BanjiIds.size!=0)
                            {
                                if (banjiList.BanjiIds.contains(BanjiId))
                                {
                                    xiangmuIds.add(i.piciId)
                                }
                            }
                            else
                            {
                                xiangmuIds.add(i.piciId)
                            }
                        }
                    }
                    else
                    {
                        xiangmuIds.add(i.piciId)
                    }
                }
            }
            var taskIdList: TaskIdList = TaskIdList(xiangmuIds)
            RetrofitHelper.insance.getTaskMainList(taskIdList, taskSpList)
        }
        else
        {
            var xiangmuIds: ArrayList<Int> = ArrayList()
            if (taskSpList!=null)
            {
                for (i in taskSpList)
                {
                    xiangmuIds.add(i.piciId)
                }
            }
            var taskIdList: TaskIdList = TaskIdList(xiangmuIds)
            RetrofitHelper.insance.getTaskMainList(taskIdList, taskSpList)
        }
    }
}