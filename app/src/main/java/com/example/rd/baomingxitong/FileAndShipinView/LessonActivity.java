package com.example.rd.baomingxitong.FileAndShipinView;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;


import com.example.rd.baomingxitong.Http.HttpUtil;
import com.example.rd.baomingxitong.R;
import com.example.rd.baomingxitong.base.MyApp;
import com.example.rd.baomingxitong.entity.FileAndShipin.Lesson;
import com.example.rd.baomingxitong.entity.FileAndShipin.Wendang;
import com.example.rd.baomingxitong.entity.HttpResult;
import com.example.rd.baomingxitong.entity.Task.wendang;
import com.example.rd.baomingxitong.entity.myHttpResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leon.lfilepickerlibrary.LFilePicker;
import com.melnykov.fab.FloatingActionButton;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.FileWrapper;
import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Response;

public class LessonActivity extends BaseActivity implements recycle_item_listener{
    private FloatingActionButton button;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout lesson_refresh;
    private final int REQUESTCODE_FROM_ACTIVITY = 1000;  //选择文件后的请求id
    private UploadBinder uploadBinder;
    //后台上传服务
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            uploadBinder=(UploadBinder) service;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kecheng_recycler);
        mRecyclerView=(RecyclerView) findViewById(R.id.playlist);
        button=(FloatingActionButton) findViewById(R.id.lesson_add);
        lesson_refresh=(SwipeRefreshLayout) findViewById(R.id.lesson_refresh);
        lesson_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();
            }
        });
        button.attachToRecyclerView(mRecyclerView);
        //上传文件按钮
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String directory= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                //   String directory= Environment.getRootDirectory().getPath();
                // Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                new LFilePicker()
                        .withActivity(LessonActivity.this)
                        .withRequestCode(REQUESTCODE_FROM_ACTIVITY)
                        .withStartPath(directory)
                        .withIsGreater(true)
                        .start();
            }
        });

        init();
        Intent intent=new Intent(this,myService.class);
        startService(intent);
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    //请求数据与初始化界面
    public void init(){

        LessonAdapter adapter=new LessonAdapter(LessonActivity.this,getPlaylist(),this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);

        BaseHttpRequestCallback<myHttpResult<List<Lesson>>> mycallback=new BaseHttpRequestCallback<myHttpResult<List<Lesson>>>(){
            @Override
            public void onResponse(Response httpResponse, String response, Headers headers) {
                super.onResponse(httpResponse, response, headers);
                System.out.println(response);
                System.out.println("返回值"+response+httpResponse);
                Gson gson1=new Gson();
                Type listype=new TypeToken<HttpResult<List<Lesson>>>(){}.getType();
                final HttpResult<List<Lesson>> result=gson1.fromJson(response,listype);

                //请求数据，正式测试时候请删除result.getData().size()!=0
                if(result.getCode()==200&&result.getMsg().equals("success")&&result.getData().size()!=0)
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lesson_refresh.setRefreshing(false);
                        lesson_refresh.setEnabled(false);
                        LessonAdapter adapter=new LessonAdapter(LessonActivity.this,result.getData(),LessonActivity.this);

                        mRecyclerView.setAdapter(adapter);
                    }
                });
            }



            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lesson_refresh.setRefreshing(false);
                    }
                });
                Toast.makeText(LessonActivity.this,"获取数据失败，请下拉刷新",Toast.LENGTH_SHORT).show();
            }
        };
        RequestParams params=new RequestParams(LessonActivity.this);
        Lesson lesson=new Lesson();
        //填写相关信息

        params.applicationJson();
        params.addHeader("Cookie", MyApp.instances.getSharedPreferences("cookies_prefs", MyApp.instances.getContext().MODE_PRIVATE).getString("bmcookies", ""));
        params.addFormDataPart("piciId","sb");
        params.addFormDataPart("xiangmuId","sb");
       HttpUtil.Get(HttpUtil.type_lesson,params,mycallback);
    }

    public List<Lesson> getPlaylist(){
        List<Lesson> videoList = new ArrayList<>();
        Lesson mylesson=new Lesson();
        mylesson.setCourse("kecheng");
        mylesson.setUrl("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-30-43.jpg");
        mylesson.setJianjie("jieshao");
        videoList.add(mylesson);
        videoList.add(mylesson);
        videoList.add(mylesson);
        videoList.add(mylesson);
        videoList.add(mylesson);
        videoList.add(mylesson);
        return videoList;
    }

    //悬浮按钮文件选择后上传
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTCODE_FROM_ACTIVITY ) {
                List<String> list = data.getStringArrayListExtra("paths");
                Toast.makeText(getApplicationContext(), "选中了" + list.get(0) + "个文件", Toast.LENGTH_SHORT).show();
                List<FileWrapper> files = new ArrayList<>();
                for(String mylist:list){
                    FileWrapper myFileWrapper=new FileWrapper(new File(mylist), MediaType.parse("application/octet-stream"));
                    System.out.println(mylist+"      upload file format=="+MediaType.parse("application/octet-stream"));
                    files.add(myFileWrapper);
                }
                //上传文件选择
                Lesson lesson=new Lesson();
               //得到项目信息,对lesson进行设计


                RequestParams params = new RequestParams(LessonActivity.this);//请求参数
                //         params.addFormDataPart("username", mUserName);//表单数据
                //        params.addFormDataPart("password", mPassword);//表单数据
                //       params.addFormDataPart("file", file);//上传单个文件

                params.addFormDataPart("myfiles", files);//上传多个文件

                params.addFormDataPart("piciId","sb");
                params.addFormDataPart("xiangmuId","sb");
                params.addHeader("Cookie", MyApp.instances.getSharedPreferences("cookies_prefs", MyApp.instances.getContext().MODE_PRIVATE).getString("bmcookies", ""));
                //       params.addHeader("token", token);//添加header信息
                upload_success callback=new upload_success() {
                    @Override
                    public void success() {
                       init();
                    }
                };
                if(!files.isEmpty()&&uploadBinder!=null)
                    uploadBinder.startUpload(params,callback);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    @Override
    public void item_click(int pos) {
        BaseHttpRequestCallback<myHttpResult<List<Lesson>>> mycallback=new BaseHttpRequestCallback<myHttpResult<List<Lesson>>>(){
            @Override
            public void onResponse(Response httpResponse, String response, Headers headers) {
                super.onResponse(httpResponse, response, headers);
                System.out.println(response);
                System.out.println("返回值"+response+httpResponse);
                Gson gson1=new Gson();
                Type listype=new TypeToken<HttpResult<List<Lesson>>>(){}.getType();
                final HttpResult<List<Lesson>> result=gson1.fromJson(response,listype);

                //请求数据，正式测试时候请删除result.getData().size()!=0
                if(result.getCode()==200&&result.getMsg().equals("success")){
                    Intent intent=new Intent(LessonActivity.this,MoviePlayActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("shiping",(Serializable)result.getData());//序列化,要注意转化(Serializable)
                intent.putExtras(bundle);//发送数据
                startActivity(intent);}
            }



            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);

                Toast.makeText(LessonActivity.this,"获取课程信息失败",Toast.LENGTH_SHORT).show();
            }
        };
        RequestParams params=new RequestParams(LessonActivity.this);
        Lesson lesson=new Lesson();
        //填写相关信息

        params.applicationJson();
        params.addHeader("Cookie", MyApp.instances.getSharedPreferences("cookies_prefs", MyApp.instances.getContext().MODE_PRIVATE).getString("bmcookies", ""));
        params.addFormDataPart("piciId","sb");
        params.addFormDataPart("xiangmuId","sb");
        HttpUtil.Get(HttpUtil.type_shiping,params,mycallback);

  /*      RequestParams params=new RequestParams(this);
        params.applicationJson();
        params.addFormDataPart("piciId","sb");
        params.addFormDataPart("xiangmuId","sb");
        //设置vId，出错，后端问题？
        //       params.addFormDataPart("vId", MyApp.instances.lesson.getvId());
        BaseHttpRequestCallback<myHttpResult<List<Lesson>>> mycallback=new BaseHttpRequestCallback<myHttpResult<List<Lesson>>>(){
            @Override
            public void onResponse(Response httpResponse, String response, Headers headers) {
                super.onResponse(httpResponse, response, headers);

                System.out.println("返回值"+response+httpResponse);
                Gson gson1=new Gson();
                Type listype=new TypeToken<HttpResult<List<Lesson>>>(){}.getType();
                HttpResult<List<Lesson>> result=gson1.fromJson(response,listype);

                //请求数据，正式测试时候请删除result.getData().size()!=0
                if(result.getCode()==200&&result.getMsg().equals("success")){

                    Intent intent=new Intent(LessonActivity.this,MoviePlayActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("shiping",(Serializable)result.getData());//序列化,要注意转化(Serializable)
                    intent.putExtras(bundle);//发送数据
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);

                Toast.makeText(LessonActivity.this,"获取课程信息失败",Toast.LENGTH_SHORT).show();
            }
        };
        HttpUtil.Get(HttpUtil.type_lesson,params,mycallback);*/
    }
}
