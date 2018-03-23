package com.teamnotfoundexception.impetus.activities;

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
import android.widget.Toast;

import com.teamnotfoundexception.impetus.Databases.EventItem;
import com.teamnotfoundexception.impetus.R;
import com.teamnotfoundexception.impetus.fragments.DescriptionFragment;
import com.teamnotfoundexception.impetus.fragments.RegisterFragment;
import com.teamnotfoundexception.impetus.fragments.TimerFragment;

import java.util.ArrayList;

public class DescriptionActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EventItem eventItem = (EventItem) getIntent().getSerializableExtra("msg");

        if (eventItem == null)
        {
            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
            return;
        }

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),eventItem);

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            Intent intent = new Intent(DescriptionActivity.this,SearchActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.logout){
            //TODO logout of the app
        }

        return super.onOptionsItemSelected(item);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> fragments;

        public SectionsPagerAdapter(FragmentManager fm,EventItem eventItem) {
            super(fm);
            Bundle bundle = new Bundle();
            TimerFragment timerFragment = new TimerFragment();
            bundle.putSerializable("dope",eventItem);
            timerFragment.setArguments(bundle);

            bundle = new Bundle();
            DescriptionFragment descriptionFragment= new DescriptionFragment();
            bundle.putSerializable("dope",eventItem);
            descriptionFragment.setArguments(bundle);

            bundle = new Bundle();
            RegisterFragment registerFragment = new RegisterFragment();
            bundle.putSerializable("dope",eventItem);
            registerFragment.setArguments(bundle);

            fragments = new ArrayList<>();
            fragments.add(timerFragment);
            fragments.add(descriptionFragment);
            fragments.add(registerFragment);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public static void notifyMe() {
        Log.i("registered", "registerd from descr activity");
    }
}
