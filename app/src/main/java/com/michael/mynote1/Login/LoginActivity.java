package com.michael.mynote1.Login;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.michael.mynote1.MainActivity;
import com.michael.mynote1.R;
import com.michael.mynote1.Utils.OnPasswordInputFinish;
import com.michael.mynote1.myView.passwordView;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Michael on 2016/9/15.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUsername;
    private EditText etPassword;
    private Button btGo;
    private CardView cv;
    private FloatingActionButton fab;
    private TextView forget;
    private TextView jump;
    private CheckBox remember;
    private LinearLayout login_title_linear;
    private TextView login_title;
    private View login_line;
    private Context mContext;
    private View parentView;
    private SweetAlertDialog loadingDialog;
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.login_layout);
        Log.d("11111","login");
        mContext = this;
        parentView = View.inflate(mContext,R.layout.login_layout,null);
        etUsername = (EditText)findViewById(R.id.et_username);
        etPassword = (EditText)findViewById(R.id.et_password);
        btGo = (Button)findViewById(R.id.bt_go);
        cv = (CardView)findViewById(R.id.cv);
        login_title_linear = (LinearLayout)findViewById(R.id.login_title_linear);
        login_title = (TextView)findViewById(R.id.login_title);
        login_line = (View)findViewById(R.id.login_line);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        btGo.setOnClickListener(this);
        fab.setOnClickListener(this);
        forget = (TextView)findViewById(R.id.forget_password);
        jump = (TextView)findViewById(R.id.jump_to_activity);
        forget.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
        forget.getPaint().setAntiAlias(true);
        jump.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
        jump.getPaint().setAntiAlias(true);
        jump.setOnClickListener(this);
        titleanim(cv);
        titleanim(login_line);
        titleanim(login_title);
        titleanim(login_title_linear);
        titleanim(fab);
    }

    private void titleanim(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view,"scaleX",0,1);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0, 1);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
        animatorSet.setDuration(1000);
        animatorSet.setInterpolator(new LinearOutSlowInInterpolator());
        animatorSet.setTarget(view);
        animatorSet.playTogether(scaleX,scaleY,alpha);
        animatorSet.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
                    startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
                } else {
                    startActivity(new Intent(this, RegisterActivity.class));
                }
                break;
            case R.id.bt_go:
                Explode explode = new Explode();
                explode.setDuration(400);

                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);
                ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
                Intent i2 = new Intent(this,MainActivity.class);
                startActivity(i2, oc2.toBundle());
                break;
            case R.id.jump_to_activity:
                final passwordView popwindow = new passwordView(mContext);
                popwindow.setParent(parentView);
                backgroundAlpha(0.5f);
                popwindow.showPopupWindow();
                popwindow.getForgetTextView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext,"forget",Toast.LENGTH_SHORT).show();
                    }
                });
                popwindow.getCancelImageView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popwindow.dismiss();
                        backgroundAlpha(1.0f);
                    }
                });
                popwindow.setOnFinishInput(new OnPasswordInputFinish() {
                    @Override
                    public void inputFinish() {
                        pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
                        pDialog.getProgressHelper().setBarColor(Color.parseColor("#5A79A6"));
                        pDialog.setTitleText("Loading");
                        pDialog.setCancelable(false);
                        pDialog.show();
                        if(popwindow.getStrPassword().equals("222222")) {
                            pDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            pDialog.showCancelButton(false);
                            pDialog.dismiss();
                            popwindow.dismiss();
                            Explode explode = new Explode();
                            explode.setDuration(400);
                            getWindow().setExitTransition(explode);
                            getWindow().setEnterTransition(explode);
                            ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);
                            Intent i2 = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i2, oc2.toBundle());
                        }
                        else{
                            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                            pDialog.setTitleText("密码有误")
                                    .setCancelText("退出")
                                    .setConfirmText("重新输入")
                                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            pDialog.dismissWithAnimation();
                                            popwindow.dismiss();
                                        }
                                    })
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            pDialog.dismiss();
                                            popwindow.restart();
                                        }
                                    });
                        }
                    }
                });
                break;
        }
    }
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

}