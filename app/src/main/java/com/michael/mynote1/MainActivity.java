package com.michael.mynote1;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.michael.mynote1.Adapter.MainViewPagerAdapter;
import com.michael.mynote1.Fragment.TodayFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Context mContext;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    //所在的tab标题图标
    private int[] tabs_image_pressed = {R.drawable.ic_today_yellow_400_24dp,R.drawable.ic_view_list_yellow_400_24dp};
    //为点击的tab标题图标
    private int[] tabs_image = {R.drawable.ic_today_lightblue_24dp,R.drawable.ic_view_list_lightblue_24dp};
    private List<String> tab_title;
    private List<Fragment> fragments;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private boolean fabOpen;
    private FloatingActionButton fab_more;
    private FloatingActionButton fab_edit;
    private FloatingActionButton fab_add;

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
        setContentView(R.layout.activity_main);
        Explode explode = new Explode();
        explode.setDuration(400);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);
        initView();
        initData();
        initViewPager();
        initEvent();
        fab_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFab();
            }
        });
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"111",Toast.LENGTH_SHORT).show();
            }
        });
        fab_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"222",Toast.LENGTH_SHORT).show();
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void checkFab() {
        if(fabOpen){
            closeFabmore();
        }
        else{
            openFabmore();
        }
    }

    private void closeFabmore() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(fab_more,"rotation",-135,20,0);
        objectAnimator.setDuration(500);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                fab_more.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFEE58")));
            }
        });
        objectAnimator.start();
        fabDismiss(fab_add);
        fabDismiss(fab_edit);
        fabOpen = false;
    }

    private void fabDismiss(final View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view,"scaleX",1,0);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1, 0);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1, 0);
        animatorSet.setDuration(500);
        animatorSet.setTarget(view);
        animatorSet.playTogether(scaleX,scaleY,alpha);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
            }
        });
        animatorSet.start();
    }

    private void fabShow(View view) {
        view.setVisibility(View.VISIBLE);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view,"scaleX",0,1);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0, 1);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
        animatorSet.setDuration(500);
        animatorSet.setTarget(view);
        animatorSet.playTogether(scaleX,scaleY,alpha);
        animatorSet.start();
    }

    private void openFabmore() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(fab_more,"rotation",0,-155,-135);
        objectAnimator.setDuration(500);
        fab_more.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        objectAnimator.start();
        fabShow(fab_add);
        fabShow(fab_edit);
        fabOpen = true;
    }

    private void initEvent() {
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTabSeleted(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTabNormal(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void changeTabNormal(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        ImageView img_title = (ImageView) view.findViewById(R.id.tab_img);
        TextView txt_title = (TextView) view.findViewById(R.id.tab_text);
        txt_title.setTextColor(getResources().getColor(R.color.tabColor));
        if (txt_title.getText().toString().equals(tab_title.get(0))) {
            img_title.setImageResource(R.drawable.ic_today_lightblue_24dp);
        } else {
            img_title.setImageResource(R.drawable.ic_view_list_lightblue_24dp);
        }
    }

    private void changeTabSeleted(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        ImageView img_title = (ImageView) view.findViewById(R.id.tab_img);
        TextView txt_title = (TextView) view.findViewById(R.id.tab_text);
        txt_title.setTextColor(getResources().getColor(R.color.tabColorSelected));
        if (txt_title.getText().toString().equals(tab_title.get(0))) {
            img_title.setImageResource(R.drawable.ic_today_yellow_400_24dp);
            mViewPager.setCurrentItem(0);
        } else {
            img_title.setImageResource(R.drawable.ic_view_list_yellow_400_24dp);
            mViewPager.setCurrentItem(1);
        }
    }

    //标题内容
    private void initData() {
        tab_title = new ArrayList<>();
        tab_title.add("近日事务");
        tab_title.add("全部事项");
        fragments = new ArrayList<>();
        for(int i=0;i<tab_title.size();i++){
            TodayFragment todayFragment = new TodayFragment();
            fragments.add(todayFragment.newInstance(""+i));
        }
    }

    private void initView() {
        fab_more = (FloatingActionButton) findViewById(R.id.fab_more);
        fab_edit = (FloatingActionButton)findViewById(R.id.fab_edit);
        fab_add = (FloatingActionButton)findViewById(R.id.fab_add);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        mContext = this;
        fabOpen = false;
        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    private void initViewPager() {
        mTabLayout.addTab(mTabLayout.newTab().setText(tab_title.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(tab_title.get(1)));
        MainViewPagerAdapter viewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(),fragments,tab_title);
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        setupIcon();
    }

    private void setupIcon() {
        for(int i=0;i<tab_title.size();i++) {
            mTabLayout.getTabAt(i).setCustomView(getTabView(i));
        }
    }

    private View getTabView(int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tab_view,null);
        ImageView imageView = (ImageView)view.findViewById(R.id.tab_img);
        TextView textView = (TextView)view.findViewById(R.id.tab_text);
        imageView.setImageResource(tabs_image[i]);
        textView.setText(tab_title.get(i));
        if (i == 0) {
            imageView.setImageResource(tabs_image_pressed[i]);
        }
        return view;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(this,BaseActivity.class);
//传递退出所有Activity的Tag对应的布尔值为true
                intent.putExtra(BaseActivity.EXIST, true);
//启动BaseActivity
                startActivity(intent);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
