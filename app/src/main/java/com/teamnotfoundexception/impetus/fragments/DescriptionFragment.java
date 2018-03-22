package com.teamnotfoundexception.impetus.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.teamnotfoundexception.impetus.Databases.EventItem;
import com.teamnotfoundexception.impetus.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DescriptionFragment extends Fragment {


    TextView mDescription;
    ImageView mPosterContainer;

    public DescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_description2, container, false);
        mPosterContainer = (ImageView) view.findViewById(R.id.posterContainer);
        Bundle bundle = getArguments();
        mDescription = view.findViewById(R.id.eventDescription);
        if (bundle == null){
            Toast.makeText(getContext(),"Something is wrong with your phone!",Toast.LENGTH_SHORT).show();
            return view;
        }
        EventItem eventItem = (EventItem) bundle.getSerializable("dope");
        mDescription.setText(eventItem.getDescription());
        Glide.with(getContext()).load(eventItem.getImagePath()).into(mPosterContainer);


        return view;
    }

}
