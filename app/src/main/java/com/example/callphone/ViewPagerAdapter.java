package com.example.callphone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> listFragment=new ArrayList<>();
    String [] listTitlle=new String[]{"NHẬT KÝ","DANH BẠ"};

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        listFragment.add(new FragmentLog());
        listFragment.add(new FragmentListContact());

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
       return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTitlle[position];
    }
}
