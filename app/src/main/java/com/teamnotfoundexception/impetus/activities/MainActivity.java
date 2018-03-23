package com.teamnotfoundexception.impetus.activities;

import android.app.Notification;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.teamnotfoundexception.impetus.Databases.EventItem;
import com.teamnotfoundexception.impetus.Databases.EventsManager;
import com.teamnotfoundexception.impetus.Databases.StatusManager;
import com.teamnotfoundexception.impetus.LogSign.LoginActivity;
import com.teamnotfoundexception.impetus.R;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        StatusManager.get(getApplicationContext()).setAuth(FirebaseAuth.getInstance());
        StatusManager.get(getApplicationContext()).setUser(FirebaseAuth.getInstance().getCurrentUser());
        StatusManager.get(getApplicationContext()).setFirebaseDatabase(FirebaseDatabase.getInstance());
        new Date().getTime()
        EventItem item = new EventItem(101,"DopeGame","Happy",20,"Happy meals","","1521759300","1521759300","Hebba",2,0,0);
        StatusManager.get(getApplicationContext()).setupNotification(item);


        if (FirebaseAuth.getInstance().getCurrentUser()==null){
            Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent1);
            finish();
        }else{
            Intent intent1 = new Intent(this, HomeActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent1);
            finish();
        }

        EventsManager.get(getApplicationContext()).insertAllEventItems();
        EventsManager.get(getApplicationContext()).initializeEventItemsList();
        StatusManager.get(getApplicationContext()).initializeRegisteredList();
        StatusManager.get(getApplicationContext()).initializeStarredList();



    }


    public static void notifyMe() {



    }
}
