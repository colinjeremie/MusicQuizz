package com.github.colinjeremie.willyoufindit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.deezer.sdk.model.Album;
import com.deezer.sdk.model.Artist;
import com.deezer.sdk.model.CombinedResult;
import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;
import com.github.colinjeremie.willyoufindit.DeezerAPI;
import com.github.colinjeremie.willyoufindit.R;
import com.github.colinjeremie.willyoufindit.adapters.SearchAdapter;

import java.util.List;

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener {

    private View mErrorView;
    private RecyclerView mResultView;
    private SearchAdapter mSearchAdapter;

    private RequestListener mAutoCompleteListener = new JsonRequestListener() {
        @Override
        public void onResult(Object o, Object o1) {
            mSearchAdapter.clear();
            CombinedResult res = (CombinedResult) o;
            List<Track> tracks = res.getTracks();
            List<Album> albums = res.getAlbums();
            List<Artist> artists = res.getArtists();

            if (tracks.size() == 0 && albums.size() == 0 && artists.size() == 0){
                mSearchAdapter.clear();
                showErrorView();
            } else {
                mSearchAdapter.setTracks(tracks);
                mSearchAdapter.setAlbums(albums);
                mSearchAdapter.setArtists(artists);
                mSearchAdapter.notifyDataSetChanged();
                hideErrorView();
            }
        }

        @Override
        public void onUnparsedResult(String s, Object o) {

        }

        @Override
        public void onException(Exception e, Object o) {

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mErrorView = view.findViewById(R.id.error_view);
        mResultView = (RecyclerView) view.findViewById(R.id.result_recycler_view);

        mSearchAdapter = new SearchAdapter();
        mResultView.setAdapter(mSearchAdapter);
        mSearchAdapter.setHeadersSection(getResources().getStringArray(R.array.search_sections_array));
        mResultView.setLayoutManager(new LinearLayoutManager(getContext()));
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
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.isEmpty()){
            mSearchAdapter.clear();
        } else {
            DeezerAPI.getInstance(getContext()).autocomplete(newText, mAutoCompleteListener);
        }
        return false;
    }

    private void showErrorView() {
        mResultView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
    }

    private void hideErrorView() {
        mResultView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
    }
}
