package com.github.colinjeremie.willyoufindit

import android.app.Application
import com.deezer.sdk.network.connect.DeezerConnect
import com.deezer.sdk.network.request.DeezerRequestFactory
import com.deezer.sdk.network.request.event.RequestListener
import com.deezer.sdk.player.TrackPlayer
import com.deezer.sdk.player.networkcheck.WifiAndMobileNetworkStateChecker

class DeezerAPI constructor(private val application: Application) {
    private val deezerConnect by lazy {
        DeezerConnect
            .forApp(application.getString(R.string.deezer_app_id))
            .withContext(application)
            .build()
    }
    val trackPlayer: TrackPlayer by lazy {
        TrackPlayer(application, deezerConnect, WifiAndMobileNetworkStateChecker())
    }

    fun getGenres(requestListener: RequestListener) {
        val request = DeezerRequestFactory.requestGenres()
        deezerConnect.requestAsync(request, requestListener)
    }

    fun getRadios(requestListener: RequestListener) {
        val request = DeezerRequestFactory.requestRadios()
        deezerConnect.requestAsync(request, requestListener)
    }

    fun getGenreRadios(genreId: Long, requestListener: RequestListener) {
        val request = DeezerRequestFactory.requestGenreRadios(genreId)
        deezerConnect.requestAsync(request, requestListener)
    }

    fun getRadioTracks(radioId: Long, requestListener: RequestListener) {
        val request = DeezerRequestFactory.requestRadioTracks(radioId)
        deezerConnect.requestAsync(request, requestListener)
    }
}
