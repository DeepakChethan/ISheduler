package com.teamnotfoundexception.impetus.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamnotfoundexception.impetus.Databases.EventItem;
import com.teamnotfoundexception.impetus.Databases.StatusManager;
import com.teamnotfoundexception.impetus.R;
import com.teamnotfoundexception.impetus.activities.DescriptionActivity;
import com.teamnotfoundexception.impetus.fragments.EventsFragment;

import java.util.ArrayList;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private ArrayList<EventItem> mEventItems;
    private  EventsFragment.OnListFragmentInteractionListener mListener;

    public Context context;

    public EventsAdapter(ArrayList<EventItem> items, EventsFragment.OnListFragmentInteractionListener listener, Context c) {
        mEventItems = items;
        mListener = listener;
        context = c;

    }
    public EventsAdapter(ArrayList<EventItem> items, Context c) {
        mEventItems = items;
        context = c;

    }

    public void dataChaged(ArrayList<EventItem> items){
        mEventItems = new ArrayList<>();
        mEventItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override

    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.mItem = mEventItems.get(position);
        final EventItem eventItem = holder.mItem;
        holder.mEventNameHolder.setText(eventItem.getName());
        holder.mEventTypeHolder.setText(eventItem.getType());
        holder.mEventCostHolder.setText(eventItem.getPrice()+"");
        //Log.i("dope","I am being called from events adapter");
        Log.i("dope","The length of event items is "+StatusManager.get(context).getRegisteredIdList().size());

        Log.i("dope","The length of event items in events adapter is "+mEventItems.size());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    //StatusManager.get(context).addToRegistered(mEventItems.get(position));

                    mListener.onListFragmentInteraction(holder.mItem);
                }
                Intent intent = new Intent(context, DescriptionActivity.class);
                intent.putExtra("msg",eventItem);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                StatusManager.get(context).addToStarred(eventItem);
                return true;
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
        public EventItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mEventCostHolder = (TextView) view.findViewById(R.id.eventPriceHolder);
            mEventNameHolder = (TextView) view.findViewById(R.id.eventNameHolder);
            mEventTypeHolder = (TextView) view.findViewById(R.id.eventTypeHolder);
            mEventImageHolder = (ImageView) view.findViewById(R.id.eventImageSquare);
        }

    }
}
