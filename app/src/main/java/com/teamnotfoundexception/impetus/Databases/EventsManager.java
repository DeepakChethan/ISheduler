package com.teamnotfoundexception.impetus.Databases;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class EventsManager {

    public static final String TAG = "EventsManager";



    private static EventsManager mEventsManager = null;

    private static EventsDatabaseHelper mDatabaseHelper ;

    private static ArrayList<EventItem> mEventItemsList = null;

    private Context mAppContext;

    private SharedPreferences mSharedPref;


    private EventsManager(Context context) {

        mAppContext = context;
        mDatabaseHelper = new EventsDatabaseHelper(mAppContext);
        mEventItemsList = new ArrayList<>();
        mSharedPref = mAppContext.getSharedPreferences("user_data", Context.MODE_PRIVATE);
    }


    public static void setEventsManager(EventsManager dishRepository) {
        mEventsManager = dishRepository;
    }

    public void insertAllEventItems() {

        Log.i("i", "in inserting function");


        //  String currentUser = CartManager.get(mAppContext).getUser().getEmail();

        boolean isInsertedBefore = mSharedPref.getString("inserted_before", "0").equals("0") ? false: true;

        if(!isInsertedBefore) {


            mDatabaseHelper.insertEventItem((new EventItem(1001,"Enigma", "TreasureHunt",30,  "scan qr codes at different places","", "1521876600","1521876600", "Entire Campus",2,0,0)));
            mDatabaseHelper.insertEventItem((new EventItem(1002,"Appathon", "TreasureHunt",30,  "scan qr codes at different places","", "1521876600","1521876600", "red",2,0,0)));
            mDatabaseHelper.insertEventItem((new EventItem(1003,"NeutralOxide", "TreasureHunt",30,  "scan qr codes at different places","", "1521876600","1521876600", "blue",2,0,0)));
            mDatabaseHelper.insertEventItem((new EventItem(1004,"Cyborg", "TreasureHunt",30,  "scan qr codes at different places","", "1521876600","1521876600","green",2,0,0)));
            mDatabaseHelper.insertEventItem((new EventItem(1005,"Break or Mech", "TreasureHunt",30,  "scan qr codes at different places","", "1521876600","1521876600", "purple",2,0,0)));
            mDatabaseHelper.insertEventItem((new EventItem(1006,"RoboMania", "TreasureHunt",30,  "scan qr codes at different places","", "1521876600","1521876600", "yellow",2,0,0)));
            mDatabaseHelper.insertEventItem((new EventItem(1007,"Eat me", "TreasureHunt",30,  "scan qr codes at different places","", "1521876600","1521876600", "violet",2,0,0)));
            mDatabaseHelper.insertEventItem((new EventItem(1008,"Breaking bad", "TreasureHunt",30,  "scan qr codes at different places","", "1521876600","1521876600", "indigo",2,0,0)));
            mDatabaseHelper.insertEventItem((new EventItem(1009,"So you think it is over?", "TreasureHunt",30,  "scan qr codes at different places","", "1521876600","1521876600","white",2,0,0)));
            mDatabaseHelper.insertEventItem((new EventItem(1010,"TechCrunch", "TreasureHunt",30,  "scan qr codes at different places","", "1521876600","1521876600","black",2,0,0)));

            SharedPreferences.Editor editor = mSharedPref.edit();
            editor.putString("inserted_before", "1");
            editor.apply();
            editor.commit();

        }
        else {
            Log.i("i", "it was inserted already");
        }
    }

    public void initializeEventItemsList() {
        if(EventsManager.get(mAppContext).getEventItemsList().size() == 0) {

            EventsDatabaseHelper.EventItemCursor mEventItemCursor = this.mDatabaseHelper.getAllEventItems();


            Log.i("i", "database helper inited");


            if (mEventItemCursor.getCount() != 0) {
                Log.i("i", "mDishcursor size" + mEventItemCursor.getCount());
                mEventItemCursor.moveToFirst();
                while (!mEventItemCursor.isAfterLast()) {
                    EventItem dishItem = mEventItemCursor.getEventItem();
                    Log.i("i", dishItem.getImagePath());
                    mEventItemsList.add(dishItem);
                    mEventItemCursor.moveToNext();
                }
            }
            mEventItemCursor.close();
        }
    }

    public static EventsManager  get(Context c) {
        if(mEventsManager == null) {
            mEventsManager = new EventsManager(c);
            Log.i("i", "dish repo initialized");
        }
        return mEventsManager;
    }


    public ArrayList<EventItem> getEventItemsList() {
        return mEventItemsList;
    }


    public Time convertSecondsToTime(long seconds) {
        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day *24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds)* 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) *60);
        Time time = new Time(hours, minute, second);
        return time;
    }

}

