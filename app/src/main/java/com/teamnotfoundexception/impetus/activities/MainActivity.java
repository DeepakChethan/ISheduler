package com.teamnotfoundexception.impetus.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.teamnotfoundexception.impetus.Databases.EventsManager;
import com.teamnotfoundexception.impetus.Databases.StatusManager;
import com.teamnotfoundexception.impetus.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        StatusManager.get(getApplicationContext()).setAuth(FirebaseAuth.getInstance());
        StatusManager.get(getApplicationContext()).setUser(FirebaseAuth.getInstance().getCurrentUser());
        StatusManager.get(getApplicationContext()).setFirebaseDatabase(FirebaseDatabase.getInstance());

        EventsManager.get(getApplicationContext()).insertAllEventItems();
        EventsManager.get(getApplicationContext()).initializeEventItemsList();

        StatusManager.get(getApplicationContext()).getRegisteredIdList();

        StatusManager.get(getApplicationContext()).getStarredIdList();


        startActivity(new Intent(this, HomeActivity.class));
    }


    public static void notifyMe() {

    }
}
