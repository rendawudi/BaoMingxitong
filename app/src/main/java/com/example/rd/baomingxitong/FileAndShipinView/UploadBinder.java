package com.example.rd.baomingxitong.FileAndShipinView;

import android.os.Binder;

import com.example.rd.baomingxitong.Http.HttpUtil;
import com.example.rd.baomingxitong.entity.myHttpResult;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;

class UploadBinder extends Binder {
        private   upload_success callback;
        private BaseHttpRequestCallback<myHttpResult> mycallback;

    public UploadBinder(BaseHttpRequestCallback<myHttpResult> mycallback) {
        this.mycallback = mycallback;
    }

    public void startUpload(RequestParams params, upload_success callback){
           // HttpUtil.uploadFile(params,mycallback);
            this.callback=callback;
            HttpUtil.Upload(HttpUtil.type_document,params,mycallback);
        }

        public upload_success getCallback(){
        return callback;
        }

    }