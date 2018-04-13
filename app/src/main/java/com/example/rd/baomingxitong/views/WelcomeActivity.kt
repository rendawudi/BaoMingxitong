package com.example.rd.baomingxitong.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.rd.baomingxitong.R
import android.content.Intent
import android.os.Handler


class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        Handler().postDelayed(Runnable {
            val i = Intent(this, LoginActivityXs::class.java)
            startActivity(i)
            finish()
        }, 3000)
    }
}
