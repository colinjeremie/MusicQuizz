package com.github.colinjeremie.willyoufindit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.request.event.DeezerError;
import com.deezer.sdk.player.TrackPlayer;
import com.deezer.sdk.player.exception.TooManyPlayersExceptions;
import com.github.colinjeremie.willyoufindit.DeezerAPI;
import com.github.colinjeremie.willyoufindit.R;


public class PlayerFragment extends Fragment implements View.OnClickListener {

    private static final String TRACK = "TRACK";

    private TrackPlayer mTrackPlayer;
    private Track mTrack;
    private View mPlayBtn;
    private View mPauseBtn;
    private View mStopBtn;

    public PlayerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTrack = getArguments().getParcelable(TRACK);
        }
        try {
            mTrackPlayer = DeezerAPI.getInstance(getActivity()).getTrackPlayer(getActivity().getApplication());
        } catch (DeezerError | TooManyPlayersExceptions deezerError) {
            deezerError.printStackTrace();
            mTrackPlayer = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);

        mPlayBtn = view.findViewById(R.id.play_btn);
        mPlayBtn.setOnClickListener(this);
        mPauseBtn = view.findViewById(R.id.pause_btn);
        mPauseBtn.setOnClickListener(this);
        mStopBtn = view.findViewById(R.id.stop_btn);
        mStopBtn.setOnClickListener(this);

        return view;
    }

    public void pauseTrack() {
        if (mTrackPlayer != null){
            mPlayBtn.setVisibility(View.VISIBLE);
            mPauseBtn.setVisibility(View.GONE);
            mTrackPlayer.pause();
        }
    }

    public void stopTrack() {
        if (mTrackPlayer != null){
            mPlayBtn.setVisibility(View.VISIBLE);
            mPauseBtn.setVisibility(View.GONE);
            mTrackPlayer.stop();
        }
    }

    public void playTrack() {
        if (mTrack != null && mTrackPlayer != null){
            mPlayBtn.setVisibility(View.GONE);
            mPauseBtn.setVisibility(View.VISIBLE);
            mTrackPlayer.playTrack(mTrack.getId());
        }
    }

    public void setTrack(Track pTrack){
        if (mTrack != null && mTrackPlayer != null){
            mTrackPlayer.stop();
        }
        mTrack = pTrack;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play_btn:
                playTrack();
                break;
            case R.id.pause_btn:
                pauseTrack();
                break;
            case R.id.stop_btn:
                stopTrack();
                break;
            default:
                break;
        }
    }
}
