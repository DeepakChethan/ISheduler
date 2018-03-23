package com.teamnotfoundexception.impetus.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.teamnotfoundexception.impetus.Databases.EventsManager;
import com.teamnotfoundexception.impetus.Databases.FirebaseHelper;

import com.teamnotfoundexception.impetus.Databases.StatusManagerForOrganizer;
import com.teamnotfoundexception.impetus.R;
import com.teamnotfoundexception.impetus.adapters.PlayerListAdapter;

public class OrgoPlayerFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private static PlayerListAdapter mPlayerListAdapter;
    public Handler handler;
    public Runnable myRunnable;

    public OrgoPlayerFragment() {
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
        View view = inflater.inflate(R.layout.fragment_item_list2, container, false);
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.item_list2);

        // Set the adapter
        if (recyclerView instanceof RecyclerView) {

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            mPlayerListAdapter =
            new PlayerListAdapter(StatusManagerForOrganizer.get(getActivity().getApplicationContext()).getParticipants(), mListener, getActivity().getApplicationContext());

            recyclerView.setAdapter(mPlayerListAdapter);

        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    @Override
    public void onResume() {
        super.onResume();
        handler = new Handler();
        myRunnable = new Runnable() {
            @Override
            public void run() {
                if(FirebaseHelper.FETCHING_PARTICIPANTS == 0) {
                    handler.postDelayed(this, 1000);
                    System.out.println("0)))");
                } else {
                    handler = null ;
                    mPlayerListAdapter.notifyDataSetChanged();
                    System.out.println("notifying player list");
                    System.out.println(StatusManagerForOrganizer.get(getActivity().getApplicationContext()).getParticipants().size());
                }

            }
        };
        handler.post(myRunnable);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        if(handler != null) {
            handler.removeCallbacks(myRunnable);
            handler = null;
        }
        super.onDetach();
        mListener = null;
    }

    public static void notifyMe() {
        mPlayerListAdapter.notifyDataSetChanged();
    }


    public interface OnListFragmentInteractionListener {

        void onListFragmentInteraction(FirebaseHelper.Participant item);

    }
}
