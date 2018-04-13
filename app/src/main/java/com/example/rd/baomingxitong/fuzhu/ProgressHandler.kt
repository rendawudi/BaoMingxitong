package com.example.rd.baomingxitong.fuzhu

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.app.ProgressDialog
import android.content.Context
import android.os.Handler
import android.os.Message
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import com.example.rd.baomingxitong.R


/**
 * Created by rd on 2017/11/3.
 */
object ProgressHandler : Handler() {
    var pd: ProgressDialog? = null
    fun initProgressDialog(mContext: Context) {
        if (pd==null)
        {
            pd = ProgressDialog(mContext)
            pd!!.setCancelable(false)
            pd!!.setTitle("加载中...")
            var window: Window = pd!!.window
            window.setGravity(Gravity.CENTER)
            window.setWindowAnimations(R.style.mystyle)
        }
    }

    fun showDialog()
    {
        if (!pd!!.isShowing) {
            pd!!.show()//显示进度条
        }
    }

    fun dismissDialog() {
        if (pd != null) {
            pd!!.dismiss()//取消进度条
            pd = null
        }
    }

    fun showProgressDialog() {
        if (ProgressHandler != null) {
            ProgressHandler.obtainMessage(ProgressHandler.SHOW_PROGRESS).sendToTarget()
        }
    }

    fun disMissProgressDialog() {
        if (ProgressHandler != null) {
            ProgressHandler.obtainMessage(ProgressHandler.DISMISS_PROGRESS).sendToTarget()
        }
    }

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        when (msg.what) {
            SHOW_PROGRESS -> showDialog()
            DISMISS_PROGRESS -> dismissDialog()
        }
    }
        val SHOW_PROGRESS = 0
        val DISMISS_PROGRESS = 1
}