package com.teamnotfoundexception.impetus.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.teamnotfoundexception.impetus.Databases.EventItem;
import com.teamnotfoundexception.impetus.Databases.StatusManager;
import com.teamnotfoundexception.impetus.R;
import com.teamnotfoundexception.impetus.activities.DescriptionActivity;
import com.teamnotfoundexception.impetus.fragments.EventsFragment.OnListFragmentInteractionListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MyEventsAdapter extends RecyclerView.Adapter<MyEventsAdapter.ViewHolder> {

    private List<EventItem> mEventItems;
    private final OnListFragmentInteractionListener mListener;

    public Context context;

    public MyEventsAdapter(List<EventItem> items, OnListFragmentInteractionListener listener, Context c) {
        mEventItems = items;
        mListener = listener;
        context = c;

    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    public void updateData(ArrayList<EventItem> items){
        mEventItems = new ArrayList<>();
        mEventItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        EventItem eventItemm = mEventItems.get(position);
        holder.setIsRecyclable(false);
        ArrayList<Integer> registeredlist = StatusManager.get(context).getRegisteredIdList();
        System.out.println("i am here");
        System.out.println("in bind view" + registeredlist.size());

            holder.mItem = mEventItems.get(position);
            final EventItem eventItem = holder.mItem;
            holder.mEventNameHolder.setText(eventItem.getName());
            holder.mEventTypeHolder.setText(eventItem.getType());
            holder.mEventCostHolder.setText(eventItem.getPrice() + "");
            Glide.with(context).load(eventItem.getImagePath()).into(holder.mEventImageHolder);

            holder.mStar.setVisibility(View.GONE);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        //StatusManager.get(context).addToRegistered(mEventItems.get(position));
                        mListener.onListFragmentInteraction(holder.mItem);
                    }
                    Intent intent = new Intent(context, DescriptionActivity.class);
                    intent.putExtra("msg", eventItem);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });




    }

    @Override
    public int getItemCount() {
        return mEventItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mEventNameHolder,mEventCostHolder;
        public final TextView mEventTypeHolder;
        public final ImageView mEventImageHolder;
        public final ImageView mStar;
        public EventItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mEventCostHolder = (TextView) view.findViewById(R.id.eventPriceHolder);
            mEventNameHolder = (TextView) view.findViewById(R.id.eventNameHolder);
            mEventTypeHolder = (TextView) view.findViewById(R.id.eventTypeHolder);
            mEventImageHolder = (ImageView) view.findViewById(R.id.eventImageSquare);
            mStar= (ImageView) view.findViewById(R.id.startContainer);

        }

    }
}
