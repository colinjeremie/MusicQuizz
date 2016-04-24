package com.github.colinjeremie.willyoufindit.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.request.event.DeezerError;
import com.deezer.sdk.player.TrackPlayer;
import com.deezer.sdk.player.exception.TooManyPlayersExceptions;
import com.github.colinjeremie.willyoufindit.DeezerAPI;
import com.github.colinjeremie.willyoufindit.R;

import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayGameFragment extends Fragment implements View.OnClickListener {
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

    /**
     * The root view of the Fragment
     */
    private View mRootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_play_game, container, false);

        if (getArguments() != null) {
            mList = getArguments().getParcelableArrayList(LIST_TRACKS);
        }
        mTrackTitleView = (TextView) mRootView.findViewById(R.id.track_title);
        mTrackArtistView = (TextView) mRootView.findViewById(R.id.track_artist);

        mPlayBtn = mRootView.findViewById(R.id.play_btn);
        mPlayBtn.setOnClickListener(this);
        mPauseBtn = mRootView.findViewById(R.id.pause_btn);
        mPauseBtn.setOnClickListener(this);

        mRootView.findViewById(R.id.discover_song_btn).setOnClickListener(this);
        mRootView.findViewById(R.id.next_song_btn).setOnClickListener(this);

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initPlayer();
        playRandomSong();
    }

    private void initPlayer() {
        try {
            mTrackPlayer = DeezerAPI.getInstance(getContext()).getTrackPlayer(getActivity().getApplication());
        } catch (DeezerError | TooManyPlayersExceptions deezerError) {
            deezerError.printStackTrace();
            anErrorHappened(deezerError.getLocalizedMessage());
        }
    }

    private void endOfTheSong(){
        endOfTheSong(false);
    }

    private void endOfTheSong(boolean pPauseIt) {
        if (pPauseIt){
            mTrackPlayer.pause();
        } else {
            mTrackPlayer.stop();
        }

        mPlayBtn.setVisibility(View.VISIBLE);
        mPauseBtn.setVisibility(View.GONE);
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
        Toast.makeText(getContext(), pMessage, Toast.LENGTH_SHORT).show();
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
        Snackbar.make(mRootView, "All songs have been played. Please go back and choose another radio ;)", Snackbar.LENGTH_LONG);
    }

    private void playTrack(Track pTrack) {
        mTrackTitleView.setVisibility(View.GONE);
        mTrackArtistView.setVisibility(View.GONE);
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
        mTrackTitleView.setVisibility(View.GONE);
        mTrackArtistView.setVisibility(View.GONE);
        if (mTrack != null && mTrackPlayer != null){
            mTrackPlayer.pause();
            mPlayBtn.setVisibility(View.VISIBLE);
            mPauseBtn.setVisibility(View.GONE);
        }
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
