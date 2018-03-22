package com.teamnotfoundexception.impetus.Databases;

import java.io.Serializable;

/**
 * Created by sagar on 3/21/18.
 */

public class EventItem implements Serializable {

    public int mId;
    public String mName;
    public String mType;
    public int  mPrice;
    public String mDescription;
    public String mImagePath;
    public String mStartTime;
    public String mEndTime;
    public String mLocation;
    public int mMaxTeamSize;
    public int isRegistered  = 0;
    public int isStarred  = 0;
    


    public EventItem() {

    }

    public EventItem(EventItem eventItem) {

        this.mId = eventItem.getId();
        this.mName = eventItem.getName();
        this.mType = eventItem.getType();
        this.mPrice = eventItem.getPrice();
        this.mDescription = eventItem.getDescription();
        this.mImagePath = eventItem.getImagePath() ;
        this.mStartTime = eventItem.getStartTime();
        this.mEndTime = eventItem.getEndTime();
        this.mLocation = eventItem.getLocation();
        this.mMaxTeamSize = eventItem.getMaxTeamSize();


    }

    public EventItem(int dishId, String dishName, String dishType,
                     int price, String description, String imagePath,
                     String startTime, String endTime, String color, int maxTeam, int isRegistered,int isStarred) {

        this.mId = dishId;
        this.mName = dishName;
        this.mType = dishType;
        this.mPrice = price;
        this.mDescription = description;
        this.mImagePath = imagePath ;
        this.mStartTime = startTime;
        this.mEndTime = endTime;
        this.mLocation = color;
        this.isRegistered = isRegistered;
        this.isStarred = isStarred;
        this.mMaxTeamSize = maxTeam;
    }


    public void printt() {
        System.out.println(mId);
        System.out.println(mName);
        System.out.println(mType);
        System.out.println(mPrice);
        System.out.println(mDescription);
        System.out.println(mImagePath);
        System.out.println(mStartTime);
        System.out.println(mEndTime);
        System.out.println(mLocation);
        System.out.println(isRegistered);
        System.out.println(mMaxTeamSize);

    }
    public int getMaxTeamSize() { return mMaxTeamSize; }

    public void setMaxTeamSize(int maxTeam) {mMaxTeamSize = maxTeam;}

    public int isRegistered() {
        return isRegistered;
    }

    public int isStarred() {
        return isStarred;
    }

    public void setRegistered(int registered) {
        isRegistered = registered;
    }

    public void setStarred(int starred) {
        isStarred = starred;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String color) {
        mLocation = color;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String StartTime) {
        this.mStartTime = StartTime;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String endTime) {
        this.mEndTime = endTime;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        this.mImagePath = imagePath;
    }


    public String getName() {
        return mName;
    }

    public String getType() {
        return mType;
    }

    public int getPrice() {
        return mPrice;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public void setPrice(int price) {
        this.mPrice = price;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }




}
