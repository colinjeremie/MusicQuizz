package com.github.colinjeremie.willyoufindit;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.deezer.sdk.model.Permissions;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.connect.SessionStore;
import com.deezer.sdk.network.connect.event.DialogListener;
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
    private final Context mContext;
    private final DeezerConnect mDeezerConnect;

    private static final String[] _deezerAuthPermissions = new String[]{
            Permissions.BASIC_ACCESS,
            Permissions.MANAGE_LIBRARY,
            Permissions.LISTENING_HISTORY
    };

    private static DeezerAPI _instance = null;

    public static DeezerAPI getInstance(Context pContext){
        if (_instance == null){
            _instance = new DeezerAPI(pContext);
        }
        return _instance;
    }

    private DeezerAPI(Context pContext){
        mContext = pContext;
        mDeezerConnect = new DeezerConnect(pContext, mContext.getString(R.string.deezer_app_id));
    }

    public void connectUser(Activity pActivity, DialogListener pListener){
        mDeezerConnect.authorize(pActivity, _deezerAuthPermissions, pListener);
    }

    public boolean saveSession(){
        SessionStore sessionStore = new SessionStore();
        return sessionStore.save(mDeezerConnect, mContext);
    }

    public boolean restoreSession(){
        SessionStore sessionStore = new SessionStore();
        return sessionStore.restore(mDeezerConnect, mContext);
    }

    public void getTrack(long pTrackId, RequestListener pRequestListener){
        DeezerRequest request = DeezerRequestFactory.requestTrack(pTrackId);
        mDeezerConnect.requestAsync(request, pRequestListener);
    }

    public TrackPlayer playTrack(Application pApplication, long pTrackId) throws DeezerError, TooManyPlayersExceptions {
        TrackPlayer trackPlayer = new TrackPlayer(pApplication, mDeezerConnect, new WifiAndMobileNetworkStateChecker());
        trackPlayer.playTrack(pTrackId);

        return trackPlayer;
    }

    public void autocomplete(String pName, RequestListener pRequestListener){
        DeezerRequest request = DeezerRequestFactory.requestSearchAutocomplete(pName);
        mDeezerConnect.requestAsync(request, pRequestListener);
    }
}
