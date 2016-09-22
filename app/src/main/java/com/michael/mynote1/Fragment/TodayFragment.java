package com.michael.mynote1.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.michael.mynote1.R;

/**
 * Created by Michael on 2016/9/10.
 */
public class TodayFragment extends Fragment {
    private static final String TYPE = "type";
    private View parentView;

    public TodayFragment(){
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.today_fragment,container,false);
        initView();
        return parentView;
    }

    private void initView() {
//        RecyclerView recyclerView  =(RecyclerView)parentView.findViewById(R.id.today_recycleview);
        TextView textView = (TextView)parentView.findViewById(R.id.today_text);
        textView.setText(getArguments().getString(TYPE, "Default"));
    }
    public static TodayFragment newInstance(String text){
        TodayFragment fragment = new TodayFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE, text);
        fragment.setArguments(bundle);
        return fragment;
    }
}
