package com.github.colinjeremie.willyoufindit.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.deezer.sdk.model.Radio;
import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.github.colinjeremie.willyoufindit.DeezerAPI;
import com.github.colinjeremie.willyoufindit.R;
import com.github.colinjeremie.willyoufindit.adapters.RadioAdapter;
import com.github.colinjeremie.willyoufindit.utils.OnSwitchContentListener;

import java.util.ArrayList;
import java.util.List;

public class RadioFragment extends Fragment implements SearchView.OnQueryTextListener, RadioAdapter.OnRadioItemClickListener {
    private RadioAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genres, container, false);

        RecyclerView genresView = (RecyclerView) view.findViewById(R.id.recycler_view);
        genresView.setLayoutManager(new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL));
        mAdapter = new RadioAdapter();
        genresView.setAdapter(mAdapter);

        mAdapter.setOnRadioItemClick(this);
        mAdapter.init(getActivity());

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);

        initSearchView(menu);
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
            Fragment fragment = new PlayGameFragment();
            Bundle args = new Bundle();
            args.putParcelableArrayList(PlayGameFragment.LIST_TRACKS, new ArrayList<Parcelable>(tracks));
            fragment.setArguments(args);
            ((OnSwitchContentListener) getActivity()).onSwitchContent(fragment);
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
        DeezerAPI.getInstance(getContext()).getRadioTracks(pRadio.getId(), mTrackListener);
    }
}
