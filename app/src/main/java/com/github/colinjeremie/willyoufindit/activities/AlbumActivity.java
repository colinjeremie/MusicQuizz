package com.github.colinjeremie.willyoufindit.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.deezer.sdk.model.Album;
import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;
import com.github.colinjeremie.willyoufindit.DeezerAPI;
import com.github.colinjeremie.willyoufindit.R;
import com.github.colinjeremie.willyoufindit.adapters.SearchAdapter;
import com.github.colinjeremie.willyoufindit.fragments.PlayerFragment;

public class AlbumActivity extends AppCompatActivity implements SearchAdapter.OnTrackClickListener {

    public static final String ALBUM = "ALBUM";
    private static final String TAG = "ALBUM_ACTIVITY";

    private View mPlayerView;
    private Album mAlbum;
    private RecyclerView mResultView;
    private SearchAdapter mSearchAdapter;
    private PlayerFragment mPlayerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        mAlbum = getIntent().getParcelableExtra(ALBUM);
        mResultView = (RecyclerView) findViewById(R.id.result_recycler_view);
        mPlayerView = findViewById(R.id.player);
        mPlayerFragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.player_fragment);

        mSearchAdapter = new SearchAdapter();
        mResultView.setAdapter(mSearchAdapter);
        mSearchAdapter.setHeadersSection(getResources().getStringArray(R.array.search_sections_array));
        mResultView.setLayoutManager(new LinearLayoutManager(this));

        DeezerAPI.getInstance(this).getAlbumFromId(mAlbum.getId(), mListener);
        setTitle(mAlbum.getTitle());

        mSearchAdapter.setTrackClickListener(this);
    }

    private RequestListener mListener = new JsonRequestListener() {
        @Override
        public void onResult(Object result, Object requestId){
            mAlbum = (Album) result;
            mSearchAdapter.clear();
            mSearchAdapter.setTracks(mAlbum.getTracks());
            mSearchAdapter.notifyDataSetChanged();
        }

        @Override
        public void onUnparsedResult(String s, Object o) {

        }

        @Override
        public void onException(Exception e, Object o) {

        }
    };

    @Override
    public void onTrackClick(Track pTrack) {
        Log.d(TAG, "Click on track : " + pTrack.getTitle());
        mPlayerView.setVisibility(View.VISIBLE);
        mPlayerFragment.setTrack(pTrack);
        mPlayerFragment.playTrack();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
