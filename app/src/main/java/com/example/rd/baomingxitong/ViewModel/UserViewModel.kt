package com.example.rd.baomingxitong.ViewModel

import android.app.AlertDialog
import android.databinding.ObservableField
import android.view.View
import android.widget.Toast
import com.example.rd.baomingxitong.Http.RetrofitHelper
import com.example.rd.baomingxitong.R
import com.example.rd.baomingxitong.base.MyApp
import com.example.rd.baomingxitong.databinding.ActivityRegisterBinding
import com.example.rd.baomingxitong.entity.UserHttpRegisterSend
import com.example.rd.baomingxitong.entity.UserInfo
import com.example.rd.baomingxitong.entity.Xuexiao.Banji
import com.example.rd.baomingxitong.entity.Xuexiao.Xueyuan
import com.example.rd.baomingxitong.entity.Xuexiao.Zhuanye
import com.example.rd.baomingxitong.fuzhu.ProgressHandler
import com.example.rd.baomingxitong.fuzhu.ProgressObservable
import com.example.rd.baomingxitong.fuzhu.SubscribeOnNextListener
import com.example.rd.baomingxitong.views.RegisterActivity

/**
 * Created by rd on 2017/9/24.
 */
object UserViewModel
{
    lateinit var userInfo: UserInfo
    lateinit var binding: ActivityRegisterBinding
    lateinit var registerActivity: RegisterActivity
    lateinit var xueyuan: ObservableField<String>
    lateinit var zhuanye: ObservableField<String>
    lateinit var banji: ObservableField<String>
    lateinit var userRegisterSend: UserHttpRegisterSend

    fun init(activityRegisterBinding: ActivityRegisterBinding,registerActivity: RegisterActivity)
    {
        this.binding = activityRegisterBinding
        this.registerActivity = registerActivity
        this.binding.userModel = this
        userInfo = UserInfo()
        userRegisterSend = UserHttpRegisterSend()
        xueyuan = ObservableField("")
        zhuanye = ObservableField("")
        banji = ObservableField("")
    }

    fun onClickZhuCe(view: View)
    {
        userRegisterSend.nicheng = userInfo.nicheng.get()
        userRegisterSend.jianjie = userInfo.jianjie.get()
        userRegisterSend.mima = userInfo.mima.get()
        userRegisterSend.qq = userInfo.qq.get()
        userRegisterSend.shouji = userInfo.shouji.get()
        userRegisterSend.weixin = userInfo.weixin.get()
        userRegisterSend.xingming = userInfo.xingming.get()
        var xh = userInfo.xuehao.get()

        userRegisterSend.xuehao = userInfo.xuehao.get()
        if (userRegisterSend.banjiId==0)
        {
            Toast.makeText(registerActivity,"请选择班级",Toast.LENGTH_SHORT).show()
        }
        else if (userRegisterSend.zhuanyeId==0)
        {
            Toast.makeText(registerActivity,"请选择专业",Toast.LENGTH_SHORT).show()
        }
        else if (userRegisterSend.xueyuanId==0)
        {
            Toast.makeText(registerActivity,"请选择学院",Toast.LENGTH_SHORT).show()
        }
        else if (userRegisterSend.xuehao.equals(0))
        {
            Toast.makeText(registerActivity,"请输入学号",Toast.LENGTH_SHORT).show()
        }
        else
        {
            ProgressHandler.initProgressDialog(registerActivity)
            var mOnNextListener = object : SubscribeOnNextListener<String?>
            {
                override fun onNext(t: String?) {
                    if (t.equals("200"))
                    {
                        MyApp.instances.querenuser(false)
                        MyApp.instances.pdLogin(registerActivity)
                    }
                    else
                    {
                        MyApp.instances.ClearUser()
                    }
                }

                override fun onStart() {
                    ProgressHandler.showProgressDialog()
                }

                override fun onComplete() {

                }
            }
            RetrofitHelper.insance.UserRigister(ProgressObservable(registerActivity,mOnNextListener), userRegisterSend)
        }

    }

    fun onClickXueYuan(view: View)
    {
        userRegisterSend.zhuanyeId = 0;
        zhuanye.set("")
        userRegisterSend.banjiId = 0;
        banji.set("")

        RetrofitHelper.insance.getXueYuans()
    }
    fun xueYuanXs(xueYuanList:List<Xueyuan>?)
    {
        var dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(registerActivity)
        dialogBuilder.setIcon(R.drawable.huda)
        dialogBuilder.setTitle("学院")
        var xueYuast:ArrayList<String> = ArrayList()
        var xueyuanIdList: ArrayList<Int> = ArrayList()
        if (xueYuanList!=null)
        {
            for (i in 0..(xueYuanList.size-1))
            {
                xueYuast.add(xueYuanList[i].xueyuan)
                xueyuanIdList.add(xueYuanList[i].xueyuanId)
            }
            var xyList: Array<CharSequence> = xueYuast.toTypedArray()

            dialogBuilder.setItems(xyList,{
                dialogInterface, i ->
                xueyuan.set(xyList[i].toString())
                userRegisterSend.xueyuanId = xueyuanIdList[i]
            })
            dialogBuilder.show()
        }
    }

    fun onClickZhuanYe(view: View)
    {
        userRegisterSend.banjiId = 0;
        banji.set("")
        if (userRegisterSend.xueyuanId==0) {
            Toast.makeText(registerActivity,"请先选择学院",Toast.LENGTH_SHORT).show()
        }
        else
        {
            RetrofitHelper.insance.getZhuanyes(userRegisterSend.xueyuanId)
        }
    }
    fun zhuanYEXs(zhuanyesList:List<Zhuanye>?)
    {
        var dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(registerActivity)
        dialogBuilder.setIcon(R.drawable.huda)
        dialogBuilder.setTitle("专业")
        var zYst:ArrayList<String> = ArrayList()
        var zyIdList: ArrayList<Int> = ArrayList()
        if (zhuanyesList!=null)
        {
            for (i in 0..(zhuanyesList.size-1))
            {
                zYst.add(zhuanyesList[i].zhuanye)
                zyIdList.add(zhuanyesList[i].zhuanyeId)
            }
            var xyList: Array<CharSequence> = zYst.toTypedArray()

            dialogBuilder.setItems(xyList,{
                dialogInterface, i ->
                zhuanye.set(xyList[i].toString())
                userRegisterSend.zhuanyeId = zyIdList[i]
            })
            dialogBuilder.show()
        }
    }

    fun onClickBanji(view: View) {
        if (userRegisterSend.zhuanyeId == 0) {
            Toast.makeText(registerActivity, "请先选择专业", Toast.LENGTH_SHORT).show()
        } else {
           RetrofitHelper.insance.getBanjis(userRegisterSend.zhuanyeId)
        }
    }
    fun banJiXs(zhuanyesList: List<Banji>?)
    {
        var dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(registerActivity)
        dialogBuilder.setIcon(R.drawable.huda)
        dialogBuilder.setTitle("班级")
        var zYst: ArrayList<String> = ArrayList()
        var zyIdList: ArrayList<Int> = ArrayList()
        if (zhuanyesList != null) {
            for (i in 0..(zhuanyesList.size - 1)) {
                zYst.add(zhuanyesList[i].banjiMingcheng)
                zyIdList.add(zhuanyesList[i].banjiId)
            }
            var xyList: Array<CharSequence> = zYst.toTypedArray()

            dialogBuilder.setItems(xyList, { dialogInterface, i ->
                banji.set(xyList[i].toString())
                userRegisterSend.banjiId = zyIdList[i]
            })
            dialogBuilder.show()
        }
    }
}