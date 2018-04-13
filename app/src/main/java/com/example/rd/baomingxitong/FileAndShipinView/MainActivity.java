package com.example.rd.baomingxitong.FileAndShipinView;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.rd.baomingxitong.Http.HttpUtil;
import com.example.rd.baomingxitong.Http.RetrofitHelper;
import com.example.rd.baomingxitong.R;
import com.example.rd.baomingxitong.base.MyApp;
import com.example.rd.baomingxitong.entity.FileAndShipin.Wendang;
import com.example.rd.baomingxitong.entity.HttpResult;
import com.example.rd.baomingxitong.views.TaskActivity;
import com.example.rd.baomingxitong.views.TaskXsActivity;
import com.example.rd.baomingxitong.zidingyiView.MyFragmentAdapter;
import com.google.gson.Gson;
import com.leon.lfilepickerlibrary.LFilePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.FileWrapper;

import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class MainActivity extends BaseActivity {
    private SwipeRefreshLayout swipeRefresh;
    private RadioButton first,second,thirst,joinButton;
    private ViewPager viewPager;
    private List<Fragment> list=new ArrayList<Fragment>();
    private MyFragmentAdapter adapter;
    private MainActivity activity;
    private final int[] array={R.id.tab1_button,R.id.tab2_button,R.id.tab3_button};
    private final int REQUESTCODE_FROM_ACTIVITY = 1000;  //选择文件后的请求id
    private myService.UploadBinder uploadBinder;
    //后台上传服务
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            uploadBinder=(myService.UploadBinder) service;
            if(service!=null)
                System.out.print("error");
            if(service==null)
                System.out.print("error1");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        activity = this;
        viewPager=(ViewPager) findViewById(R.id.view_pager);
        first=(RadioButton) findViewById(R.id.tab1_button);
        second=(RadioButton) findViewById(R.id.tab2_button);
        thirst=(RadioButton) findViewById(R.id.tab3_button);
        joinButton = (RadioButton) findViewById(R.id.jiaruxiangmu);
       swipeRefresh=(SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        first.setChecked(true);
        // Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        //  setSupportActionBar(toolbar);

        setJiaruButton(false);
        RetrofitHelper.Companion.getInsance().getPermission(MyApp.instances.xiangmuXs.getXiangmuId(), MyApp.instances.xiangmuXs.getPiciId(), activity);

       swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestdata();
                first.setChecked(true);
            }
        });

        Intent intent=new Intent(this,myService.class);
        startService(intent);
        bindService(intent,connection,BIND_AUTO_CREATE);
       requestdata();
    }

    public void setJiaruButton(Boolean sfJoined)
    {
        if (sfJoined)
        {
            joinButton.setText("开始填表");
            joinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //更改图标

                    startActivity(new Intent(getContext(), TaskXsActivity.class));
                }
            });
        }
        else
            joinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //判断时间



                    RetrofitHelper.Companion.getInsance().qiangXM(MyApp.instances.xiangmuXs.getXiangmuId(), MyApp.instances.xiangmuXs.getPiciId(), activity);
                }
            });
    }

    public void pdSfSucess(Integer integer)
    {
        if (integer.equals(0))
        {
            Toast.makeText(this, "报名成功，请填写队员信息", Toast.LENGTH_SHORT).show();
            setJiaruButton(true);
        }
        else if (integer.equals(112))
        {
            Toast.makeText(this, "同一批次不能报名两个项目", Toast.LENGTH_SHORT).show();
        }
        else if (integer.equals(111))
        {
            Toast.makeText(this, "当前项目报名人数已满", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show();

        }
    }


    //悬浮按钮
    public void onClickFab(View v){
        if(second.isChecked()){
            String directory=
                   Environment.getRootDirectory().getPath();
           // Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            new LFilePicker()
                    .withActivity(this)
                    .withRequestCode(REQUESTCODE_FROM_ACTIVITY)
                    .withStartPath(directory)
                    .withIsGreater(true)
                    .start();
        }
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

                Wendang wendang=new Wendang();
                wendang.setXiangmuId(MyApp.instances.xiangmuXs.getXiangmuId());
                wendang.setPiciId(MyApp.instances.xiangmuXs.getPiciId());
                //得到项目信息

                RequestParams params = new RequestParams(MainActivity.this);//请求参数
                //         params.addFormDataPart("username", mUserName);//表单数据
                //        params.addFormDataPart("password", mPassword);//表单数据
                //       params.addFormDataPart("file", file);//上传单个文件

                params.addFormDataPart("myfiles", files);//上传多个文件

                params.addFormDataPart("piciId",wendang.getPiciId());
                params.addFormDataPart("xiangmuId",wendang.getXiangmuId());
                params.addHeader("Cookie", MyApp.instances.getSharedPreferences("cookies_prefs", MyApp.instances.getContext().MODE_PRIVATE).getString("bmcookies", ""));
                //       params.addHeader("token", token);//添加header信息
                if(!files.isEmpty()&&uploadBinder!=null)
                    uploadBinder.startUpload(params);
            }
        }
    }


    public void init(List<Wendang> wendangs){

  //      tab1_fragment fragment1=new tab1_fragment(Kecheng.introduce);
   //     tab2_fragment fragment2=new tab2_fragment(Kecheng.titles,Kecheng.urls);
           tab1_fragment fragment1=new tab1_fragment();

           tab2_fragment fragment2=new tab2_fragment(wendangs);

            list.add(fragment1);
        list.add(fragment2);
        adapter=new MyFragmentAdapter(getSupportFragmentManager(),list);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(0);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (array[position]){
                    case R.id.tab1_button:
                        first.setChecked(true);
                        break;
                    case R.id.tab2_button:
                        second.setChecked(true);
                        break;
                    case R.id.tab3_button:
                        thirst.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        RadioGroup group=(RadioGroup) findViewById(R.id.radioGroup);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.tab1_button:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.tab2_button:
                        viewPager.setCurrentItem(1);
                        break;
                }
            }
        });

    }

    //
    public  void requestdata(){

        RequestParams params = new RequestParams(MainActivity.this);
        Wendang wendang=new Wendang();
        wendang.setPiciId(MyApp.instances.xiangmuXs.getPiciId());
        wendang.setXiangmuId(MyApp.instances.xiangmuXs.getXiangmuId());

        params.applicationJson();
        params.addHeader("Cookie", MyApp.instances.getSharedPreferences("cookies_prefs", MyApp.instances.getContext().MODE_PRIVATE).getString("bmcookies", ""));
        params.addFormDataPart("piciId",wendang.getPiciId());
        params.addFormDataPart("xiangmuId",wendang.getXiangmuId());
       // RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(wendang));
      //  params.setCustomRequestBody(body);
        System.out.println(params.toString());

        BaseHttpRequestCallback<HttpResult<List<Wendang>>> callback=new BaseHttpRequestCallback<HttpResult<List<Wendang>>>(){
            @Override
            public void onResponse(String response, Headers headers) {
                super.onResponse(response, headers);
                System.out.println("response=="+response);
                Gson gson1=new Gson();
                final HttpResult<List<Wendang>> result=gson1.fromJson(response,HttpResult.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                        if(result.getCode()==200&&result.getMsg().equals("success")){
                            init(result.getData());
                        }
                    }
                });
            }



            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                        Log.d("onFailure", "run: ");
                        Toast.makeText(MainActivity.this,"请下拉刷新",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
    HttpUtil.getFile(params,callback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]!= PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"not use",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:break;
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unbindService(connection);
    }


}
