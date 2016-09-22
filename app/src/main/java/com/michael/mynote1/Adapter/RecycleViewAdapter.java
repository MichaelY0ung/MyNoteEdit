package com.michael.mynote1.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.michael.mynote1.Bean.data;
import com.michael.mynote1.Utils.MyItemClickListener;
import com.michael.mynote1.Utils.MyItemLongClickListener;

import java.util.ArrayList;

/**
 * Created by Michael on 2016/9/11.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context mContext;
    private final ArrayList<data> mArrayList;
    private MyItemClickListener myItemClickListener;
    private MyItemLongClickListener myItemLongClickListener;
    public void setMyItemClickListener(MyItemClickListener myItemClickListener){
        this.myItemClickListener = myItemClickListener;
    }
    public void setMyItemLongClickListener(MyItemLongClickListener myItemLongClickListener){
        this.myItemLongClickListener = myItemLongClickListener;
    }
    public RecycleViewAdapter(Context context, ArrayList<data> arrayList){
        mContext = context;
        mArrayList = arrayList;
    }

    @Override
    public int getItemViewType(int position) {
        int i;
        if(position==0){
            i= 1;
        }
        else{
            if(mArrayList.get(position).date.equals(mArrayList.get(position-1).date)){
                i= 0;
            }
            else{
                i= 1;
            }
        }
        return null==mArrayList?0:i;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
