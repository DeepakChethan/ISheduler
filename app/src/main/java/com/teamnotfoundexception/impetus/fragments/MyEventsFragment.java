package com.teamnotfoundexception.impetus.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.teamnotfoundexception.impetus.Databases.EventsManager;
import com.teamnotfoundexception.impetus.R;
import com.teamnotfoundexception.impetus.adapters.MyEventsAdapter;

public class MyEventsFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 2;

    private EventsFragment.OnListFragmentInteractionListener mListener;

    public MyEventsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_events, container, false);
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.listMyEvents);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.myEventsHidden);
        if (EventsManager.get(context).getEventItemsList().size() == 0){
            recyclerView.setVisibility(View.GONE);
          relativeLayout.setVisibility(View.VISIBLE);
        }else {
            recyclerView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
        }
         //Set the adapter
        if (view instanceof RecyclerView) {

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            recyclerView.setAdapter(new MyEventsAdapter(EventsManager.get(context).getEventItemsList(), mListener, getActivity().getApplicationContext()));

        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EventsFragment.OnListFragmentInteractionListener) {
            mListener = (EventsFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
