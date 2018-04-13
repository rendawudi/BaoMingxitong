package com.example.rd.baomingxitong.FileAndShipinView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.rd.baomingxitong.Http.HttpUtil;
import com.example.rd.baomingxitong.R;
import com.example.rd.baomingxitong.entity.FileAndShipin.Wendang;
import com.example.rd.baomingxitong.open_document.IntentBuilder;
import com.example.rd.baomingxitong.zidingyiView.MyListAdapter;
import com.example.rd.baomingxitong.zidingyiView.MyListview;
import com.example.rd.baomingxitong.zidingyiView.RoundProgressBarWidthNumber;

import java.util.List;

import cn.finalteam.okhttpfinal.FileDownloadCallback;


/**
 * Created by mick2017 on 2018/3/17.
 */

public class tab2_fragment extends Fragment implements AdapterView.OnItemClickListener {
    private String[] data={"1","2","3","4","5","2","3","4","5","2","3","4","5"};
    private List<Wendang> mWendang;

    private MyListview listView;
    private MyListAdapter adapter;
    public tab2_fragment() {
    }
    public tab2_fragment(List<Wendang> mWendang) {
        this.mWendang=mWendang;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment2,container,false);
        listView=(MyListview) view.findViewById(R.id.document);
        adapter=new MyListAdapter(getActivity(),R.layout.item_fragment2,mWendang);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(final AdapterView<?> adapterView, final View view, final int i, long l) {
        Log.d("hello", "i"+i);
    //    String url=urls.get(i);
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }


        //    String url=mWendang.get(i).getWeizhi();
        String url="http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-33-30.mp4";
        final RoundProgressBarWidthNumber round=view.findViewById(R.id.download);
        String fileName=url.substring(url.lastIndexOf("/"));
        String directory= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();

       final String myFile=directory+fileName;

        FileDownloadCallback callback=new FileDownloadCallback(){
            @Override
            public void onStart() {
                super.onStart();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        round.setVisibility(View.VISIBLE);
                        System.out.println("设置不可点击"+i);
                        view.setEnabled ( false );
                        view.setOnClickListener( new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                System.out.println("clicked");
                                IntentBuilder.viewFile(getActivity(),myFile);
                            }
                        } );
                    }
                });
            }

            @Override
            public void onProgress(int progress, long networkSpeed) {
                super.onProgress(progress, networkSpeed);
                round.setProgress(progress);
            }

            @Override
            public void onFailure() {
                super.onFailure();

            }

            @Override
            public void onDone() {
                super.onDone();
                IntentBuilder.viewFile(getActivity(),myFile);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("设置可点击"+i);
                        view.setEnabled(true);

                    }
                });
            }
        };
        HttpUtil.dowloadFile(url,myFile,callback,getActivity());
    }

    /**
     * Called when the view previously created by {@link #onCreateView} has
     * been detached from the fragment.  The next time the fragment needs
     * to be displayed, a new view will be created.  This is called
     * after {@link #onStop()} and before {@link #onDestroy()}.  It is called
     * <em>regardless</em> of whether {@link #onCreateView} returned a
     * non-null view.  Internally it is called after the view's state has
     * been saved but before it has been removed from its parent.
     */
    /**
     * Called when the view previously created by {@link #onCreateView} has
     * been detached from the fragment.  The next time the fragment needs
     * to be displayed, a new view will be created.  This is called
     * after {@link #onStop()} and before {@link #onDestroy()}.  It is called
     * <em>regardless</em> of whether {@link #onCreateView} returned a
     * non-null view.  Internally it is called after the view's state has
     * been saved but before it has been removed from its parent.
     */


}
