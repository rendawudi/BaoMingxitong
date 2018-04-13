package com.example.rd.baomingxitong.FileAndShipinView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rd.baomingxitong.R;
import com.example.rd.baomingxitong.base.MyApp;

/**
 * Created by mick2017 on 2018/3/17.
 */

public class tab1_fragment extends Fragment {
    private String introduce;

    public tab1_fragment() {
    }

    @SuppressLint("ValidFragment")
    public tab1_fragment(String introduce) {
        this.introduce=introduce;
    }


    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment1,container,false);
        TextView title=(TextView) view.findViewById(R.id.xiangmutitle);
        TextView jianjie=(TextView) view.findViewById(R.id.xiangmujianjie);
        TextView laoshi=(TextView) view.findViewById(R.id.xiangmulaoshi);
        TextView dqrenshu=(TextView) view.findViewById(R.id.yiyourenshu);


        TextView zongrenshu=(TextView) view.findViewById(R.id.xiangmuzongrenshu);
        TextView kaishishijian=(TextView) view.findViewById(R.id.kaishishijian);
        TextView jieshushijian=(TextView) view.findViewById(R.id.jieshushijian);

        title.setText(MyApp.instances.xiangmuXs.getXiangmu());
        jianjie.setText(MyApp.instances.xiangmuXs.getJianjie());
        laoshi.setText(MyApp.instances.xiangmuXs.getLaoshi());
        dqrenshu.setText(String.valueOf(MyApp.instances.xiangmuXs.getDqrenshu()));
       zongrenshu.setText(String.valueOf(MyApp.instances.xiangmuXs.getRenshu()));
        kaishishijian.setText(MyApp.instances.xiangmuXs.getStartTime().toString());
        jieshushijian.setText(MyApp.instances.xiangmuXs.getEndTime().toString());
        return view;
    }
    private String getString(String name){
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<500;i++){
            stringBuilder.append(name);

        }
        stringBuilder.append("end");
        return stringBuilder.toString();
    }
}
