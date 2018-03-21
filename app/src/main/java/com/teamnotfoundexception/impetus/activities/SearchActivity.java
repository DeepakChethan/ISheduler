package com.teamnotfoundexception.impetus.activities;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.teamnotfoundexception.impetus.Databases.EventItem;
import com.teamnotfoundexception.impetus.Databases.EventsManager;
import com.teamnotfoundexception.impetus.R;
import com.teamnotfoundexception.impetus.adapters.EventsAdapter;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    Toolbar tools;
    RecyclerView recyclerView;
    EventsAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        tools = (Toolbar) findViewById(R.id.tools);
        setSupportActionBar(tools);
        recyclerView = (RecyclerView) findViewById(R.id.slist);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        mAdapter =new EventsAdapter(EventsManager.get(getApplicationContext()).getEventItemsList(),getApplicationContext());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setIconified(false);
        searchView.setQueryHint("Search events");
        // TODO goback when the user is done searching
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.isEmpty()) {

                }
                newText = newText.toLowerCase();
                ArrayList<EventItem> newList = new ArrayList<>();
                ArrayList<EventItem> eventItems = EventsManager.get(getApplicationContext()).getEventItemsList();
                for (int i = 0; i < eventItems.size(); ++i) {
                    EventItem eventItem = eventItems.get(i);
                    String name = eventItem.getName();
                   // String loc = eventItem.getLocation();
                    String type = eventItem.getType();
                    if (name.contains(newText)  || type.contains(newText)) {
                        newList.add(eventItem);
                    }
                    mAdapter.dataChaged(newList);
                }

                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }
}
