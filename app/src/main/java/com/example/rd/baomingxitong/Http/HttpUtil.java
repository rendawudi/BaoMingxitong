package com.example.rd.baomingxitong.Http;

import android.content.Context;

import com.example.rd.baomingxitong.entity.FileAndShipin.Wendang;
import com.example.rd.baomingxitong.entity.HttpResult;
import com.example.rd.baomingxitong.open_document.IntentBuilder;

import java.io.File;
import java.util.List;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.FileDownloadCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class HttpUtil {
    private final static String myIp="192.168.1.231:8080";
    public final static int type_document=1;
    public final static int type_lesson=2;
    public final static int type_shiping=3;
    public static void  Delet(int i,RequestParams params, BaseHttpRequestCallback callback){
        String url=null;
        switch (i){
            case 1:
                url="http://"+myIp+"/FileAndController/dlwendang";
                break;
            case 2:
                url="http://"+myIp+"/FileAndController/deleteLesson";
                break;
            case 3:
                url="http://"+myIp+"/FileAndController/dlshipin";
        }
        if(url!=null)
        HttpRequest.post(url,params,callback);
    }
    public static void Get(int i,RequestParams params, BaseHttpRequestCallback callback){
        String url=null;
        switch (i){
            case 1:
                url="http://"+myIp+"/FileAndController/wendang";
                break;
            case 2:
                url="http://"+myIp+"/FileAndController/lesson";

                break;
            case 3:
                url="http://"+myIp+"/FileAndController/shipin";

        }
        if(url!=null)
        HttpRequest.post(url,params,callback);
    }


    public static void Upload(int i,RequestParams params, BaseHttpRequestCallback callback){
        String url=null;
        switch (i){
            case 1:
                url="http://"+myIp+"/FileAndController/scwendang";
                break;
            case 2:
                url="http://"+myIp+"/FileAndController/insertLesson";
                break;
            case 3:
                url="http://"+myIp+"/FileAndController/scshipin";
        }
        if(url!=null)
        HttpRequest.post(url, params, callback);
    }

   public static void deletFile(RequestParams params, BaseHttpRequestCallback callback)
   {
      String Url="http://"+myIp+"/FileAndController/wendang";
              //"http://111.230.17.38/baomingxitong/FileAndController/wendang";
      HttpRequest.post(Url,params,callback);
   }

    public static void getFile(RequestParams params, BaseHttpRequestCallback<HttpResult<List<Wendang>>> callback){
        String Url=
                //"http://192.168.1.116:8080/FileAndController/wendang";
                "http://"+myIp+"/FileAndController/wendang";   //http://10.0.2.2:8080/FileAndController/wendang/   http://111.230.17.38/baomingxitong/

      //  RequestBody body=params.getRequestBody();
        HttpRequest.post(Url,params,callback);
    }
   public static void uploadFile(RequestParams params, BaseHttpRequestCallback callback) {

        String fileuploadUri = //"http://192.168.1.116:8080/FileAndController/wendang";
                "http://"+myIp+"/FileAndController/scwendang";

        //上传的服务器地址，请求部分，返回response数据,
        HttpRequest.post(fileuploadUri, params, callback);
    }


    public static void dowloadFile(final String url, final String myFile, final FileDownloadCallback callback, final Context context) {

        HttpcallbackLister mylistener=new HttpcallbackLister() {
            @Override
            public void setContentlength(long contentlength) {
                File file=new File(myFile);
                if(file.exists()&&file.length()==contentlength){
                    if(context!=null)
                        IntentBuilder.viewFile(context,myFile);
                    System.out.println("文件已经存在");
                }
                else {
                    HttpRequest.download(url,file,callback);}
            }
        };
        getContentLength(url,mylistener);
    }

    private interface HttpcallbackLister{
        public long contentlength=0;
        void setContentlength(long contentlength);

    }

    private static void getContentLength(final String downloadUrl,final HttpcallbackLister httpcallbackLister){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url(downloadUrl)
                        .build();
                System.out.println("hello");
                long contentLength=0;
                try {
                    Response response=client.newCall(request).execute();
                    System.out.println("hello1");
                    if(response!=null&&response.isSuccessful()){
                        contentLength=response.body().contentLength();
                        System.out.println("hello2"+contentLength);
                        response.close();

                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
          httpcallbackLister.setContentlength(contentLength);


            }
        }).start();

}}