package com.github.colinjeremie.willyoufindit;

import android.app.Application;
import android.content.Context;

import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.DeezerError;
import com.deezer.sdk.network.request.event.RequestListener;
import com.deezer.sdk.player.TrackPlayer;
import com.deezer.sdk.player.exception.TooManyPlayersExceptions;
import com.deezer.sdk.player.networkcheck.WifiAndMobileNetworkStateChecker;

/**
 * * WillYouFindIt
 * Created by jerem_000 on 3/9/2016.
 */
public class DeezerAPI {
    private final DeezerConnect mDeezerConnect;

    private static DeezerAPI _instance = null;
    private TrackPlayer mTrackPlayer;

    public static DeezerAPI getInstance(Context pContext){
        if (_instance == null){
            _instance = new DeezerAPI(pContext);
        }
        return _instance;
    }

    private DeezerAPI(Context pContext){
        mDeezerConnect = new DeezerConnect(pContext, pContext.getString(R.string.deezer_app_id));
    }

    public TrackPlayer getTrackPlayer(Application pApplication) throws DeezerError, TooManyPlayersExceptions {
        if (mTrackPlayer == null) {
            mTrackPlayer = new TrackPlayer(pApplication, mDeezerConnect, new WifiAndMobileNetworkStateChecker());
        }
        return mTrackPlayer;
    }

    public void getGenres(RequestListener pRequestListener){
        DeezerRequest request = DeezerRequestFactory.requestGenres();
        mDeezerConnect.requestAsync(request, pRequestListener);
    }

    public void getRadios(RequestListener pRequestListener) {
        DeezerRequest request = DeezerRequestFactory.requestRadios();
        mDeezerConnect.requestAsync(request, pRequestListener);
    }

    public void getGenreRadios(Long pGenreId, RequestListener pRequestListener) {
        DeezerRequest request = DeezerRequestFactory.requestGenreRadios(pGenreId);
        mDeezerConnect.requestAsync(request, pRequestListener);
    }

    public void getRadioTracks(Long pRadioId, RequestListener pRequestListener) {
        DeezerRequest request = DeezerRequestFactory.requestRadioTracks(pRadioId);
        mDeezerConnect.requestAsync(request, pRequestListener);
    }
}
