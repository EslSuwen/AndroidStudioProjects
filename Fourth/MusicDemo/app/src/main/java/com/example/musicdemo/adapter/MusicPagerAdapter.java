package com.example.musicdemo.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by shaoyangyang on 2017/12/18.
 */

public class MusicPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList ;
    //添加一个List<Fragment> fragmentList
    public MusicPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }
    //返回fragmentList.get(position)
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
    //返回fragmentList.size()
    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
