package com.example.rd.baomingxitong.FileAndShipinView;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.rd.baomingxitong.Http.HttpUtil;
import com.example.rd.baomingxitong.R;
import com.example.rd.baomingxitong.entity.HttpResult;
import com.example.rd.baomingxitong.entity.myHttpResult;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;


public class myService extends Service {

    private UploadBinder mBinder=new UploadBinder();
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    //回调接口，后台服务显示上传进度
    BaseHttpRequestCallback<myHttpResult> mycallback=new BaseHttpRequestCallback<myHttpResult>() {
        @Override
        public void onSuccess(myHttpResult uploadResponse) {
            super.onSuccess(uploadResponse);
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("Upload Success",-1));
            Toast.makeText(getBaseContext(), "上传成功：" + uploadResponse.getMsg(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(int errorCode, String msg) {
            super.onFailure(errorCode, msg);
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("Upload Failed",-1));
            Toast.makeText(getBaseContext(), "上传失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProgress(int progress, long networkSpeed, boolean done) {
            getNotificationManager().notify(1,getNotification("upload...",progress));
        }
    };

    class UploadBinder extends Binder {
        public void startUpload(RequestParams params){
            HttpUtil.uploadFile(params,mycallback);
        }
        public void pauseUpload(){

        }

        public void cancelUpload(){

        }
    }

    private NotificationManager getNotificationManager(){
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }
    private Notification getNotification(String title, int progress){
        Intent intent=new Intent(this,MainActivity.class);
        PendingIntent pi=PendingIntent.getActivity(this,0,intent,0);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"defalut");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentIntent(pi);
        builder.setContentTitle(title);
        if(progress>0) {
            builder.setContentText(progress + "%");
            builder.setProgress(100,progress,false);
        }
        return builder.build();
    }
}
