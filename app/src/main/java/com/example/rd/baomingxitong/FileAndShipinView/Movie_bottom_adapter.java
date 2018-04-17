package com.example.rd.baomingxitong.FileAndShipinView;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.rd.baomingxitong.R;
import com.example.rd.baomingxitong.entity.FileAndShipin.Vedio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mick2017 on 2018/3/18.
 */

public class Movie_bottom_adapter extends RecyclerView.Adapter <Movie_bottom_adapter.ViewHolder>{
    ArrayList<String> list = new ArrayList<String>();
    private RecyclerView mRv;
    public static int mSelectedPos = 0;
    private movieListener listener;
    private List<Vedio> vedios;
    public Movie_bottom_adapter(List<Vedio> list, RecyclerView recyclerView,movieListener listener) {
        this.vedios= list;
        mRv=recyclerView;
        this.listener=listener;
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public Movie_bottom_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_bottom,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final Movie_bottom_adapter.ViewHolder holder, final int position) {
        final Vedio vedio=vedios.get(position);
        holder.textView.setText(vedio.getVedioname());

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewHolder mholder=(ViewHolder) mRv.findViewHolderForLayoutPosition(mSelectedPos);
                if (mholder != null) {//还在屏幕里
                    mholder.textView.setTextColor(Color.BLACK);
                }
                mSelectedPos=position;
                holder.textView.setTextColor(Color.BLUE);
                listener.choose(vedio.getPath(),vedio.getVedioname());
            }
        });
       if(position==mSelectedPos){
           holder.textView.setTextColor(Color.BLUE);
       }else {
           holder.textView.setTextColor(Color.BLACK);
       }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return vedios.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public boolean checked=false;

        public void setCheck(boolean checked){
            this.checked=checked;
        }
        public ViewHolder(View itemView) {
            super(itemView);
            textView=(TextView) itemView.findViewById(R.id.choose_movie);

        }
        public void autoClick(){
            textView.performClick();
        }
    }
}
