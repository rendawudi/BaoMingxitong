package com.example.rd.baomingxitong.Model

import com.example.rd.baomingxitong.GreenDao.LTXiangmuDao
import com.example.rd.baomingxitong.Http.RetrofitHelper
import com.example.rd.baomingxitong.ViewModel.TaskJoinedListVM
import com.example.rd.baomingxitong.base.GreenDaoUse
import com.example.rd.baomingxitong.base.MyApp
import com.example.rd.baomingxitong.entity.LiaoTian.LTXiangmu
import com.example.rd.baomingxitong.entity.LiaoTian.LTXmHttp
import com.example.rd.baomingxitong.entity.LiaoTian.LTXmjianjie
import com.example.rd.baomingxitong.fuzhu.SocketClient

/**
 * Created by rd on 2017/11/2.
 */
object TaskJoinedModel
{
    var taskVM: TaskJoinedListVM ?=null

    fun init(taskVm: TaskJoinedListVM)
    {
        taskVM = taskVm
    }


    fun setTaskJoined(taskJoinedList: LTXmHttp?)
    {
        if (taskJoinedList!=null)
        {
            var bendiIdS = ArrayList<LTXmjianjie>()
            var fwqIdS = taskJoinedList!!.xiangmu

            var bendiXMS = GreenDaoUse.mTaskLT.loadAll()
            for (taskHttp in bendiXMS)
            {
                var sss: LTXmjianjie = LTXmjianjie(taskHttp.xiangmuId.toInt(), taskHttp.xiangmu, taskHttp.duizhangmingzi, taskHttp.banjimingzi, taskHttp.zhuangtai, taskHttp.piciId.toInt(), taskHttp.duizhangxuehao)
                bendiIdS.add(sss)
            }
            for (i in bendiIdS)
            {
                if (!fwqIdS.contains(i))
                {
                    var xiangmu = GreenDaoUse.mDaoSession.queryBuilder(LTXiangmu::class.java).where(LTXiangmuDao.Properties.XiangmuId.eq(i.xiangmuId.toLong()),LTXiangmuDao.Properties.PiciId.eq(i.piciId.toLong()),LTXiangmuDao.Properties.Duizhangxuehao.eq(i.duizhangxuehao)).unique()
                    GreenDaoUse.mTaskLT.delete(xiangmu)
                }
            }

            for (taskHttp in taskJoinedList!!.xiangmu)
            {
                var xiangmu = LTXiangmu()
                xiangmu.xiangmuId = taskHttp.xiangmuId.toLong()
                xiangmu.banjimingzi = taskHttp.banjimingzi
                xiangmu.duizhangmingzi = taskHttp.duizhangmingzi
                xiangmu.xiangmu = taskHttp.xiangmu
                xiangmu.zhuangtai = taskHttp.zhuangtai
                xiangmu.duizhangxuehao = taskHttp.duizhangxuehao
                xiangmu.piciId = taskHttp.piciId.toLong()
                try {
                    if (GreenDaoUse.mDaoSession.queryBuilder(LTXiangmu::class.java).where(LTXiangmuDao.Properties.XiangmuId.eq(xiangmu.xiangmuId.toLong()),LTXiangmuDao.Properties.PiciId.eq(xiangmu.piciId.toLong()),LTXiangmuDao.Properties.Duizhangxuehao.eq(xiangmu.duizhangxuehao)).count()== ( 0.toLong()))
                        GreenDaoUse.mTaskLT.insertOrReplace(xiangmu)
                }
                catch (e: Exception)
                {
                    e.printStackTrace()
                }

            }
            if (taskJoinedList.LTMsgs!=null)
            for (taskMsg in taskJoinedList!!.LTMsgs)
            {
                taskMsg.xmid = GreenDaoUse.mDaoSession.queryBuilder(LTXiangmu::class.java).where(LTXiangmuDao.Properties.XiangmuId.eq(taskMsg.xiangmuId.toLong()),LTXiangmuDao.Properties.PiciId.eq(taskMsg.piciId.toLong()),LTXiangmuDao.Properties.Duizhangxuehao.eq(taskMsg.duizhangxuehao)).unique().xmid
                GreenDaoUse.mMsgLt.insertInTx(taskMsg)
            }
        }
        if (taskVM!=null)
        taskVM!!.setJoinedTasks()
        SocketClient.init()
    }
}