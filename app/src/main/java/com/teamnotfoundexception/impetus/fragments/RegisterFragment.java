package com.teamnotfoundexception.impetus.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamnotfoundexception.impetus.Databases.EventItem;
import com.teamnotfoundexception.impetus.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {



    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_register, container, false);

        return view;
    }

}