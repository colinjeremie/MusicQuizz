package com.github.colinjeremie.willyoufindit

import android.app.Application
import android.content.Context

import com.deezer.sdk.network.connect.DeezerConnect
import com.deezer.sdk.network.request.DeezerRequestFactory
import com.deezer.sdk.network.request.event.DeezerError
import com.deezer.sdk.network.request.event.RequestListener
import com.deezer.sdk.player.TrackPlayer
import com.deezer.sdk.player.exception.TooManyPlayersExceptions
import com.deezer.sdk.player.networkcheck.WifiAndMobileNetworkStateChecker

class DeezerAPI private constructor(context: Context) {
    companion object {
        private var instance: DeezerAPI? = null

        fun getInstance(context: Context): DeezerAPI {
            if (instance == null) {
                instance = DeezerAPI(context)
            }
            return instance!!
        }
    }

    private val deezerConnect = DeezerConnect.forApp(context.getString(R.string.deezer_app_id)).withContext(context).build()
    private var trackPlayer: TrackPlayer? = null

    @Throws(DeezerError::class, TooManyPlayersExceptions::class)
    fun getTrackPlayer(application: Application): TrackPlayer {
        if (trackPlayer == null) {
            trackPlayer = TrackPlayer(application, deezerConnect, WifiAndMobileNetworkStateChecker())
        }
        return trackPlayer!!
    }

    fun getGenres(requestListener: RequestListener) {
        val request = DeezerRequestFactory.requestGenres()
        deezerConnect.requestAsync(request, requestListener)
    }

    fun getRadios(requestListener: RequestListener) {
        val request = DeezerRequestFactory.requestRadios()
        deezerConnect.requestAsync(request, requestListener)
    }

    fun getGenreRadios(genreId: Long?, requestListener: RequestListener) {
        val request = DeezerRequestFactory.requestGenreRadios(genreId!!)
        deezerConnect.requestAsync(request, requestListener)
    }

    fun getRadioTracks(radioId: Long?, requestListener: RequestListener) {
        val request = DeezerRequestFactory.requestRadioTracks(radioId!!)
        deezerConnect.requestAsync(request, requestListener)
    }
}
