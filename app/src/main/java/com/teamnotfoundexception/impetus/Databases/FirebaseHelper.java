package com.teamnotfoundexception.impetus.Databases;

/**
 * Created by sagar on 3/21/18.
 */

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.teamnotfoundexception.impetus.activities.MainActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class FirebaseHelper {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private Context mAppContext;

    public FirebaseHelper(Context c) {
        mFirebaseDatabase = null;
        mAppContext = c;
    }

    public void setFirebaseDatabase(FirebaseDatabase firebaseDatabase) {
        mFirebaseDatabase = firebaseDatabase;
        if (firebaseDatabase != null)
            mDatabaseReference = mFirebaseDatabase.getReference("users");
    }


    public void updateStarredList(ArrayList<Integer> starredListItem, FirebaseUser user) {

        Log.i("favorite", "update favorite caleld");


        String emailId = getEmailStripped(user.getEmail());

        Map<String, ArrayList<Integer>> starredListMap = new HashMap<>();
        starredListMap.put("event_ids", starredListItem);

        try {

            mDatabaseReference.child(user.getUid()).child(emailId).child("starred").setValue(starredListItem);

        } catch (Exception e) {

            Log.i("favoriteError", "error in placing the order");

        }

    }




    public void updateRegisteredList(ArrayList<Integer> registeredListItem, FirebaseUser user) {

        Log.i("favorite", "update favorite caleld");


        Map<String, ArrayList<Integer>> registeredListMap = new HashMap<>();
        registeredListMap.put("event_ids", registeredListItem);

        try {

            mDatabaseReference.child(user.getUid()).child("registered").setValue(registeredListMap);

        } catch (Exception e) {

            Log.i("favoriteError", "error in placing the order");

        }

    }




    public void fetchRegisteredList(FirebaseUser user) {


        String emailId = getEmailStripped(user.getEmail());
        DatabaseReference databaseReference = mDatabaseReference.child(user.getUid()).child(emailId).child("registered").child("event_ids");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Integer>> t = new GenericTypeIndicator<List<Integer>>() {
                };
                ArrayList<Integer> registeredListIds = (ArrayList<Integer>) dataSnapshot.getValue(t);
                if (registeredListIds != null) {
                    StatusManager.get(mAppContext).setRegisteredIdList(new ArrayList<Integer>(registeredListIds));
              //      System.out.println("size of favorite list " + com.teamnamenotfoundexception.lsheduler.Database.StatusManager.get(mAppContext).getFavoriteIdList().size());
                    MainActivity.notifyMe();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());

            }
        });


    }


    public void fetchStarredList(FirebaseUser user) {


        String emailId = getEmailStripped(user.getEmail());
        DatabaseReference databaseReference = mDatabaseReference.child(user.getUid()).child(emailId).child("starred").child("event_ids");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Integer>> t = new GenericTypeIndicator<List<Integer>>() {
                };
                ArrayList<Integer> starredListIds = (ArrayList<Integer>) dataSnapshot.getValue(t);
                if (starredListIds != null) {
                 StatusManager.get(mAppContext).setStarredIdList(new ArrayList<Integer>(starredListIds));
                    //System.out.println("size of favorite list " + com.teamnamenotfoundexception.lsheduler.Database.StatusManager.get(mAppContext).getFavoriteIdList().size());
                    MainActivity.notifyMe();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());

            }
        });


    }





    public String getEmailStripped(String emailId) {
        String emailIdSplit[] = emailId.split("@");
        String _emailId = emailIdSplit[0];
        return _emailId;
    }



    class FavoriteObject {
        public int itemId;

        public FavoriteObject() {

        }

        public FavoriteObject(int dishItemId) {
            itemId = dishItemId;
        }
    }



}

