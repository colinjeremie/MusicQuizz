package com.github.colinjeremie.willyoufindit.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.request.event.DeezerError;
import com.deezer.sdk.player.TrackPlayer;
import com.deezer.sdk.player.event.OnPlayerStateChangeListener;
import com.deezer.sdk.player.event.PlayerState;
import com.deezer.sdk.player.exception.TooManyPlayersExceptions;
import com.github.colinjeremie.willyoufindit.DeezerAPI;
import com.github.colinjeremie.willyoufindit.R;

import java.util.List;
import java.util.Random;

public class PlayGameActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String LIST_TRACKS = "LIST_TRACKS";

    /**
     * List of {@link Track} for the game
     */
    private List<Track> mList;

    /**
     * the Track being played
     */
    private Track mTrack;

    /**
     * The player to play the music
     */
    private TrackPlayer mTrackPlayer;

    /**
     * The play button of the player
     */
    private View mPlayBtn;

    /**
     * The pause button of the player
     */
    private View mPauseBtn;

    /**
     * The textview to display the artist
     */
    private TextView mTrackArtistView;

    /**
     * The textview to display the track title
     */
    private TextView mTrackTitleView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        if (getIntent() != null) {
            mList = getIntent().getParcelableArrayListExtra(LIST_TRACKS);
        }
        mTrackTitleView = (TextView) findViewById(R.id.track_title);
        mTrackArtistView = (TextView) findViewById(R.id.track_artist);

        mPlayBtn = findViewById(R.id.play_btn);
        if (mPlayBtn != null) {
            mPlayBtn.setOnClickListener(this);
        }
        mPauseBtn = findViewById(R.id.pause_btn);
        if (mPauseBtn != null) {
            mPauseBtn.setOnClickListener(this);
        }

        View discoverBtn = findViewById(R.id.discover_song_btn);
        View nextSongBtn = findViewById(R.id.next_song_btn);
        if (discoverBtn != null) {
            discoverBtn.setOnClickListener(this);
        } if (nextSongBtn != null){
            nextSongBtn.setOnClickListener(this);
        }

        initPlayer();
        playRandomSong();
    }

    private void initPlayer() {
        try {
            mTrackPlayer = DeezerAPI.getInstance(this).getTrackPlayer(getApplication());
            mTrackPlayer.addOnPlayerStateChangeListener(new OnPlayerStateChangeListener() {
                @Override
                public void onPlayerStateChange(PlayerState playerState, long l) {
                    if (playerState == PlayerState.PLAYBACK_COMPLETED){
                        showPlayButton();
                    }
                }
            });
        } catch (DeezerError | TooManyPlayersExceptions deezerError) {
            deezerError.printStackTrace();
            anErrorHappened(deezerError.getLocalizedMessage());
        }
    }

    private void endOfTheSong(){
        endOfTheSong(false);
    }

    private void showPlayButton(){
        mPlayBtn.setVisibility(View.VISIBLE);
        mPauseBtn.setVisibility(View.GONE);
    }

    private void endOfTheSong(boolean pPauseIt) {
        if (pPauseIt){
            mTrackPlayer.pause();
        } else {
            mTrackPlayer.stop();
        }

        showPlayButton();
        mTrackTitleView.setVisibility(View.VISIBLE);
        mTrackArtistView.setVisibility(View.VISIBLE);
        if (mTrack != null){
            mTrackTitleView.setText(mTrack.getTitle());
            mTrackArtistView.setText(mTrack.getArtist().getName());
        }
    }

    private void nextSong(){
        endOfTheSong();
        playRandomSong();
    }

    private void anErrorHappened(String pMessage) {
        Toast.makeText(this, pMessage, Toast.LENGTH_SHORT).show();
    }
    
    private void playRandomSong() {
        mTrack = null;
        if (mList != null && mList.size() > 0){
            int size = mList.size();
            int rand = new Random().nextInt(size);
            mTrack = mList.get(rand % size);
            mList.remove(mTrack);
            playTrack(mTrack);
        } else {
            endOfGame();
        }
    }

    private void endOfGame() {
        Snackbar.make(this.getWindow().getDecorView(), "All songs have been played. Please go back and choose another radio ;)", Snackbar.LENGTH_LONG);
    }

    private void hideSongInformation(){
        mTrackTitleView.setVisibility(View.GONE);
        mTrackArtistView.setVisibility(View.GONE);
    }

    private void playTrack(Track pTrack) {
        hideSongInformation();
        if (mTrackPlayer != null && pTrack != null){
            mTrackPlayer.playTrack(pTrack.getId());
            mPlayBtn.setVisibility(View.GONE);
            mPauseBtn.setVisibility(View.VISIBLE);
        }
    }

    private void playTrack(){
        playTrack(mTrack);
    }

    private void pauseTrack(){
        hideSongInformation();
        if (mTrack != null && mTrackPlayer != null){
            mTrackPlayer.pause();
            mPlayBtn.setVisibility(View.VISIBLE);
            mPauseBtn.setVisibility(View.GONE);
        }
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
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.play_btn){
            playTrack();
        } else if (v.getId() == R.id.pause_btn){
            pauseTrack();
        } else if (v.getId() == R.id.discover_song_btn){
            endOfTheSong(true);
        } else if (v.getId() == R.id.next_song_btn){
            nextSong();
        }
    }
}
