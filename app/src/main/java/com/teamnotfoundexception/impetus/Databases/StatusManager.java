package com.teamnotfoundexception.impetus.Databases;

/**
 * Created by sagar on 3/13/18.
 */

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.teamnotfoundexception.impetus.Databases.EventItem;
import com.teamnotfoundexception.impetus.Databases.FirebaseHelper;
import com.teamnotfoundexception.impetus.activities.DescriptionActivity;
import com.teamnotfoundexception.impetus.activities.MainActivity;

import com.teamnotfoundexception.impetus.adapters.EventsAdapter;
import com.teamnotfoundexception.impetus.adapters.MyEventsAdapter;
import com.teamnotfoundexception.impetus.adapters.PlayerListAdapter;
import com.teamnotfoundexception.impetus.adapters.StarredAdapter;

import com.teamnotfoundexception.impetus.fragments.EventsFragment;
import com.teamnotfoundexception.impetus.services.Notifier;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public  class StatusManager {

    public static final String TAG = "StatusManager";

    private FirebaseAuth mAuth = null;

    private FirebaseUser mUser = null;
    private static FirebaseDatabase mFirebaseDatabase = null;
    private static FirebaseHelper mFirebaseHelper = null;


    // for storing in firebase
    private ArrayList<Integer> mRegisteredEventsIds;
    private ArrayList<Integer> mStarredEventsIds;


    //for use inside the app

    private ArrayList<EventItem> mRegisteredEventsList;
    private ArrayList<EventItem> mStarredEventsList;
    private EventItem nextEvent;


    private Context mAppContext;

    private static StatusManager mStatusManager = null;

    private EventsAdapter mEventsAdapter = null;
    private MyEventsAdapter mMyEventsAdapter = null;
    private StarredAdapter mStarredAdapter = null;

    private StatusManager(Context context) {

        mAppContext = context;

        mAuth = null;

        mUser = null;

        if (mUser != null) Log.i("i", mUser.getEmail());

        mRegisteredEventsIds = new ArrayList<>();

        mStarredEventsIds = new ArrayList<>();

        mRegisteredEventsList = new ArrayList<>();

        mStarredEventsList = new ArrayList<>();


        mFirebaseDatabase = null;

        mFirebaseHelper = new FirebaseHelper(mAppContext);

    }



    public static StatusManager get(Context c) {

        if (mStatusManager == null) {
            mStatusManager = new StatusManager(c);
            Log.i("i", "Cart Manager initialized");
        }
        return mStatusManager;
    }


    public void setEventsAdapter(EventsAdapter eventsAdapter){
        mEventsAdapter = eventsAdapter;
    }
    public void setStarredAdapter(StarredAdapter eventsAdapter){
        mStarredAdapter = eventsAdapter;
    }
    public void setMyEventsAdapter(MyEventsAdapter eventsAdapter){
        mMyEventsAdapter = eventsAdapter;
    }

    public EventsAdapter getmEventsAdapter() {
        return mEventsAdapter;
    }

    public MyEventsAdapter getmMyEventsAdapter() {
        return mMyEventsAdapter;
    }

    public StarredAdapter getmStarredAdapter() {
        return mStarredAdapter;
    }

    public void initializeNotifications(){
        ArrayList<EventItem> mRegistered = getRegisteredEventsList();
        for(int i = 0; i < mRegistered.size(); ++i){
            EventItem item = mRegistered.get(i);
            setupNotification(item);
        }
    }


    public void setupNotification(EventItem item){

        long time = Long.parseLong(item.getStartTime());
        Intent intent = new Intent(mAppContext, Notifier.class);
        intent.putExtra("dope",item);
        PendingIntent pi = PendingIntent.getBroadcast(mAppContext,0,intent,0);
        AlarmManager alarmManager = (AlarmManager) mAppContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,time,pi);
        Log.i("dope","Setup up alarm at "+item.getStartTime()+" "+item.getName());

    }

    public void initializeStarredList() {
        try {
            mFirebaseHelper.fetchStarredList(mUser);
        } catch(Exception e) {
            Log.i("hkkgja", "error fetching favs");
        }
    }

    public void initializeRegisteredList() {
        try {
            Log.i("ini", "initializng registred list");;
            mFirebaseHelper.fetchRegisteredList(mUser);
        } catch(Exception e) {
            Log.i("e", e.getMessage());
        }
    }

    public void addToStarred(EventItem item) {

        try {
            StatusManager.get(mAppContext).getStarredIdList().add(item.getId());
            StatusManager.get(mAppContext).getStarredEventsList().add(item);
            item.setStarred(1);
            mFirebaseHelper.updateStarredList(mStarredEventsIds, mUser);
            MainActivity.notifyMe();
        } catch(Exception e) {
            Log.i("error", "cannot update favorite list");
        }
    }


    public void addToRegistered(EventItem item, FirebaseHelper.Participant participant) {

        try {
            if(item.isRegistered == 0) {
                StatusManager.get(mAppContext).getRegisteredIdList().add(item.getId());

                getRegisteredEventsList().add(item);

                item.setRegistered(1);



                FirebaseMessaging.getInstance().subscribeToTopic(item.getName().toUpperCase());
                Log.i("i", "subscribed to " + item.getName().toUpperCase());
                mFirebaseHelper.updateRegisteredList(mRegisteredEventsIds, mUser, item, participant);

                EventsFragment.notifyMe();
            } else {
                Log.i("update", "already registered");
            }

        } catch(Exception e) {

            Log.i("error", "cannot update favorite list" + e.getMessage());

        }
    }

    public void removeFromStarred(EventItem item) {

        try {
            StatusManager.get(mAppContext).getStarredIdList().remove(new Integer(item.getId()));
            mFirebaseHelper.updateStarredList(mStarredEventsIds, mUser);
            System.out.println("removing fav" + item.getName());
          //  MainActivity.notifyMe();
        } catch(Exception e) {
            Log.i("error", "cannto update after removing");

        }

    }

    public ArrayList<Integer> getStarredIdList() {
        return mStarredEventsIds;
    }

    public ArrayList<Integer> getRegisteredIdList() {
        return mRegisteredEventsIds;
    }

    public ArrayList<EventItem> getRegisteredEventsList() {
        return mRegisteredEventsList;
    }

    public ArrayList<EventItem> getStarredEventsList() {
        return mStarredEventsList;
    }


    public void setStarredIdList(ArrayList<Integer> starredIdList) {
        this.mStarredEventsIds = starredIdList;
    }

    public void setRegisteredIdList(ArrayList<Integer> registeredIdList) {
        this.mRegisteredEventsIds = registeredIdList;
    }

    /*public void setRegisteredEventsList(ArrayList<Integer> registeredEventsList) {
        this.mStarredEventsIds = registeredEventsList;
    }
    public void setStarredEventsList(ArrayList<Integer> starredEventsList) {
        this.mStarredEventsIds = starredEventsList;
    }*/

    public void addEventToStarred(EventItem item) {
        mStarredEventsList.add(item);
    }

    public void removeDishFromStarred(EventItem item) {
        mStarredEventsList.remove(item);
    }




    public void  setUser(FirebaseUser user) {
        this.mUser = user;
    }

    public void setAllTonull() {
        mRegisteredEventsIds = null;
        mStatusManager = null ;
        mFirebaseDatabase = null;
        mFirebaseHelper = null;
    }


    public EventItem checkForClash(EventItem item){

        ArrayList<EventItem> mTempRegisteredEvents = new ArrayList<>();
        mTempRegisteredEvents.addAll(mRegisteredEventsList);
        Collections.sort(mTempRegisteredEvents);
        Long eventStartTime = Long.parseLong(item.getStartTime());
        for (int i = 0; i < mTempRegisteredEvents.size(); ++i){
            EventItem eventItem = mTempRegisteredEvents.get(i);
            Long itemStartTime = Long.parseLong(eventItem.getStartTime());
            Long itemEndTime = Long.parseLong(eventItem.getEndTime());
            Log.i("dope", "checkForClash: "+itemStartTime+" "+eventStartTime+" "+itemEndTime);
            if (eventStartTime >= itemStartTime && eventStartTime <= itemEndTime){
                Log.i("dope", "checkForClash: its true");
                return eventItem;
            }

        }
        return null;
    }

    public void setAuth(FirebaseAuth auth) {
        this.mAuth = auth;
    }

    public void setFirebaseDatabase(FirebaseDatabase firebaseDatabase) {

        this.mFirebaseDatabase = firebaseDatabase;
        mFirebaseHelper.setFirebaseDatabase(mFirebaseDatabase, 0);

    }



    public FirebaseAuth getAuth() {
        return mAuth;
    }

    public FirebaseUser getUser() {
        return mUser;
    }

    public FirebaseDatabase getFirebaseDatabase() {
        return mFirebaseDatabase;
    }



}
