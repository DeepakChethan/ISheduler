package com.teamnotfoundexception.impetus.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import java.util.HashMap;
import java.util.Map;

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

        mSectionsPagerAdapterUser = new SectionsPagerAdapterUser(getSupportFragmentManager(),HomeActivity.this);
        Log.i(TAG, "onCreate: "+EventsManager.get(getApplicationContext()).getEventItemsList().size());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapterUser);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Fragment fragment = ((SectionsPagerAdapterUser)mViewPager.getAdapter()).getFragment(position);
                if (fragment != null){
                    fragment.onResume();
                }
            }

            @Override
            public void onPageSelected(int position) {

                Fragment fragment = ((SectionsPagerAdapterUser)mViewPager.getAdapter()).getFragment(position);
                if (fragment != null){
                    fragment.onResume();
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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
        if (item.getItemId() == R.id.logout){
            //TODO logout of the app
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(EventItem item) {

    }



    public class SectionsPagerAdapterUser extends FragmentPagerAdapter {

        private Map<Integer,String> mFragmentTags;
        private FragmentManager mFragmentManager;
        private Context mContext;

        ArrayList<Fragment> fragments;
        public SectionsPagerAdapterUser(FragmentManager fm,Context context) {
            super(fm);
            mFragmentManager = fm;
            mContext = context;
            mFragmentTags = new HashMap<>();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull View container, int position) {
            Object obj = super.instantiateItem(container,position);
            if (obj instanceof Fragment){
                Fragment fragment = (Fragment) obj;
                String tag = fragment.getTag();
                mFragmentTags.put(position,tag);
            }
            return obj;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0: return (new MyEventsFragment());
                case 1: return (new StarredFragment());
                case 2: return (new EventsFragment());
            }
            return null;
        }

        public Fragment getFragment(int pos){
            String tag = mFragmentTags.get(pos);
            if (tag == null) return null;
            return mFragmentManager.findFragmentByTag(tag);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
