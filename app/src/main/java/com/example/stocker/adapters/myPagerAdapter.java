package com.example.stocker.adapters;

import android.content.Context;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class myPagerAdapter extends FragmentStatePagerAdapter {

    //    @StringRes
//    private static final int[] TAB_TITLES = new int[]{R.string.textOne, R.string.textTwo,R.string.textThree};
//    private final Context mContext;
    private Context context;
    private List<Fragment> fragmentList;//定义各栏目版块集合
    private List<String> columnList;//定义栏目标题列表
    private Fragment mInstance;

    public myPagerAdapter(FragmentManager fm, Context context, List<Fragment> fragmentList, List<String> columnList) {
        super(fm);
        this.context = context;
        this.fragmentList = fragmentList;
        this.columnList = columnList;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        return fragmentList.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return columnList.get(position);
        //获取对应栏目标题
    }

    @Override
    public int getCount() {
        return columnList.size();
        //获取栏目总数，为pageAdapter调用
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }



    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }
}