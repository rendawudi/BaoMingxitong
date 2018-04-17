package com.example.rd.baomingxitong.ViewModel

import android.app.Activity
import android.content.Intent
import android.databinding.ObservableField
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.example.rd.baomingxitong.BR
import com.example.rd.baomingxitong.Http.RetrofitHelper
import com.example.rd.baomingxitong.Model.TaskJoinedModel
import com.example.rd.baomingxitong.R
import com.example.rd.baomingxitong.RecyclerView.ItemViewHolder
import com.example.rd.baomingxitong.RecyclerView.MyAdapter
import com.example.rd.baomingxitong.base.GreenDaoUse
import com.example.rd.baomingxitong.base.MyApp
import com.example.rd.baomingxitong.databinding.ActivityTaskJoinedBinding
import com.example.rd.baomingxitong.entity.LiaoTian.LTXiangmu
import com.example.rd.baomingxitong.entity.LiaoTian.TaskJoinedJJ
import com.example.rd.baomingxitong.fuzhu.SocketClient
import com.example.rd.baomingxitong.views.LiaotianShi
import com.example.rd.baomingxitong.views.TaskActivity1
import com.example.rd.baomingxitong.views.TaskJoinedActivity
import kotlinx.android.synthetic.main.liaotianitem.view.*

/**
 * Created by rd on 2017/11/2.
 */

object TaskJoinedListVM
{
   lateinit var xiangMus: List<LTXiangmu>
    var binding: ActivityTaskJoinedBinding?=null
    lateinit var mActivity: TaskJoinedActivity
    var myadapter: MyAdapter<TaskJoinedJJ> ?=null

    fun init(binding: ActivityTaskJoinedBinding, activity: TaskJoinedActivity,xactivity: Activity) {
        this.binding = binding
        this.mActivity = activity
        this.binding!!.listModel = this
        TaskJoinedModel.init(this)
        if (myadapter!=null)
        {
            this.binding!!.lvUserlist.adapter = myadapter
            this.binding!!.lvUserlist.adapter.notifyDataSetChanged()
        }
    }

    fun setJoinedTasks()
    {
        GreenDaoUse.mDaoSession.clear()
        xiangMus = GreenDaoUse.mTaskLT.loadAll()
        if (xiangMus!=null) {

            var taskJoineds = ArrayList<TaskJoinedJJ>()
            for (i in xiangMus) {
                var t = TaskJoinedJJ()
                t.banji.set(i.banjimingzi)
                t.duizhang.set(i.duizhangmingzi)
                t.xiangmu.set(i.xiangmu)
                if (i.zhuangtai==0)
                {
                    t.qx.set(false)
                    t.zhuangtai.set("审核中")
                }
                if (i.zhuangtai==1)
                {
                    t.qx.set(true)
                    t.zhuangtai.set("通过")
                }
                taskJoineds.add(t)
            }
            val variabaleId: Int = BR.Liaotianxm
            myadapter = object : MyAdapter<TaskJoinedJJ>(R.layout.liaotianitem, variabaleId) {
                override fun convert(holder: ItemViewHolder, t: TaskJoinedJJ, position: Int) {
                    holder.setBinding(variabaleId, t)
                    holder.itemView.openlt.setOnClickListener {
                        LiaoTianShiVM.xiangmuId = xiangMus[position].xiangmuId
                        LiaoTianShiVM.piciId = xiangMus[position].piciId
                        LiaoTianShiVM.duizhangxuehao = xiangMus[position].duizhangxuehao
                        mActivity.startActivity(Intent(mActivity.activity, LiaotianShi::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                    }
                    holder.itemView.openjj.setOnClickListener {
                        TaskViewModelXS.xiangmuId = xiangMus[position].xiangmuId.toInt()
                        mActivity.startActivity(Intent(mActivity.activity, TaskActivity1::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                    }
                    holder.itemView.setOnLongClickListener {
                        if (MyApp.instances.sftecher)
                        {
                            var dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(mActivity.activity)
                            dialogBuilder.setTitle("项目编辑")
                            var xueYuast:ArrayList<String> = ArrayList()
                            xueYuast.add("确认项目")
                            xueYuast.add("删除项目")
                            var xyList: Array<CharSequence> = xueYuast.toTypedArray()
                            dialogBuilder.setItems(xyList,{
                                dialogInterface, i ->
                                if (i==0)
                                {
                                    RetrofitHelper.insance.updataZhuangTai(mActivity.activity,xiangMus[position].xiangmuId.toInt(), xiangMus[position].piciId.toInt(), xiangMus[position].duizhangxuehao)
                                }
                                else
                                {
                                    RetrofitHelper.insance.delXD(mActivity.activity,xiangMus[position].xiangmuId.toInt(), xiangMus[position].piciId.toInt(), xiangMus[position].duizhangxuehao)
                                    //greendao 删除
                                }
                            })
                            dialogBuilder.show()
                        }
                        return@setOnLongClickListener true
                    }
                }
            }

            myadapter!!.setDatas(taskJoineds)
            if (this.binding!=null)
            {
                this.binding!!.lvUserlist.adapter = myadapter
                this.binding!!.lvUserlist.adapter.notifyDataSetChanged()
            }
        }
    }

}