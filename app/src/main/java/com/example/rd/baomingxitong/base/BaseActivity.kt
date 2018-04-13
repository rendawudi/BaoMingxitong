package com.example.rd.baomingxitong.base

import android.os.Bundle

import android.os.PersistableBundle

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

/**
 * Created by rd on 2017/9/27.
 */

open class BaseActivity: RxAppCompatActivity()
{


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        ActivityController.addActivity(this)
        pdUserGuoqi()
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityController.removeActivity(this)
    }

    override fun onRestart() {
        super.onRestart()
        pdUserGuoqi()
    }

    override fun onStart() {
        super.onStart()
        pdUserGuoqi()
    }

    override fun onResume() {
        super.onResume()
        pdUserGuoqi()
    }
    fun pdUserGuoqi()
    {
        if (MyApp.instances.user ==null&&MyApp.instances.sftecher==false)
        {
            MyApp.instances.pdLogin(this)
        }
        if (MyApp.instances.teacher ==null&&MyApp.instances.sftecher==true)
        {
            MyApp.instances.pdLoginT(this)
        }
    }
}