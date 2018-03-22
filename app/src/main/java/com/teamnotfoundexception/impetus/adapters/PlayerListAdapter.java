package com.teamnotfoundexception.impetus.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamnotfoundexception.impetus.Databases.FirebaseHelper;
import com.teamnotfoundexception.impetus.R;
import com.teamnotfoundexception.impetus.fragments.OrgoPlayerFragment.OnListFragmentInteractionListener;

import java.util.List;



public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.ViewHolder> {


    private final List<FirebaseHelper.Participant> mParticipants;
    private final OnListFragmentInteractionListener mListener;
    public Context context;

    public PlayerListAdapter(List<FirebaseHelper.Participant> participants, OnListFragmentInteractionListener listener, Context c) {

        mParticipants = participants;
        mListener = listener;
        context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.participant = mParticipants.get(position);
        holder.mTeamName.setText(holder.participant.teamName);
        //holder.mParticipants.setText(holder.participant.teamMembers);
        holder.mCollege.setText(holder.participant.collegeName);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mParticipants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTeamName;
        public final TextView mParticipants;
        public final TextView mCollege;
        public FirebaseHelper.Participant participant;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTeamName = (TextView) view.findViewById(R.id.playTeamName);
            mParticipants = (TextView) view.findViewById(R.id.playParticipants);
            mCollege = (TextView) view.findViewById(R.id.playCollege);
         }

    }
}
