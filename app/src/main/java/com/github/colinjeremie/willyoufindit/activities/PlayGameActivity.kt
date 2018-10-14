package com.github.colinjeremie.willyoufindit.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.deezer.sdk.model.Track
import com.deezer.sdk.network.request.event.DeezerError
import com.deezer.sdk.player.TrackPlayer
import com.deezer.sdk.player.event.PlayerState
import com.deezer.sdk.player.exception.TooManyPlayersExceptions
import com.github.colinjeremie.willyoufindit.MyApplication
import com.github.colinjeremie.willyoufindit.R
import java.util.*

class PlayGameActivity : AppCompatActivity() {

    companion object {
        const val LIST_TRACKS = "LIST_TRACKS"
    }

    private var list: MutableList<Track>? = null
    private var track: Track? = null
    private var trackPlayer: TrackPlayer? = null

    private lateinit var playBtn: View
    private lateinit var pauseBtn: View
    private lateinit var trackArtistView: TextView
    private lateinit var trackTitleView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_game)

        list = intent?.getParcelableArrayListExtra(LIST_TRACKS) ?: mutableListOf()

        trackTitleView = findViewById<View>(R.id.track_title) as TextView
        trackArtistView = findViewById<View>(R.id.track_artist) as TextView

        playBtn = findViewById<View>(R.id.play_btn)
        playBtn.setOnClickListener { playTrack(track) }
        pauseBtn = findViewById<View>(R.id.pause_btn)
        pauseBtn.setOnClickListener { pauseTrack() }
        findViewById<View>(R.id.discover_song_btn)?.setOnClickListener { endOfTheSong(true) }
        findViewById<View>(R.id.next_song_btn)?.setOnClickListener { nextSong() }

        initPlayer()
        playRandomSong()
    }

    private fun initPlayer() {
        try {
            trackPlayer = MyApplication.instance.deezerApi.trackPlayer
            trackPlayer?.addOnPlayerStateChangeListener { playerState, _ ->
                if (playerState == PlayerState.PLAYBACK_COMPLETED) {
                    showPlayButton()
                }
            }
        } catch (error: DeezerError) {
            error.printStackTrace()
            anErrorHappened(error.localizedMessage)
        } catch (error: TooManyPlayersExceptions) {
            error.printStackTrace()
            anErrorHappened(error.localizedMessage)
        }
    }

    private fun showPlayButton() {
        playBtn.visibility = View.VISIBLE
        pauseBtn.visibility = View.GONE
    }

    private fun endOfTheSong(pauseIt: Boolean = false) {
        if (pauseIt) {
            trackPlayer?.pause()
        } else {
            trackPlayer?.stop()
        }

        showPlayButton()
        trackTitleView.visibility = View.VISIBLE
        trackArtistView.visibility = View.VISIBLE
        track?.let {
            trackTitleView.text = it.title
            trackArtistView.text = it.artist.name
        }
    }

    private fun nextSong() {
        endOfTheSong()
        playRandomSong()
    }

    private fun anErrorHappened(pMessage: String) {
        Toast.makeText(this, pMessage, Toast.LENGTH_SHORT).show()
    }

    private fun playRandomSong() {
        track = null
        val size = list?.size ?: 0

        if (size > 0) {
            val rand = Random().nextInt(size + 1)
            track = list?.get(rand % size)
            list?.remove(track)
            playTrack(track)
        } else {
            endOfGame()
        }
    }

    private fun endOfGame() {
        Snackbar.make(this.window.decorView, getString(R.string.end_of_game_message), Snackbar.LENGTH_LONG)
    }

    private fun hideSongInformation() {
        trackTitleView.visibility = View.GONE
        trackArtistView.visibility = View.GONE
    }

    private fun playTrack(track: Track?) {
        hideSongInformation()
        trackPlayer?.let { player ->
            track?.let {
                player.playTrack(it.id)
                playBtn.visibility = View.GONE
                pauseBtn.visibility = View.VISIBLE
            }
        }
    }

    private fun pauseTrack() {
        hideSongInformation()
        trackPlayer?.let {
            it.pause()
            playBtn.visibility = View.VISIBLE
            pauseBtn.visibility = View.GONE
        }
    }

    override fun onPause() {
        super.onPause()

        pauseTrack()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            if (item.itemId == android.R.id.home) {
                super.onBackPressed()
                true
            } else {
                super.onOptionsItemSelected(item)
            }
}
