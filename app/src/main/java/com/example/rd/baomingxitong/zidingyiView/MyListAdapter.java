package com.example.rd.baomingxitong.zidingyiView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rd.baomingxitong.FileAndShipinView.MainActivity;
import com.example.rd.baomingxitong.Http.HttpUtil;
import com.example.rd.baomingxitong.R;
import com.example.rd.baomingxitong.base.MyApp;
import com.example.rd.baomingxitong.entity.FileAndShipin.Wendang;
import com.example.rd.baomingxitong.entity.myHttpResult;

import java.util.List;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * Created by mick2017 on 2018/3/20.
 */

public class MyListAdapter extends ArrayAdapter<Wendang> {
    boolean isAllItemEnable=true;
    private int resouceId;
    private List<Wendang> mysting;
    private Context mycontext;
    public MyListAdapter(@NonNull Context context, int resource, List<Wendang> mysting) {
        super(context, resource, mysting);
        resouceId=resource;
        this.mysting=mysting;
        mycontext=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View view= LayoutInflater.from(getContext()).inflate(resouceId,parent,false);
        TextView textView=view.findViewById(R.id.document_name);
        ImageView imageView=view.findViewById(R.id.documemt_image);
       Wendang wendang=mysting.get(position);
       String name=wendang.getMingcheng();
       if(name!=null){
        textView.setText(name);
        name=name.substring(name.lastIndexOf('.')+1);
        name=name.toLowerCase();
        if(name.equals("pdf")){
         imageView.setImageResource(R.drawable.ic_picture_as_pdf_cyan_700_24dp);
        }
        else {
            imageView.setImageResource(R.drawable.ic_live_help_cyan_700_24dp);
        }
       }
       return view;
    }

    @Override
    public boolean isEnabled(int position) {
        return super.isEnabled(position);
    }

    public  void removeItem(final int pos){

                RequestParams params=new RequestParams((HttpCycleContext) mycontext);
                params.applicationJson();
                params.addFormDataPart("wjId",mysting.get(pos).getWjId());
               params.addHeader("Cookie", MyApp.instances.getSharedPreferences("cookies_prefs", MyApp.instances.getContext().MODE_PRIVATE).getString("bmcookies", ""));
                BaseHttpRequestCallback<myHttpResult> mycallback=new BaseHttpRequestCallback<myHttpResult>(){
                    @Override
                    protected void onSuccess(myHttpResult myHttpResult) {
                        super.onSuccess(myHttpResult);
                        if(myHttpResult.getCode()==200){
                            Toast.makeText(mycontext,"删除成功",Toast.LENGTH_SHORT).show();
                            mysting.remove(pos);
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(int errorCode, String msg) {
                        super.onFailure(errorCode, msg);
                        Toast.makeText(mycontext,"删除失败",Toast.LENGTH_SHORT).show();
                    }
                };
             //   HttpUtil.deletFile(params,mycallback);
        HttpUtil.Delet(HttpUtil.type_document,params,mycallback);
            }



    }

