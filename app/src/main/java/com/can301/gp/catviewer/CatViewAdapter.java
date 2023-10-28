package com.can301.gp.catviewer;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

//制作滑动页面效果
public final class CatViewAdapter extends FragmentStatePagerAdapter {

    private int count;

    public void setCount(int count) {
        this.count = count;
    }

    public CatViewAdapter(List<Fragment> fragments, FragmentManager fm) {
        super(fm);
    }

    public CatViewAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int catIndex) {
        return CatViewFragment.newInstance(catIndex);
    }

    @Override
    public int getCount() {
        return count;
    }
}