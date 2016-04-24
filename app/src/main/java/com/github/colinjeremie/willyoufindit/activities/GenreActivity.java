package com.github.colinjeremie.willyoufindit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.deezer.sdk.model.Genre;
import com.deezer.sdk.model.Radio;
import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.github.colinjeremie.willyoufindit.DeezerAPI;
import com.github.colinjeremie.willyoufindit.R;
import com.github.colinjeremie.willyoufindit.adapters.GenreAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenreActivity extends AppCompatActivity implements GenreAdapter.OnGenreItemClickListener, SearchView.OnQueryTextListener {

    private GenreAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);

        RecyclerView genresView = (RecyclerView) findViewById(R.id.recycler_view);
        if (genresView != null) {
            genresView.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new GenreAdapter();
            genresView.setAdapter(mAdapter);
            mAdapter.setOnGenreItemClick(this);

            mAdapter.init(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        initSearchView(menu);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private final JsonRequestListener mGenreRadioListener = new JsonRequestListener() {

        @SuppressWarnings("unchecked")
        @Override
        public void onResult(Object o, Object o1) {
            List<Radio> radios = (List<Radio>) o;
            int size = radios.size();
            int position = new Random().nextInt(size);
            position %= size;

            Radio radio = radios.get(position);
            requestTrackFromRadio(radio);
        }

        @Override
        public void onUnparsedResult(String s, Object o) {

        }

        @Override
        public void onException(Exception e, Object o) {

        }
    };

    private final JsonRequestListener mTrackListener = new JsonRequestListener() {

        @SuppressWarnings("unchecked")
        @Override
        public void onResult(Object o, Object o1) {
            List<Track> tracks = (List<Track>) o;
            Intent intent = new Intent(GenreActivity.this, PlayGameActivity.class);
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

    private void requestTrackFromRadio(Radio pRadio){
        DeezerAPI.getInstance(this).getRadioTracks(pRadio.getId(), mTrackListener);
    }

    @Override
    public void onGenreItemClick(Genre pGenre) {
        DeezerAPI.getInstance(this).getGenreRadios(pGenre.getId(), mGenreRadioListener);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mAdapter.filter(newText);
        return true;
    }
}
