package com.example.rd.baomingxitong.FileAndShipinView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rd.baomingxitong.R;
import com.example.rd.baomingxitong.entity.FileAndShipin.Lesson;

/**
 * Created by mick2017 on 2018/3/16.
 */

public class LessonHolder extends RecyclerView.ViewHolder {
    private ImageView coverPicture;
    private TextView coverText1;
    private TextView coverText2;
    public View mView;
    public LessonHolder(Context context, View itemView) {
        super(itemView);
        mView=itemView;
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics dm = new DisplayMetrics();

        wm.getDefaultDisplay().getMetrics(dm);
        int mWidth=dm.widthPixels;

        coverPicture=(ImageView) itemView.findViewById(R.id.kecheng);
        ViewGroup.LayoutParams lp=(ViewGroup.LayoutParams) coverPicture.getLayoutParams();
//将宽度设置为屏幕的1/3
        lp.width=mWidth/3;
        lp.height=(mWidth*3)/14;
        coverPicture.setLayoutParams(lp);
        coverText1=(TextView) itemView.findViewById(R.id.title);
        coverText2=(TextView) itemView.findViewById(R.id.introduce);
    }

    public void bindcover(Lesson mcover){
        coverText1.setText(mcover.getCourse());
        coverText2.setText(mcover.getJianjie());
        Glide.with(itemView.getContext())
                .load(mcover.getUrl())

                .crossFade()
                .into(coverPicture);
    }
}
