package com.teamnotfoundexception.impetus.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamnotfoundexception.impetus.Databases.EventItem;
import com.teamnotfoundexception.impetus.Databases.EventsManager;
import com.teamnotfoundexception.impetus.Databases.StatusManagerForOrganizer;
import com.teamnotfoundexception.impetus.Databases.Time;
import com.teamnotfoundexception.impetus.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrgoHomeFragment extends Fragment {

    public TextView eventNameTextView, timerTextView;
    public Handler handler;
    public Runnable myRunnable;
    public long timeToEvent = 13578;

    public OrgoHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orgo_home, container, false);
        eventNameTextView = view.findViewById(R.id.fragOrgoHomeEventNameHolder);
        timerTextView =  view.findViewById(R.id.timerOrganizing);

        long unixTime = System.currentTimeMillis() / 1000L;
        EventItem eventItem = StatusManagerForOrganizer.get(getActivity().getApplicationContext()).mEventOrganized;
        long timeofevent = Long.parseLong(eventItem.getStartTime());
        timeToEvent = timeofevent - unixTime;
        if(timeToEvent < 0) {
            timerTextView.setText("EVENT STARTED");
            timeToEvent = 0;
        } else {

            Time time = EventsManager.get(getActivity().getApplicationContext()).convertSecondsToTime(timeToEvent);




            handler = new Handler();

            myRunnable = new Runnable() {
                @Override
                public void run() {
                    timeToEvent--;
                    if(timeToEvent > 0 && handler != null) {
                        if(getActivity() != null) {
                            Time time = EventsManager.get(getActivity().getApplicationContext()).convertSecondsToTime(timeToEvent);
                            timerTextView.setText(time.getHours() + ":" + time.getMinutes() + ":" + time.getSeconds());
                            handler.postDelayed(this, 1000);
                        } else {
                            handler = null;
                        }
                    } else {
                        handler = null;
                    }
                }
            };
            handler.postDelayed(myRunnable, 1000);
        }

        eventNameTextView.setText(eventItem.getName());
        return view;
    }

}
