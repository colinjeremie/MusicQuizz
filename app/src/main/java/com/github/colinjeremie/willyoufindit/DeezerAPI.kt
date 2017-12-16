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

    private val deezerConnect = DeezerConnect(context, context.getString(R.string.deezer_app_id))
    private var trackPlayer: TrackPlayer? = null

    @Throws(DeezerError::class, TooManyPlayersExceptions::class)
    fun getTrackPlayer(pApplication: Application): TrackPlayer {
        if (trackPlayer == null) {
            trackPlayer = TrackPlayer(pApplication, deezerConnect, WifiAndMobileNetworkStateChecker())
        }
        return trackPlayer!!
    }

    fun getGenres(pRequestListener: RequestListener) {
        val request = DeezerRequestFactory.requestGenres()
        deezerConnect.requestAsync(request, pRequestListener)
    }

    fun getRadios(pRequestListener: RequestListener) {
        val request = DeezerRequestFactory.requestRadios()
        deezerConnect.requestAsync(request, pRequestListener)
    }

    fun getGenreRadios(pGenreId: Long?, pRequestListener: RequestListener) {
        val request = DeezerRequestFactory.requestGenreRadios(pGenreId!!)
        deezerConnect.requestAsync(request, pRequestListener)
    }

    fun getRadioTracks(pRadioId: Long?, pRequestListener: RequestListener) {
        val request = DeezerRequestFactory.requestRadioTracks(pRadioId!!)
        deezerConnect.requestAsync(request, pRequestListener)
    }
}
