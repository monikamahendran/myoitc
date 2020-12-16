package com.velozion.myoitc.activities;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.velozion.myoitc.BaseActivity;
import com.velozion.myoitc.R;
import com.velozion.myoitc.viewModel.MyViewModel;

public class CheckInActivity extends BaseActivity {

    MyViewModel myViewModel;
    BottomNavigationView bottomNavigationView;

   /*  final Fragment fragment1 = new MapFrag();
    final Fragment fragment2 = new HistoryFrag();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;*/

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setToolbarRequired( true );
        setHomeMenuRequired( false );
        setToolbarTitle( "Check In" );
        setContentView( R.layout.content_home );

        myViewModel = ViewModelProviders.of( this ).get( MyViewModel.class );

        bottomNavigationView = findViewById( R.id.bottomnavigationview );


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById( R.id.my_nav_host_fragment );
        NavigationUI.setupWithNavController( bottomNavigationView, navHostFragment.getNavController() );

/*
        fm.beginTransaction().add( R.id.dash_framelayout, fragment2, "2" ).hide( fragment2 ).commit();
        fm.beginTransaction().add( R.id.dash_framelayout, fragment1, "1" ).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.bnv_map:
                        fm.beginTransaction().hide( active ).show( fragment1 ).commit();
                        active = fragment1;
                        return true;
                    case R.id.bnv_history:
                        fm.beginTransaction().hide( active ).show( fragment2 ).commit();
                        active = fragment2;
                        return true;
                }
                return false;

            }
        } );*/

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
// MONIKA


