package com.teamnotfoundexception.impetus.fragments;


import android.content.Context;
import android.os.AsyncTask;
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
import com.teamnotfoundexception.impetus.Databases.StatusManager;
import com.teamnotfoundexception.impetus.R;
import com.teamnotfoundexception.impetus.adapters.MyEventsAdapter;
import com.teamnotfoundexception.impetus.adapters.StarredAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class StarredFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 2;
    public StarredAdapter starredAdapter;
    private EventsFragment.OnListFragmentInteractionListener mListener;

    public StarredFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_starred, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.listStarred);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.starHidden);
        Context context = view.getContext();

        if (EventsManager.get(context).getEventItemsList().size() == 0){
            recyclerView.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
        }

        if (view instanceof RecyclerView) {

            if (mColumnCount <= 1) {

                recyclerView.setLayoutManager(new LinearLayoutManager(context));

            } else {

                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));

            }

            starredAdapter = new StarredAdapter(EventsManager.get(context).getEventItemsList(),mListener,getActivity().getApplicationContext());

            recyclerView.setAdapter(starredAdapter);

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


    public static void notifyMe() {

    //    starredAdapter.notifyDataSetChanged();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
