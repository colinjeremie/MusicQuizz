package com.github.colinjeremie.willyoufindit.activities

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import com.deezer.sdk.model.Radio
import com.deezer.sdk.model.Track
import com.deezer.sdk.network.request.event.JsonRequestListener
import com.github.colinjeremie.willyoufindit.MyApplication
import com.github.colinjeremie.willyoufindit.R
import com.github.colinjeremie.willyoufindit.adapters.GenreAdapter
import java.util.*

class GenreActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val adapter: GenreAdapter by lazy {
        GenreAdapter({ genre ->
            MyApplication.instance.deezerApi.getGenreRadios(genre.id, genreRadioListener)
        }, { isLoading ->
            loaderView.visibility = if (isLoading) View.VISIBLE else View.GONE
            recyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
        })
    }
    private val genreRadioListener = object : JsonRequestListener() {

        override fun onResult(o: Any, o1: Any) {
            val radios = o as List<Radio>
            val size = radios.size

            if (radios.isNotEmpty()) {
                val position = Random().nextInt(size + 1) % size

                requestTrackFromRadio(radios[position])
            }
        }

        override fun onUnparsedResult(s: String, o: Any) {
        }

        override fun onException(e: Exception, o: Any) {
        }
    }
    private val trackListener = object : JsonRequestListener() {

        override fun onResult(o: Any, o1: Any) {
            val tracks = o as List<Track>
            val intent = Intent(this@GenreActivity, PlayGameActivity::class.java)
            intent.putParcelableArrayListExtra(PlayGameActivity.LIST_TRACKS, ArrayList<Parcelable>(tracks))

            startActivity(intent)
        }

        override fun onUnparsedResult(s: String, o: Any) {
        }

        override fun onException(e: Exception, o: Any) {
        }
    }

    private val loaderView: ProgressBar by lazy { findViewById<ProgressBar>(R.id.progress_bar) }
    private val recyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.recycler_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        adapter.fetchGenres()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        initSearchView(menu)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return super.onCreateOptionsMenu(menu)
    }

    private fun initSearchView(menu: Menu) {
        val item = menu.findItem(R.id.menu_search)
        val actionView = item.actionView as SearchView

        actionView.setOnQueryTextListener(this)
        item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                adapter.clearFilter()
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            if (item.itemId == android.R.id.home) {
                onBackPressed()
                true
            } else {
                super.onOptionsItemSelected(item)
            }

    private fun requestTrackFromRadio(pRadio: Radio) {
        MyApplication.instance.deezerApi.getRadioTracks(pRadio.id, trackListener)
    }

    override fun onQueryTextSubmit(query: String): Boolean = false

    override fun onQueryTextChange(newText: String): Boolean {
        adapter.filter(newText)
        return true
    }
}
