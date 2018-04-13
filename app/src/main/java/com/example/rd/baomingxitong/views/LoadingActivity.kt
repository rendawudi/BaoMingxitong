package com.example.rd.baomingxitong.views

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.example.rd.baomingxitong.R
import com.example.rd.baomingxitong.base.ActivityController
import com.example.rd.baomingxitong.base.MyApp
import kotlin.concurrent.thread

/**class LoadingActivity : AppCompatActivity() {
    lateinit var dialog: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        dialog = findViewById(R.id.prograssBar)
        if (MyApp.instances.user==null)
        {
            zxcx()
        }
    }

    override fun onRestart() {
        super.onRestart()
        if (MyApp.instances.user==null)
        {
            zxcx()
        }
    }

    override fun onResume() {
        super.onResume()
        if (MyApp.instances.user==null)
        {
            zxcx()
        }
    }
    fun zxcx()
    {
        thread (start = true){
            dialog.progress = Thread.currentThread().id.toInt()
            dialog.isIndeterminate = true
            var shijian = 0
            dialog.visibility = View.VISIBLE
            while ( MyApp.instances.user==null)
            {
                Thread.sleep(1000)
                shijian++
                Log.d("加载中", MyApp.instances.user.toString())
                MyApp.instances.getUser()
                if (shijian==15)
                    break
            }
            if (shijian==15)
            {
                this.startActivity(android.content.Intent(this,com.example.rd.baomingxitong.views.LoginActivityXs::class.java))
                this.finish()
            }
            if (MyApp.instances.user!=null)
            {
                this.startActivity(android.content.Intent(this,TaskListActivity::class.java))
                this.finish()
            }
            else
            {
                this.startActivity(android.content.Intent(this,com.example.rd.baomingxitong.views.LoginActivityXs::class.java))
                ActivityController.finishAll()
                this.finish()
            }
        }
    }

} */