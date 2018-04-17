package com.example.rd.baomingxitong.FileAndShipinView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.rd.baomingxitong.R;
import com.example.rd.baomingxitong.entity.FileAndShipin.Vedio;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;
import com.xiao.nicevideoplayer.next_movie;


import java.util.ArrayList;
import java.util.List;

public class MoviePlayActivity extends AppCompatActivity {

    //第三方nicepalyer有问题，关于重新播放不能从开始播放。
    private NiceVideoPlayer player;
    private LinearLayout mLayout;
    private RecyclerView mrecycler;
    private TextView detai_intro;
   private ArrayList<String> strArray = new ArrayList<String>();
   private List<Vedio> Vedios=new ArrayList<>();
   private NestedScrollView myscroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.movie_play);
        Intent intent = this.getIntent();
   //     Vedios=(List<Vedio>) intent.getSerializableExtra("shiping");

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int mHeight=dm.heightPixels;
        mLayout=(LinearLayout) findViewById(R.id.myLine);
        ViewGroup.LayoutParams lp=(ViewGroup.LayoutParams) mLayout.getLayoutParams();
        lp.height=(mHeight)/3;
        mLayout.setLayoutParams(lp);
        myscroll=(NestedScrollView) findViewById(R.id.my_scroll);
        detai_intro=(TextView) findViewById(R.id.detail_introduce);
        player=(NiceVideoPlayer) findViewById(R.id.movie_player);
        player.setPlayerType(NiceVideoPlayer.TYPE_NATIVE); // IjkPlayer or MediaPlayer
        String videoUrl = "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-33-30.mp4";
//        videoUrl = Environment.getExternalStorageDirectory().getPath().concat("/办公室小野.mp4");
        player.setUp(videoUrl, null);
     final TxVideoPlayerController controller = new TxVideoPlayerController(MoviePlayActivity.this);
        controller.setTitle("办公室小野开番外了，居然在办公室开澡堂！老板还点赞？");
     //   controller.setLenght(98000);
        Glide.with(this)
                .load("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-30-43.jpg")
                .placeholder(R.drawable.img_default)
                .crossFade()
                .into(controller.imageView());


        mrecycler=(RecyclerView) findViewById(R.id.movie_player_bottom);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mrecycler.setLayoutManager(layoutManager);
        Vedio myvedio1=new Vedio();
        Vedio myvedio2=new Vedio();
        Vedio myvedio3=new Vedio();
        myvedio1.setPath("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-33-30.mp4");
        myvedio1.setVedioname("第一集");
        Vedios.add(myvedio1);
        myvedio2.setPath("https://cdn.qupeiyin.cn/2017-05-13/14946480892464422254.mp4");
        myvedio2.setVedioname("第二集");
        Vedios.add(myvedio2);
        myvedio3.setPath("https://cdn.qupeiyin.cn/2018-02-26/m1470991672332.mp4");
        myvedio3.setVedioname("第三集");
        Vedios.add(myvedio3);

        movieListener listener=new movieListener() {
            @Override
            public void choose(String url,String name) {
                //释放播放器
                player.releasePlayer();
                player.setPlayerType(NiceVideoPlayer.TYPE_NATIVE);
                player.setUp(url, null);
                controller.setTitle(name);
                player.setController(controller);
                player.start();

           /*     if(player.getLast_state()==NiceVideoPlayer.type_mini)
                    player.enterTinyWindow();
                else if(player.getLast_state()==NiceVideoPlayer.type_full)
                    player.enterFullScreen();*/


            }
        };
        Movie_bottom_adapter adapter=new Movie_bottom_adapter(Vedios,mrecycler,listener);
        mrecycler.setAdapter(adapter);
        detai_intro.setText(getString("hello"));
        next_movie next_movie_callback=new next_movie() {
            @Override
            public void next() {
                View view=mrecycler.getChildAt(Movie_bottom_adapter.mSelectedPos+1);
                if(view!=null){
                    Movie_bottom_adapter.ViewHolder viewHolder=(Movie_bottom_adapter.ViewHolder) mrecycler.getChildViewHolder(view);
                    viewHolder.autoClick();
                }
                else {
                    Toast.makeText(MoviePlayActivity.this,"已经是最后一集",Toast.LENGTH_SHORT).show();
                }
            }
        };

        controller.setmycallback(next_movie_callback);
        player.setController(controller);

        myscroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:

                        if (v.getScrollY() > 0&&!player.isIdle()&&player.isPlaying()) {

                           player.enterTinyWindow();
                        } else if (v.getScrollY()==0) {

                            if(player.isTinyWindow()){
                                player.exitTinyWindow();
                            }
                        }
                        break;
                    default:
                        break;
                }
                return false;
            } });
    }

    @Override
    protected void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
        System.out.println("onStop");
    }

    //需要进入两次enterthin才能恢复正正确的窗口
    @Override
    public void onBackPressed() {

        if(player.isFullScreen()){
        if (NiceVideoPlayerManager.instance().onBackPressd())
        {
            System.out.println("点击返回");
            if(myscroll.getScrollY()>0)
            player.enterTinyWindow();
            return;
        }}
        super.onBackPressed();
    }
    private String getString(String name){
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<500;i++){
            stringBuilder.append(name);

        }
        stringBuilder.append("end");
        return stringBuilder.toString();
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */



}
