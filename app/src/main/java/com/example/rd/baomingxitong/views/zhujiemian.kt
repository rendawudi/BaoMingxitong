package com.example.rd.baomingxitong.views

import android.app.FragmentTransaction
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.View
import com.example.rd.baomingxitong.R
import com.example.rd.baomingxitong.databinding.ActivityZhujiemianBinding
import com.example.rd.baomingxitong.fuzhu.SocketClient
import kotlinx.android.synthetic.main.activity_zhujiemian.*

class zhujiemian : FragmentActivity() {

    var taskJoined: TaskJoinedActivity ?=null
    var taskList: TaskListActivity ?=null
    var zidingyi: TaskActivity?=null
    lateinit var binding: ActivityZhujiemianBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_zhujiemian)
        binding.zhujiemian = this
        liaotian(null)
        shezhiMenu(binding)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        SocketClient.closeConnect()
    }

    fun liaotian(view:View?)
    {
        var fragmentTransaction: FragmentTransaction = this.fragmentManager.beginTransaction()
        if (taskJoined==null)
        taskJoined = TaskJoinedActivity()
        fragmentTransaction.replace(R.id.frame_content,taskJoined)
        fragmentTransaction.commit()
    }

    fun tasklist(view: View)
    {
        var fragmentTransaction: FragmentTransaction = this.fragmentManager.beginTransaction()
        if (taskList==null)
            taskList = TaskListActivity()
        fragmentTransaction.replace(R.id.frame_content,taskList)
        fragmentTransaction.commit()
    }

    fun zidingyi(view: View)
    {
        var fragmentTransaction: FragmentTransaction = this.fragmentManager.beginTransaction()
        if (zidingyi==null)
            zidingyi = TaskActivity()
        fragmentTransaction.replace(R.id.frame_content,zidingyi)
        fragmentTransaction.commit()
    }


    fun shezhiMenu(binding: ActivityZhujiemianBinding)
    {
        binding.nav.setNavigationItemSelectedListener {
            item ->
            if (item.itemId.equals(R.id.favorite))
                startActivity(Intent(this,shezhiActivity::class.java))
            binding.drawerlayout.closeDrawer(binding.nav)
            return@setNavigationItemSelectedListener true
        }
        binding.usernemu.setOnClickListener {
            if (binding.drawerlayout.isDrawerOpen(binding.nav))
                binding.drawerlayout.closeDrawer(binding.nav)
            else
                binding.drawerlayout.openDrawer(binding.nav)
        }
    }
}
