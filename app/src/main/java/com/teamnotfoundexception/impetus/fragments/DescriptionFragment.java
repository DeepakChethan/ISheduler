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
public class DescriptionFragment extends Fragment {


    TextView mDescription;
    public DescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_description2, container, false);

        Bundle bundle = getArguments();
        mDescription = view.findViewById(R.id.eventDescription);
        if (bundle == null){
            Toast.makeText(getContext(),"Something is wrong with your phone!",Toast.LENGTH_SHORT).show();
            return view;
        }
        EventItem eventItem = (EventItem) bundle.getSerializable("dope");
        mDescription.setText(eventItem.getDescription());



        return view;
    }

}
