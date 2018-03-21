package com.teamnotfoundexception.impetus.Databases;

/**
 * Created by sagar on 3/21/18.
 */

public class Time {

    public long hours, minutes, seconds;

    public Time(long hours, long minutes, long seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public long getHours() {
        return hours;
    }

    public long getMinutes() {
        return minutes;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public long getSeconds() {
        return seconds;
    }


}

