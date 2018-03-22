package com.teamnotfoundexception.impetus.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.teamnotfoundexception.impetus.Databases.EventItem;
import com.teamnotfoundexception.impetus.Databases.EventsManager;
import com.teamnotfoundexception.impetus.R;
import com.teamnotfoundexception.impetus.fragments.EventsFragment;
import com.teamnotfoundexception.impetus.fragments.MyEventsFragment;
import com.teamnotfoundexception.impetus.fragments.StarredFragment;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements EventsFragment.OnListFragmentInteractionListener{


    private static final String TAG = "dc";

    private SectionsPagerAdapterUser mSectionsPagerAdapterUser;
    private ViewPager mViewPager;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapterUser = new SectionsPagerAdapterUser(getSupportFragmentManager());
        Log.i(TAG, "onCreate: "+EventsManager.get(getApplicationContext()).getEventItemsList().size());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapterUser);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (R.id.search == item.getItemId()){
            startActivity(new Intent(HomeActivity.this,SearchActivity.class));
        }
        if (R.id.org == item.getItemId()){
            startActivity(new Intent(HomeActivity.this,OrgoActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(EventItem item) {

    }



    public class SectionsPagerAdapterUser extends FragmentPagerAdapter {

        ArrayList<Fragment> fragments;
        public SectionsPagerAdapterUser(FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<>();
            fragments.add(new MyEventsFragment());
            fragments.add(new MyEventsFragment());
            fragments.add(new MyEventsFragment());
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
