package com.teamnotfoundexception.impetus.Databases;

/**
 * Created by sagar on 3/21/18.
 */

import android.content.Context;
import android.provider.Telephony;
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
    private DatabaseReference mDatabaseReference1, mDatabaseReference2, mDatabaseReference3;
    private Context mAppContext;

    private Participant p;

    public FirebaseHelper(Context c) {
        mFirebaseDatabase = null;
        mAppContext = c;
    }

    public void setFirebaseDatabase(FirebaseDatabase firebaseDatabase, int type) {
        mFirebaseDatabase = firebaseDatabase;
        if (firebaseDatabase != null)
                mDatabaseReference1 = mFirebaseDatabase.getReference("users_private");
                mDatabaseReference2 = mFirebaseDatabase.getReference("users_public");
                mDatabaseReference3
                        = mFirebaseDatabase.getReference("events");

    }


    public void updateStarredList(ArrayList<Integer> starredListItem, FirebaseUser user) {

        String emailId = getEmailStripped(user.getEmail());

        Map<String, ArrayList<Integer>> starredListMap = new HashMap<>();
        starredListMap.put("event_ids", starredListItem);

        try {

            mDatabaseReference1.child(user.getUid()).child("starred").setValue(starredListItem);

        } catch (Exception e) {

            Log.i("favoriteError", "error in placing the order");

        }

    }




    public void updateRegisteredList(ArrayList<Integer> registeredListItem, FirebaseUser user, EventItem currentRegisteredItem) {

        Log.i("favorite", "update favorite caleld");


        Map<String, ArrayList<Integer>> registeredListMap = new HashMap<>();

        registeredListMap.put("event_ids", registeredListItem);

        String emailId = user.getEmail().split("@")[0];

        try {

            mDatabaseReference1.child(user.getUid()).child("registered").setValue(registeredListMap);
            ArrayList<String> m = new ArrayList<>();
            m.add("sagar");
            m.add("sachin");
            m.add("vinay");
            mDatabaseReference3.child(currentRegisteredItem.getName().toUpperCase()).child(emailId).setValue(new Participant(user.getEmail(), "bmsce", m));


        } catch (Exception e) {

            Log.i("favoriteError", "error in placing the order" + e.getMessage());

        }

    }




    public void fetchRegisteredList(FirebaseUser user) {


        String emailId = getEmailStripped(user.getEmail());
        DatabaseReference databaseReference = mDatabaseReference1.child(user.getUid()).child(emailId).child("registered").child("event_ids");

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
        DatabaseReference databaseReference = mDatabaseReference1.child(user.getUid()).child(emailId).child("starred").child("event_ids");

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



/*
 ORGANIZERS API START FROM HERE

 */

    public void fetchParticipantsList(EventItem eventOrganized) {

        DatabaseReference databaseReference = mDatabaseReference1.child(eventOrganized.getName().toUpperCase());

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //TODO get the primary keys of all the users and do a refetch again for those users.

                /*

                events: {
                    0: "user1@gmail.com",
                    1: "user2@gmail.com",
                    2: "user3@gmail.com"
                },
                users: {
                    "user1@gmail.com": {
                        "name": "sagar",
                        "college":"uvce",
                    }
                }
                 */


                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {
                };
                ArrayList<String> participantsEmailsList = (ArrayList<String>) dataSnapshot.getValue(t);
                if (participantsEmailsList != null) {

                    //fetch the email_ids
                    StatusManagerForOrganizer.get(mAppContext).setParticipantsEmailIds(new ArrayList<String>(participantsEmailsList));

                    //fetch the data associated with email_ids
                    ArrayList<Participant> participants =  fetchParticipants(participantsEmailsList);

                    StatusManagerForOrganizer.get(mAppContext).setParticipants(participants);

                    MainActivity.notifyMe();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());

            }
        });
    }

    public ArrayList<Participant> fetchParticipants(ArrayList<String> participantsEmailIds) {


        ArrayList<Participant> participants = new ArrayList<>();

        for(int i = 0; i < participantsEmailIds.size(); i++) {

            String emailId = participantsEmailIds.get(i);

            Participant participant = fetchUsersDataByEmail(emailId);

            participants.add(participant);

        }

        return participants;
    }

    public Participant fetchUsersDataByEmail(String emailId) {

        final DatabaseReference databaseReference = mFirebaseDatabase.getReference("users_public").child(emailId);



        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Participant> t = new GenericTypeIndicator<Participant>() {
                };

                 FirebaseHelper.this.p = dataSnapshot.getValue(t);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }

        });


        return FirebaseHelper.this.p;

    }




   public static  class Participant {

      //  public String name;
        public String teamName;
        public String collegeName;
        public ArrayList<String> teamMembers;

        public Participant() {

        }


        public Participant(String name, String collegeName, ArrayList<String> teamMembers) {

            this.teamName = name;
            this.collegeName = collegeName;
            this.teamMembers = teamMembers;

        }


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

