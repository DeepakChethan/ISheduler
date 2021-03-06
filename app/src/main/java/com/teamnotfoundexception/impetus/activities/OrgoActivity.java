package com.teamnotfoundexception.impetus.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;


import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.teamnotfoundexception.impetus.Databases.FirebaseHelper;
import com.teamnotfoundexception.impetus.Databases.StatusManagerForOrganizer;
import com.teamnotfoundexception.impetus.R;
import com.teamnotfoundexception.impetus.fragments.OrgoHomeFragment;
import com.teamnotfoundexception.impetus.fragments.OrgoPlayerFragment;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrgoActivity extends AppCompatActivity implements OrgoPlayerFragment.OnListFragmentInteractionListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private FloatingActionButton button;
    private Handler handler;
    private Runnable myRunnable;

    @Override
    public void onBackPressed() {
        new MaterialStyledDialog.Builder(OrgoActivity.this)
                .setIcon(R.drawable.ic_launcher)
                .setTitle("Want to quit?")
                .setDescription("Are you sure you want to quit?")
                .setPositiveText("Yes")
                .setCancelable(true)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                })
                .setNegativeText("No")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orgo);


        StatusManagerForOrganizer.get(getApplicationContext()).initializeParticipantsList();
        StatusManagerForOrganizer.get(getApplicationContext()).setListenerForParticipants();




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        button = (FloatingActionButton)findViewById(R.id.fab);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.customview,null);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = view.findViewById(R.id.orgoMessage);
                new MaterialStyledDialog.Builder(OrgoActivity.this)
                        .setTitle("Notify Participants")
                        .setCancelable(true)
                        .setCustomView(view)
                        .setDescription("What do you want them to know ?")
                        .setPositiveText("Send")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                String note = editText.getText().toString();
                                Toast.makeText(getApplicationContext(),note+"",Toast.LENGTH_SHORT).show();
                                sendNotification(note);
                            }
                        })
                        .setNegativeText("Cancel")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();


            }
        });

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }

    public void sendNotification(String notes) {

        String url= "https://fcm.googleapis.com/fcm/send";
        JSONObject postparams=new JSONObject();
        try {
            postparams.put("to", "/topics/" + StatusManagerForOrganizer.get(getApplicationContext()).mEventOrganized.getName().toUpperCase());
            JSONObject extraInformation = new JSONObject();
            extraInformation.put("extra_information", notes);

            postparams.put("data", extraInformation);
            JSONObject titleAndText = new JSONObject();
            titleAndText.put("title", notes);
            titleAndText.put("text", notes);
            postparams.put("notification", titleAndText);

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    url, postparams,
                    new Response.Listener() {

                        @Override
                        public void onResponse(Object response) {
                            System.out.println("got a reponse" + response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            //Failure Callback
                            System.out.println("got a reponse" + error.toString());

                        }
                    }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "key=AAAAZq9yKM0:APA91bFeIzylkRFLLH40hpiElvdqVXXhG3YANyAdS7-0d4XrH5E4hdt5d5XiTRvOgTmDEdsfjzXEzTBXfaEkPLPCUTwmX3hH7mQyt22L8STnOiUzhX4uCck-bP5V7vdip453LgXVsUbp");
                    return headers;

                }
            };
            jsonObjReq.setTag("postRequest");
            StatusManagerForOrganizer.get(getApplicationContext()).getRequestQueue().add(jsonObjReq);
        } catch(Exception e) {

        }



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
            startActivity(new Intent(OrgoActivity.this,SearchActivity.class));
            return true;
        }
        if (id == R.id.logout){
            //TODO logout of the app
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(FirebaseHelper.Participant item) {
        
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        public SectionsPagerAdapter(FragmentManager fm) {

            super(fm);
            fragments = new ArrayList<>();
            fragments.add(new OrgoHomeFragment());
            fragments.add(new OrgoPlayerFragment());

        }

        @Override
        public Fragment getItem(int position) {

            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}


/*
//{   https://fcm.googleapis.com/fcm/send
        "to": "/topics/STEALJOBS",
        "data": {
        "extra_information": "This is message"
        },
        "notification": {
        "title": "your event starts soon enigma boys",
        "text": "This is the notification"
        }
        }*/

//key=AAAAZq9yKM0:APA91bFeIzylkRFLLH40hpiElvdqVXXhG3YANyAdS7-0d4XrH5E4hdt5d5XiTRvOgTmDEdsfjzXEzTBXfaEkPLPCUTwmX3hH7mQyt22L8STnOiUzhX4uCck-bP5V7vdip453LgXVsUbp
