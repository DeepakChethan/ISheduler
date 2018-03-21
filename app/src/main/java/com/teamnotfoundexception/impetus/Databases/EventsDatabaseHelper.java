package com.teamnotfoundexception.impetus.Databases;

/**
 * Created by sagar on 3/21/18.
 */
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



public class EventsDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "impetus.sqlite";
    public static final int VERSION = 1;

    private static final String TABLE_EVENTS = "events";
    private static final String COLUMN_EVENT_UUID = "uuid";
    private static final String COLUMN_EVENT_NAME = "name";
    private static final String COLUMN_EVENT_TYPE = "type";
    private static final String COLUMN_EVENT_DESCRIPTION = "description";
    private static final String COLUMN_EVENT_ENTRY_PRICE = "entry_price";
    private static final String COLUMN_EVENT_IMAGE_PATH = "image_path";
    private static final String COLUMN_EVENT_START_TIME = "start_time";
    private static final String COLUMN_EVENT_END_TIME = "end_time";
    private static final String COLUMN_EVENT_COLOR = "color";
    private static final String COLUMN_EVENT_IS_REGISTERED = "is_registered";
    private static final String COLUMN_EVENT_IS_STARRED= "is_starred";


    public EventsDatabaseHelper(Context context) {

        super(context, DB_NAME, null,  VERSION);
        Log.i("i", "database helper inited");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table events (" + " id integer primary key autoincrement, " +
                "name varchar(1000), " +
                "type varchar(1000), " +
                "uuid int, " +
                "description varchar(2000), " +
                "entry_price int," +
                " image_path varchar(2000), " +
                "start_time varchar(2000), " +
                "end_time varchar(2000), color varchar(2000)," +
                "is_registered int, is_starred int)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }




    public void deleteAllRows() {
        getWritableDatabase().delete(TABLE_EVENTS, null, null);
    }


    public EventItemCursor getAllEventItems() {
        Cursor wrapped = getReadableDatabase().query(TABLE_EVENTS, null, null, null, null, null, null);
        return new EventItemCursor(wrapped);
    }

    public long insertEventItem(EventItem eventItem) {

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EVENT_NAME, eventItem.getName());
        cv.put(COLUMN_EVENT_TYPE, eventItem.getType());
        cv.put(COLUMN_EVENT_DESCRIPTION, eventItem.getDescription());
        cv.put(COLUMN_EVENT_ENTRY_PRICE, eventItem.getPrice());
        cv.put(COLUMN_EVENT_UUID, eventItem.getId());
        cv.put(COLUMN_EVENT_IMAGE_PATH, eventItem.getImagePath());
        cv.put(COLUMN_EVENT_START_TIME, eventItem.getStartTime());
        cv.put(COLUMN_EVENT_END_TIME, eventItem.getEndTime());

        Log.i("inserted", "inserted mate");
        return getReadableDatabase().insert(TABLE_EVENTS, null, cv);

    }



    public static class EventItemCursor extends CursorWrapper {

        public EventItemCursor(Cursor c) {

            super(c);

        }


        EventItem createNewEventItem(int eventId, String eventName, String eventType,
                                     String description, int entryPrice, String imagePath,
                                     String startTime, String endTime, String color,
                                     int isRegistered, int isStarred) {


            EventItem eventItem = new EventItem();
            eventItem.setId(eventId);
            eventItem.setName(eventName);
            eventItem.setType(eventType);
            eventItem.setDescription(description);
            eventItem.setPrice(entryPrice);
            eventItem.setImagePath(imagePath);
            eventItem.setStartTime(startTime);
            eventItem.setEndTime(endTime);
            eventItem.setColor(color);
            eventItem.setRegistered(isRegistered);
            eventItem.setStarred(isStarred);

            return eventItem;

        }


        public EventItem getEventItem() {

            int eventId = getInt(getColumnIndex((COLUMN_EVENT_UUID)));
            String eventName = getString(getColumnIndex(COLUMN_EVENT_NAME));
            String eventType = getString(getColumnIndex((COLUMN_EVENT_TYPE)));
            String description = getString(getColumnIndex(COLUMN_EVENT_DESCRIPTION));
            int entryPrice = getInt(getColumnIndex(COLUMN_EVENT_ENTRY_PRICE));
            String imagePath = getString(getColumnIndex(COLUMN_EVENT_IMAGE_PATH));
            String startTime = getString(getColumnIndex((COLUMN_EVENT_START_TIME)));
            String endTime = getString(getColumnIndex((COLUMN_EVENT_END_TIME)));
            String color = getString(getColumnIndex((COLUMN_EVENT_COLOR)));
            int isRegistered = getInt(getColumnIndex(COLUMN_EVENT_IS_REGISTERED));
            int isStarred = getInt(getColumnIndex(COLUMN_EVENT_IS_STARRED));

            EventItem eventItem = createNewEventItem(eventId, eventName, eventType,
                                                    description, entryPrice, imagePath, startTime, endTime,
                                                    color, isRegistered, isStarred);
            Log.i("i", "returning getDishItem");
            return eventItem;


        }
    }



}




