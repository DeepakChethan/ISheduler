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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.teamnotfoundexception.impetus.Databases.EventItem;
import com.teamnotfoundexception.impetus.Databases.EventsManager;
import com.teamnotfoundexception.impetus.Databases.StatusManager;
import com.teamnotfoundexception.impetus.Databases.StatusManagerForOrganizer;
import com.teamnotfoundexception.impetus.LogSign.LoginActivity;
import com.teamnotfoundexception.impetus.R;
import com.teamnotfoundexception.impetus.fragments.EventsFragment;
import com.teamnotfoundexception.impetus.fragments.MyEventsFragment;
import com.teamnotfoundexception.impetus.fragments.StarredFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements EventsFragment.OnListFragmentInteractionListener, View.OnTouchListener{


    private static final String TAG = "dc";

    private SectionsPagerAdapterUser mSectionsPagerAdapterUser;
    private ViewPager mViewPager;
    private Activity activity;

    @Override
    protected void onResume() {
        super.onResume();
        mSectionsPagerAdapterUser.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mSectionsPagerAdapterUser.notifyDataSetChanged();
    }

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
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //  mSectionsPagerAdapterUser.notifyDataSetChanged();
            }

            @Override
            public void onPageSelected(int position) {
                mSectionsPagerAdapterUser.notifyDataSetChanged();

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mSectionsPagerAdapterUser.notifyDataSetChanged();
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

            FirebaseAuth.getInstance().signOut();
            StatusManager.get(getApplicationContext()).setAuth(null);
            StatusManager.get(getApplicationContext()).setUser(null);
            StatusManager.get(getApplicationContext()).setAllTonull();

            StatusManagerForOrganizer.get(getApplicationContext()).setAllTonull();

            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
            return super.onOptionsItemSelected(item);
        
    }

    @Override
    public void onListFragmentInteraction(EventItem item) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Toast.makeText(getApplicationContext(),"Dont touch me",Toast.LENGTH_SHORT).show();
        mSectionsPagerAdapterUser.notifyDataSetChanged();

        return true;
    }


    public class SectionsPagerAdapterUser extends FragmentPagerAdapter {


        private Context mContext;
        private ArrayList<Fragment> fragments;

        public SectionsPagerAdapterUser(FragmentManager fm,Context context) {
            super(fm);
            mContext = context;
            fragments= new ArrayList<>();
            fragments.add(new EventsFragment());
            fragments.add(new MyEventsFragment());
            fragments.add(new StarredFragment());

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
