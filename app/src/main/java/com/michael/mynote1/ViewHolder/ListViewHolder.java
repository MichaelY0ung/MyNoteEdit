package com.michael.mynote1.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.michael.mynote1.R;
import com.michael.mynote1.Utils.MyItemClickListener;
import com.michael.mynote1.Utils.MyItemLongClickListener;

/**
 * Created by Michael on 2016/9/14.
 */
public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
    private TextView title;
    private TextView text;
    private TextView alarm_time;
    private ImageView alarm_status;
    private TextView status;
    private TextView tags;
    private TextView time;
    private MyItemClickListener myItemClickListener;
    private MyItemLongClickListener myItemLongClickListener;
    public ListViewHolder(View itemView,MyItemClickListener myItemClickListener,MyItemLongClickListener myItemLongClickListener) {
        super(itemView);
        this.myItemClickListener = myItemClickListener;
        this.myItemLongClickListener = myItemLongClickListener;
        title = (TextView)itemView.findViewById(R.id.today_title);
        text = (TextView)itemView.findViewById(R.id.today_text);
        alarm_time = (TextView)itemView.findViewById(R.id.today_alarm_time);
        alarm_status = (ImageView) itemView.findViewById(R.id.today_alarm_status);
        status = (TextView)itemView.findViewById(R.id.today_status);
        tags = (TextView)itemView.findViewById(R.id.today_tags);
        time = (TextView)itemView.findViewById(R.id.today_time);
        mixCLick(itemView);
        mixCLick(alarm_status);
        mixCLick(alarm_time);
        mixCLick(tags);
    }

    private void mixCLick(View view) {
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (myItemClickListener != null) {
            myItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(myItemLongClickListener!=null){
            myItemLongClickListener.onItemClick(v,getAdapterPosition());
        }
        return true;
    }
}
