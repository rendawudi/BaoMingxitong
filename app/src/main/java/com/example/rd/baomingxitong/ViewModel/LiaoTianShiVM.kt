package com.example.rd.baomingxitong.ViewModel

import android.databinding.ObservableField
import android.view.View
import android.widget.Toast
import com.example.rd.baomingxitong.BR
import com.example.rd.baomingxitong.GreenDao.LTXiangmuDao
//import com.example.rd.baomingxitong.GreenDao.LTXiangmuDao
import com.example.rd.baomingxitong.R
import com.example.rd.baomingxitong.RecyclerView.ItemViewHolder
import com.example.rd.baomingxitong.RecyclerView.MyAdapter
import com.example.rd.baomingxitong.base.GreenDaoUse
import com.example.rd.baomingxitong.base.MyApp
import com.example.rd.baomingxitong.databinding.ActivityLiaotianShiBinding
import com.example.rd.baomingxitong.entity.LiaoTian.LTMsg
import com.example.rd.baomingxitong.entity.LiaoTian.LTSxiaoxi
import com.example.rd.baomingxitong.entity.LiaoTian.LTXiangmu
import com.example.rd.baomingxitong.fuzhu.SocketClient
import com.example.rd.baomingxitong.views.LiaotianShi
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter

/**
 * Created by rd on 2017/11/5.
 */

object LiaoTianShiVM
{
    lateinit var binding: ActivityLiaotianShiBinding
    lateinit var mactivity: LiaotianShi
    lateinit var liaotianMsgs: ArrayList<LTSxiaoxi>
    var shuru: ObservableField<String> = ObservableField("")
    var xiangmuId: Long = 0
    var piciId: Long = 0
    var duizhangxuehao: String = ""
    var xmid : Long = 0

    fun init(binding: ActivityLiaotianShiBinding,activity: LiaotianShi)
    {
        mactivity = activity
        this.binding = binding
        this.binding.vm = this
    }

    fun setxiangMu()
    {
        GreenDaoUse.mDaoSession.clear()
        var xiangmus = GreenDaoUse.mDaoSession.queryBuilder(LTXiangmu::class.java).where(LTXiangmuDao.Properties.XiangmuId.eq(xiangmuId), LTXiangmuDao.Properties.PiciId.eq(piciId), LTXiangmuDao.Properties.Duizhangxuehao.eq(duizhangxuehao)).unique()
        liaotianMsgs = ArrayList<LTSxiaoxi>()
        xmid = xiangmus.xmid
        if (xiangmus!=null&&xiangmus.msgList!=null)
        {
            for (i in xiangmus.msgList)
            {
                var x = LTSxiaoxi()
                if (i.sender==null)
                {
                    GreenDaoUse.mMsgLt.delete(i)
                }
                else
                {
                    if (!MyApp.instances.sftecher)
                    {
                        if(i.sender.equals(MyApp.instances.user!!.xuehao))
                        x.sfbr = false
                    }
                    else
                    {
                        if (i.sender.equals(MyApp.instances.teacher!!.gonghao))
                        x.sfbr = false
                    }
                    x.msg.set(i.msg)
                    x.time.set(i.time)
                    x.sender.set(i.xingming)
                    liaotianMsgs.add(x)
                }
            }
        }
        val variabaleId: Int = BR.LtMsg
        var myadapter = object : MyAdapter<LTSxiaoxi>(R.layout.ltshi,variabaleId)
        {
            override fun convert(holder: ItemViewHolder, t: LTSxiaoxi, position: Int) {
                holder.setBinding(variabaleId,t)
            }
        }
        myadapter.setDatas(liaotianMsgs)
        this.binding.lvUserlist.adapter = myadapter
        this.binding.lvUserlist.adapter.notifyDataSetChanged()
        binding.lvUserlist.scrollToPosition(liaotianMsgs.size-1)
    }

    fun fasong(view: View?)
    {
        var msg = LTMsg()
        var fomt = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        if (!MyApp.instances.sftecher)
        msg.sender = MyApp.instances.user!!.xuehao
        else
            msg.sender = MyApp.instances.teacher!!.gonghao
        msg.time = fomt.format(Date())
        msg.xiangmuId = xiangmuId
        msg.msg = shuru.get()
        msg.duizhangxuehao = duizhangxuehao
        msg.piciId = piciId
        msg.xmid = xmid

        if (!MyApp.instances.sftecher)
        msg.xingming = MyApp.instances.user!!.xingming
        else
            msg.xingming = MyApp.instances.teacher!!.xingming
        var gson = Gson()
        Toast.makeText(mactivity,gson.toJson(msg),Toast.LENGTH_SHORT).show()
        setMsg(msg)
        SocketClient.sendMsg(gson.toJson(msg))
        shuru.set("")
    }

    fun setMsg(i: LTMsg)
    {
        try {
            GreenDaoUse.mMsgLt.insertInTx(i)
        }
       catch (e: Exception)
       {
           e.printStackTrace()
       }
        if (xiangmuId.equals(i.xiangmuId))
        {
            var x = LTSxiaoxi()
            if (!MyApp.instances.sftecher)
            {
                if(i.sender.equals(MyApp.instances.user!!.xuehao))
                    x.sfbr = false
            }
            else
            {
                if (i.sender.equals(MyApp.instances.teacher!!.gonghao))
                    x.sfbr = false
            }
            x.msg.set(i.msg)
            x.time.set(i.time)
            x.sender.set(i.xingming)
            liaotianMsgs.add(x)
            binding.lvUserlist.adapter.notifyItemInserted(liaotianMsgs.size-1)
            binding.lvUserlist.scrollToPosition(liaotianMsgs.size-1)
        }
    }
}