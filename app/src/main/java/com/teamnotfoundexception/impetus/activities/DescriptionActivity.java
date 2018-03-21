package com.teamnotfoundexception.impetus.activities;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.teamnotfoundexception.impetus.Databases.EventItem;
import com.teamnotfoundexception.impetus.R;

public class DescriptionActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        textView = (TextView) findViewById(R.id.eventContentDescripton);
        setSupportActionBar(toolbar);
        EventItem item= (EventItem) getIntent().getSerializableExtra("msg");
        if (item == null){
            Toast.makeText(getApplicationContext(),"Somethings wrong with your phone!",Toast.LENGTH_SHORT).show();
            return;
        }
        toolbar.setTitle(item.getName());
        appBarLayout.setBackgroundResource(R.drawable.ic_launcher_background);

    }
}
