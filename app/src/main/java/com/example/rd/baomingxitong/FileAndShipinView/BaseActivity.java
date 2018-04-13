package com.example.rd.baomingxitong.FileAndShipinView;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.rd.baomingxitong.zidingyiView.MyHttpCycleContext;

import cn.finalteam.okhttpfinal.HttpTaskHandler;


/**
 * Desction:
 * Author:pengjianbo
 * Date:15/9/26 下午5:59
 */
public class BaseActivity extends AppCompatActivity implements MyHttpCycleContext {

    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpTaskHandler.getInstance().removeTask(HTTP_TASK_KEY);
    }
}
