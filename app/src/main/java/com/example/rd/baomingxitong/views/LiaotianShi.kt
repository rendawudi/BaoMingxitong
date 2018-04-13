package com.example.rd.baomingxitong.views

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.rd.baomingxitong.R
import com.example.rd.baomingxitong.ViewModel.LiaoTianShiVM
import com.example.rd.baomingxitong.databinding.ActivityLiaotianShiBinding

class LiaotianShi : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: ActivityLiaotianShiBinding = DataBindingUtil.setContentView(this,R.layout.activity_liaotian_shi)
        binding.lvUserlist.layoutManager = LinearLayoutManager(this)
        LiaoTianShiVM.init(binding,this)
    }

    override fun onResume() {
        super.onResume()
        LiaoTianShiVM.setxiangMu()
    }
}
