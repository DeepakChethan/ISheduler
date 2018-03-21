package com.teamnotfoundexception.impetus.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.teamnotfoundexception.impetus.Databases.EventItem;
import com.teamnotfoundexception.impetus.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimerFragment extends Fragment {

    private TextView mTimeContainer,mTeamCount,mEventLocation,mEventTeamMax,mEventPrice,mEventType;

    public TimerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        mTimeContainer = (TextView) view.findViewById(R.id.foClock);
        mTeamCount = (TextView) view.findViewById(R.id.teamCountHolder);
        mEventType = (TextView) view.findViewById(R.id.eventTypeContainer);
        mEventTeamMax = (TextView) view.findViewById(R.id.maxTeamMemberContainer);
        mEventPrice = (TextView) view.findViewById(R.id.ticketCostContainer);
        mEventLocation = (TextView) view.findViewById(R.id.eventLocationContainer);
        Bundle bundle = getArguments();
        if (bundle==null) {
            Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
            return view;
        }
        EventItem item = (EventItem) bundle.getSerializable("dope");
        mTeamCount.setText("Change Me");
        mEventType.setText(item.getType());
        mEventTeamMax.setText(item.getMaxTeamSize()+"");
        mEventPrice.setText(item.getPrice()+"");
        mEventLocation.setText(item.getLocation());
        return view;
    }

}
