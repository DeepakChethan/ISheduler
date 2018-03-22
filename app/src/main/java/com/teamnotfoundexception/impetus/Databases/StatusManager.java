package com.teamnotfoundexception.impetus.Databases;

/**
 * Created by sagar on 3/13/18.
 */

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.teamnotfoundexception.impetus.Databases.EventItem;
import com.teamnotfoundexception.impetus.Databases.FirebaseHelper;
import com.teamnotfoundexception.impetus.activities.DescriptionActivity;
import com.teamnotfoundexception.impetus.activities.MainActivity;


import java.util.ArrayList;


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
            mFirebaseHelper.updateStarredList(mStarredEventsIds, mUser);
            MainActivity.notifyMe();
        } catch(Exception e) {
            Log.i("error", "cannot update favorite list");
        }
    }


    public void addToRegistered(EventItem item, FirebaseHelper.Participant participant) {

        try {

            StatusManager.get(mAppContext).getRegisteredIdList().add(item.getId());

            mFirebaseHelper.updateRegisteredList(mRegisteredEventsIds, mUser, item, participant);

            DescriptionActivity.notifyMe();

        } catch(Exception e) {

            Log.i("error", "cannot update favorite list");

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
        this.mStarredEventsIds = registeredIdList;
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
