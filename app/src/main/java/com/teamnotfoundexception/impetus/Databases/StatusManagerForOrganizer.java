package com.teamnotfoundexception.impetus.Databases;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.teamnotfoundexception.impetus.Databases.FirebaseHelper.Participant;

import android.content.Context;
import android.provider.Telephony;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;



/**
 * Created by sagar on 3/21/18.
 */

public class StatusManagerForOrganizer {


    public static final String TAG = "StatusManagerForOrganizer";

    public ArrayList<Participant> mParticipants;
    public ArrayList<String> mParticipantsEmailIds;

    public EventItem mEventOrganized;

    private RequestQueue requestQueue;

    private FirebaseAuth mAuth = null;

    private FirebaseUser mUser = null;

    private static FirebaseDatabase mFirebaseDatabase = null;

    private static FirebaseHelper mFirebaseHelper = null;

    private Context mAppContext;

    private static StatusManagerForOrganizer mStatusManagerForOrganizer = null;


    private StatusManagerForOrganizer(Context context) {

        mAppContext = context;

        mAuth = null;

        mUser = null;

        if (mUser != null) Log.i("i", mUser.getEmail());

        mParticipants = new ArrayList<Participant>();
        mParticipantsEmailIds = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(mAppContext);
        mFirebaseDatabase = null;

        mFirebaseHelper = new FirebaseHelper(mAppContext);

        mEventOrganized = null;

    }

    public void setEventOrganized(EventItem mEventOrganized) {
        this.mEventOrganized = mEventOrganized;
    }

    public static StatusManagerForOrganizer get(Context c) {

        if (mStatusManagerForOrganizer == null) {
            mStatusManagerForOrganizer = new StatusManagerForOrganizer(c);
        }
        return mStatusManagerForOrganizer;
    }


    public ArrayList<Participant> getParticipants() {
        return mParticipants;
    }

    public void setmParticipants(ArrayList<Participant> mParticipants) {
        this.mParticipants = mParticipants;
    }

    public void setAllTonull() {
        mStatusManagerForOrganizer = null ;
        mAuth = null;
        mFirebaseDatabase = null;
        mFirebaseHelper = null;
    }

    public void initializeParticipantsList() {
        try {
            mFirebaseHelper.fetchParticipantsList(mEventOrganized);
        } catch(Exception e) {
            Log.i("participants", "error fetching participants" + e.getMessage());
        }
    }


    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public void setParticipantsEmailIds(ArrayList<String> participantsEmailIds) {
        this.mParticipantsEmailIds = participantsEmailIds ;
    }

    public void setParticipants(ArrayList<Participant> participants) {
        this.mParticipants = participants;
    }

    public void setStatusManagerToNull() {
        mStatusManagerForOrganizer = null;
    }

    public void setFirebaseDatabase(FirebaseDatabase firebaseDatabase) {

        this.mFirebaseDatabase = firebaseDatabase;
        if(firebaseDatabase != null) {
            mFirebaseHelper.setFirebaseDatabase(mFirebaseDatabase, 0);
        }
    }

    public void setListenerForParticipants() {
        mFirebaseHelper.addListenerForParticipants(mEventOrganized);
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


    public void  setUser(FirebaseUser user) {
        this.mUser = user;
    }



    public void setAuth(FirebaseAuth auth) {
        this.mAuth = auth;
    }



}
