package com.example.stocker.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.stocker.R;
import com.example.stocker.adapters.myPagerAdapter;

import com.example.stocker.ui.fragLibrary.mainFragment;
import com.example.stocker.ui.fragLibrary.chartsFragment;
import com.example.stocker.ui.fragLibrary.newsListFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    private List<Fragment> fragments = new ArrayList<>();//初始化模块集合
    private List<String> titles = new ArrayList<>();//初始化标题集合
    private static final String TAG = "MainActivity";
    private Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initView() {
        TabLayout mTableLayout = findViewById(R.id.tabs);
        ViewPager mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(new myPagerAdapter(getSupportFragmentManager(), MainActivity.this, fragments, titles));
        //设置分页适配器绑定模块集合
        mTableLayout.setupWithViewPager(mViewPager);//此方法就是让tabLayout和ViewPager联动

    }


    private void initData() {
        //设置的是什么参数值？待研究,fragment能否再添加子模块
        fragments.add(mainFragment.newInstance("首页"));
//        fragments.add(queryFragment.newInstance());
        fragments.add(chartsFragment.newInstance("行情"));//展示统计图表
        fragments.add(newsListFragment.newInstance("资讯"));
        //设置的是标题，后面可以通过调用类接口批量设置标题并添加
        titles.add("首页");//首页用来添加个人网站接口,主要提供一些接口
//        titles.add("检索");
        titles.add("行情");
        titles.add("资讯");
    }

    public void toInvader(View view) {
        Toast.makeText(MainActivity.this, "jump to website", Toast.LENGTH_SHORT).show();
        //实现打开个人网站WebView;
        String webUrl = "https://www.invacode.top";
        Intent webIntent = new Intent(this, WebsiteAct.class);
        webIntent.putExtra("webUrl", webUrl);
        startActivity(webIntent);
    }

    public void searchStock(View v){
        Toast.makeText(MainActivity.this, "jump to search", Toast.LENGTH_SHORT).show();
        //实现打开个人网站WebView;
        Intent queIntent = new Intent(this, searchMain.class);
        startActivity(queIntent);
    }

}