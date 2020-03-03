package com.example.myapplication;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SectionsPageAdapter extends FragmentPagerAdapter {

    private final ArrayList<String> Titles=new ArrayList<>();
    private final ArrayList<Fragment> fragments=new ArrayList<>();
    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }
    public void addFragment(Fragment f, String t){

        Titles.add(t);
        fragments.add(f);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return Titles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
