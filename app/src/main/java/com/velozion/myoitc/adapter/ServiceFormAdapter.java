package com.velozion.myoitc.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class ServiceFormAdapter extends FragmentStatePagerAdapter {

    List<Fragment> fragments;

    public ServiceFormAdapter(FragmentManager fm, List<Fragment> fragments) {
        super( fm );
        this.fragments = fragments;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return this.fragments.get( position );
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}

