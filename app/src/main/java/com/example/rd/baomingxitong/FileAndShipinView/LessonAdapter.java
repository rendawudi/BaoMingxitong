package com.example.rd.baomingxitong.FileAndShipinView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.rd.baomingxitong.Http.HttpUtil;
import com.example.rd.baomingxitong.R;
import com.example.rd.baomingxitong.base.MyApp;
import com.example.rd.baomingxitong.entity.FileAndShipin.Lesson;
import com.example.rd.baomingxitong.entity.HttpResult;
import com.example.rd.baomingxitong.entity.myHttpResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * Created by mick2017 on 2018/3/16.
 */

public class LessonAdapter extends RecyclerView.Adapter<LessonHolder> {
    private Context mContext;
    private List<Lesson> mVideoList;
    private recycle_item_listener recycle_item_listener;
    public LessonAdapter(Context mContext, List<Lesson> mVideoList,recycle_item_listener callback) {
        this.mContext = mContext;
        this.mVideoList = mVideoList;
        recycle_item_listener=callback;
    }

    @Override
    public LessonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kecheng, parent, false);

        LessonHolder holder=new LessonHolder(mContext,itemView);

        return holder;
    }


    @Override
    public void onBindViewHolder(LessonHolder holder, final int position) {
      Lesson mCover=mVideoList.get(position);
      holder.bindcover(mCover);
      holder.mView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              //请求视屏相关信息成功后进入视屏播放界面

           recycle_item_listener.item_click(position);
          }
      });

      holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
          @Override
          public boolean onLongClick(View view) {
              showPopMenu(view,position);
              return true;
          }
      });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    public void showPopMenu(View view, final int pos){
        PopupMenu popupMenu = new PopupMenu(mContext,view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_item,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                mVideoList.remove(pos);
                notifyItemRemoved(pos);
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                //     Toast.makeText(getContext(), "关闭PopupMenu", Toast.LENGTH_SHORT).show();
            }
        });
        popupMenu.show();
    }
}
