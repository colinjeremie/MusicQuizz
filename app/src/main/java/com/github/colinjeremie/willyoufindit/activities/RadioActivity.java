package com.github.colinjeremie.willyoufindit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.deezer.sdk.model.Radio;
import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.github.colinjeremie.willyoufindit.DeezerAPI;
import com.github.colinjeremie.willyoufindit.R;
import com.github.colinjeremie.willyoufindit.adapters.RadioAdapter;

import java.util.ArrayList;
import java.util.List;

public class RadioActivity extends AppCompatActivity implements RadioAdapter.OnRadioItemClickListener, SearchView.OnQueryTextListener {
    private RadioAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);

        RecyclerView genresView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new RadioAdapter();
        if (genresView != null) {
            genresView.setLayoutManager(new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL));
            genresView.setAdapter(mAdapter);

            mAdapter.setOnRadioItemClick(this);
            mAdapter.init(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        initSearchView(menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void initSearchView(Menu menu) {
        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView actionView = (SearchView) MenuItemCompat.getActionView(item);

        actionView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                mAdapter.clearFilter();
                return true;
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mAdapter.filter(newText);
        return false;
    }

    private final JsonRequestListener mTrackListener = new JsonRequestListener() {

        @SuppressWarnings("unchecked")
        @Override
        public void onResult(Object o, Object o1) {
            List<Track> tracks = (List<Track>) o;
            Intent intent = new Intent(RadioActivity.this, PlayGameActivity.class);
            intent.putParcelableArrayListExtra(PlayGameActivity.LIST_TRACKS, new ArrayList<Parcelable>(tracks));

            startActivity(intent);
        }

        @Override
        public void onUnparsedResult(String s, Object o) {

        }

        @Override
        public void onException(Exception e, Object o) {

        }
    };

    @Override
    public void onRadioItemClick(Radio pRadio) {
        DeezerAPI.getInstance(this).getRadioTracks(pRadio.getId(), mTrackListener);
    }
}
