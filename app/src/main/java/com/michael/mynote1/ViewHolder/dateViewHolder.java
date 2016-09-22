package com.michael.mynote1.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.michael.mynote1.R;

/**
 * Created by Michael on 2016/9/14.
 */
public class dateViewHolder extends RecyclerView.ViewHolder{
    private TextView date;
    private TextView count;
    public dateViewHolder(View itemView) {
        super(itemView);
        date = (TextView)itemView.findViewById(R.id.today_date);
        count = (TextView)itemView.findViewById(R.id.today_count);
    }
}
