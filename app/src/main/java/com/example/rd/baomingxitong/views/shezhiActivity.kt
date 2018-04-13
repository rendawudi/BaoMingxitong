package com.example.rd.baomingxitong.views

import android.app.AlertDialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.rd.baomingxitong.Http.RetrofitHelper
import com.example.rd.baomingxitong.R
import com.example.rd.baomingxitong.ViewModel.StudentViewModel
import com.example.rd.baomingxitong.ViewModel.UserViewModel
import com.example.rd.baomingxitong.base.MyApp
import com.example.rd.baomingxitong.databinding.ActivityShezhiBinding
import com.example.rd.baomingxitong.entity.UserHttpLoginGet
import com.example.rd.baomingxitong.entity.UserHttpRegisterSend
import com.example.rd.baomingxitong.entity.UserInfo
import com.example.rd.baomingxitong.fuzhu.ProgressHandler
import com.example.rd.baomingxitong.fuzhu.ProgressObservable
import com.example.rd.baomingxitong.fuzhu.SubscribeOnNextListener

class shezhiActivity : AppCompatActivity() {

        lateinit var bindng: ActivityShezhiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindng = DataBindingUtil.setContentView(this,R.layout.activity_shezhi)
        bindng.entity = UserInfo()
        bindng.tijiao.setOnClickListener {
            var tiXing: AlertDialog.Builder = AlertDialog.Builder(this)
            tiXing.setTitle("信息修改")
            tiXing.setMessage("确定修改个人信息？")
            tiXing.setPositiveButton("确定",{
                dialogInterface, i ->  tijiao()
            })
            tiXing.setNegativeButton("取消",null)
            var dialog: AlertDialog = tiXing.create()
            dialog.show()
        }
    }

    override fun onResume() {
        super.onResume()
        bindng.entity!!.jianjie.set(MyApp.instances.user!!.jianjie)
        bindng.entity!!.qq.set(MyApp.instances.user!!.qq)
        bindng.entity!!.xuehao.set(MyApp.instances.user!!.xuehao)
        bindng.entity!!.weixin.set(MyApp.instances.user!!.weixin)
        bindng.entity!!.nicheng.set(MyApp.instances.user!!.nicheng)
        bindng.entity!!.xingming.set(MyApp.instances.user!!.xingming)
        bindng.entity!!.shouji.set(MyApp.instances.user!!.shouji)

    }

    fun tijiao()
    {
        var user = UserHttpRegisterSend()
        user.xuehao = bindng.entity!!.xuehao.get()
        user.jianjie = bindng.entity!!.jianjie.get()
        user.qq = bindng.entity!!.qq.get()
        user.weixin = bindng.entity!!.weixin.get()
        user.nicheng = bindng.entity!!.nicheng.get()
        user.shouji = bindng.entity!!.shouji.get()

        ProgressHandler.initProgressDialog(this)
        var mOnNextListener = object : SubscribeOnNextListener<String?>
        {
            override fun onNext(t: String?) {

            }

            override fun onStart() {
                ProgressHandler.showProgressDialog()
            }

            override fun onComplete() {
                ProgressHandler.disMissProgressDialog()
                Toast.makeText(this@shezhiActivity,"修改成功",Toast.LENGTH_SHORT).show()
            }

        }
        RetrofitHelper.insance.UpdataUser(ProgressObservable(this,mOnNextListener), user)
    }
}
