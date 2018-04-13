package com.example.rd.baomingxitong.zidingyiView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.rd.baomingxitong.entity.FileAndShipin.Wendang;

import java.util.List;

/**
 * Created by mick2017 on 2018/3/20.
 */

public class MyListAdapter extends ArrayAdapter<Wendang> {
    boolean isAllItemEnable=true;
    private int resouceId;
    public MyListAdapter(@NonNull Context context, int resource,List<Wendang> mysting) {
        super(context, resource, mysting);
        resouceId=resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View view= LayoutInflater.from(getContext()).inflate(resouceId,parent,false);


       return view;
    }

    @Override
    public boolean isEnabled(int position) {
        return super.isEnabled(position);
    }
}
