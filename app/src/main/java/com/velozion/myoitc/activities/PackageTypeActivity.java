package com.velozion.myoitc.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.velozion.myoitc.BaseActivity;
import com.velozion.myoitc.R;
import com.velozion.myoitc.fragments.PackageTypeFrag;

public class PackageTypeActivity extends BaseActivity {

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setToolbarRequired( true );
        setToolbarTitle( "Packages" );
        setHomeMenuRequired( false );
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_package_type );

        viewPager = findViewById( R.id.package_viewpager );
        tabLayout = findViewById( R.id.package_tablayout );


        viewPager.setAdapter( new PackagePageAdapter( getSupportFragmentManager() ) );
        tabLayout.setupWithViewPager( viewPager );


    }


    class PackagePageAdapter extends FragmentPagerAdapter {


        String[] title = {"Program Type", "Service Type", "Solution Type"};

        public PackagePageAdapter(FragmentManager fm) {
            super( fm );
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0) {
                return PackageTypeFrag.newInstance( "1" );
            } else if (position == 1) {
                return PackageTypeFrag.newInstance( "2" );
            } else if (position == 2) {
                return PackageTypeFrag.newInstance( "3" );
            }
            return null;
        }

        @Override
        public int getCount() {

            return title.length;

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

    }

}
