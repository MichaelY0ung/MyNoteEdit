package com.michael.mynote1.myView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.michael.mynote1.R;
import com.michael.mynote1.Utils.OnPasswordInputFinish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Michael on 2016/9/18.
 */
public class passwordView  implements View.OnClickListener {
    private PopupWindow popwindow;
    private  View passwordView;
    private  GridView gridView;
    private  ImageView backImage;
    private  TextView forgetPassword;
    private TextView[] textList;
    private String password;
    private int count = -1;
    private HashMap valueMap;
    private Context mContext;
    private View parentView;
    private ArrayList<Map<String, String>> list;

    public passwordView(Context context) {
        passwordView = initView(context);
        initData();
        mContext = context;
        backImage = (ImageView)passwordView.findViewById(R.id.password_back);
        forgetPassword = (TextView)passwordView.findViewById(R.id.forget_password);
        gridView = (GridView)passwordView.findViewById(R.id.password_gridview);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 11 && position != 9) {    //点击0~9按钮
                    if (count >= -1 && count < 5) {      //判断输入位置————要小心数组越界
                        textList[++count].setText(list.get(position).get("name"));

                    }
                } else {
                    if (position == 11) {      //点击退格键
                        Toast.makeText(mContext,"del",Toast.LENGTH_SHORT).show();
                        if (count - 1 >= -1) {      //判断是否删除完毕————要小心数组越界
                            textList[count--].setText("");
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
    private View initView(Context context) {
        View passwordView=  LayoutInflater.from(context).inflate(R.layout.password_layout,null);
        mContext = context;
        textList = new TextView[6];
        textList[0] = (TextView) passwordView.findViewById(R.id.password1);
        textList[1] = (TextView) passwordView.findViewById(R.id.password2);
        textList[2] = (TextView) passwordView.findViewById(R.id.password3);
        textList[3] = (TextView) passwordView.findViewById(R.id.password4);
        textList[4] = (TextView) passwordView.findViewById(R.id.password5);
        textList[5] = (TextView) passwordView.findViewById(R.id.password6);
//        buttonList[0] = (Button) passwordView.findViewById(R.id.psw_one);
//        buttonList[1] = (Button) passwordView.findViewById(R.id.psw_two);
//        buttonList[2] = (Button) passwordView.findViewById(R.id.psw_three);
//        buttonList[3] = (Button) passwordView.findViewById(R.id.psw_four);
//        buttonList[4] = (Button) passwordView.findViewById(R.id.psw_five);
//        buttonList[5] = (Button) passwordView.findViewById(R.id.psw_six);
//        buttonList[6] = (Button) passwordView.findViewById(R.id.psw_seven);
//        buttonList[7] = (Button) passwordView.findViewById(R.id.psw_eight);
//        buttonList[8] = (Button)passwordView.findViewById(R.id.psw_nine);
//        buttonList[9] = (Button) passwordView.findViewById(R.id.psw_zero);
//       delete = (ImageButton) passwordView.findViewById(R.id.psw_del);

        return passwordView;
    }
    public void setParent(View parentView){
        this.parentView = parentView;
    }
    public void showPopupWindow(){
        popwindow = new PopupWindow(passwordView, LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        popwindow.setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), (Bitmap) null));
        popwindow.setTouchable(true);
        popwindow.setOutsideTouchable(false);
        popwindow.setAnimationStyle(R.style.Type_Animation_Style);
        popwindow.showAtLocation(parentView, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
    }
    public void dismiss(){
        popwindow.dismiss();
    }

    private void initData() {
        list = new ArrayList();
        for(int i=1;i<13;i++){
            valueMap = new HashMap<>();
            if(i<10){
                valueMap.put("name",String.valueOf(i));
            }
            else if(i==10){
                valueMap.put("name","");
            }
            else if (i==11){
                valueMap.put("name",String.valueOf(0));
            }
            else{
                valueMap.put("name","delete");
            }
            list.add(valueMap);
        }
    }



    BaseAdapter adapter = new BaseAdapter(){

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d("11111",""+position);
            ViewHolder viewHolder;
            if(convertView!=null){
                viewHolder = (ViewHolder)convertView.getTag();
            }
            else{
                viewHolder = new ViewHolder();
                convertView = View.inflate(mContext,R.layout.password_gird_item,null);
                viewHolder.button = (Button)convertView.findViewById(R.id.password_button);
                viewHolder.del = (ImageView)convertView.findViewById(R.id.password_del);
                convertView.setTag(viewHolder);
            }
            viewHolder.button.setText(list.get(position).get("name"));
            if(position==9){
                viewHolder.button.setBackgroundColor(Color.parseColor("#c5c5c5"));
                viewHolder.button.setEnabled(false);
            }
            if(position==11){
                viewHolder.button.setVisibility(View.GONE);
                viewHolder.del.setVisibility(View.VISIBLE);
//                if(count>-1){
//                    viewHolder.del.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            textList[count--].setText("");
//                            Toast.makeText(mContext,"delete",Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
            }
            return convertView;
        }
    };

    public void restart() {
        for(int i =0;i<6;i++){
            textList[i].setText("");
        }
        count = -1;
        password = "";
    }

    public final class ViewHolder{
        public Button button;
        public ImageView del;
    }
    public void setOnFinishInput(final OnPasswordInputFinish pass) {
        textList[5].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()==1){
                    password = "";
                    for(int i=0;i<6;i++){
                        password += textList[i].getText().toString().trim();
                    }
                    pass.inputFinish();
                }
            }
        });
    }
    public String getStrPassword() {
        return password;
    }

    /* 暴露取消支付的按钮，可以灵活改变响应 */
    public ImageView getCancelImageView() {
        return backImage;
    }

    /* 暴露忘记密码的按钮，可以灵活改变响应 */
    public TextView getForgetTextView() {
        return forgetPassword ;
    }
}
