package com.example.orangesale_02.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.List;

public class BottomBar_fragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mlist;

    //构造器
    public BottomBar_fragmentAdapter(FragmentManager fragment,List<Fragment> list) {
        super(fragment);
        this.mlist = list;
    }

    //显示第几个页面
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mlist.get(position);
    }

    //一共有几个页面
    @Override
    public int getCount() {
        return mlist.size();
    }


}
