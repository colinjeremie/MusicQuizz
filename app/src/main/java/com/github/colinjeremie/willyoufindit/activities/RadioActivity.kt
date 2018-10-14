package com.github.colinjeremie.willyoufindit.activities

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import com.deezer.sdk.model.Track
import com.deezer.sdk.network.request.event.JsonRequestListener
import com.github.colinjeremie.willyoufindit.MyApplication
import com.github.colinjeremie.willyoufindit.R
import com.github.colinjeremie.willyoufindit.adapters.RadioAdapter
import java.util.*

class RadioActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private val adapter: RadioAdapter by lazy {
        RadioAdapter({ radio ->
            MyApplication.instance.deezerApi.getRadioTracks(radio.id, trackListener)
        }, { isLoading ->
            loaderView.visibility = if (isLoading) View.VISIBLE else View.GONE
            recyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
        })
    }

    private val trackListener = object : JsonRequestListener() {

        override fun onResult(o: Any, o1: Any) {
            val tracks = o as List<Track>
            val intent = Intent(this@RadioActivity, PlayGameActivity::class.java)
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

        recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerView.adapter = adapter

        adapter.fetchRadios()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        initSearchView(menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            if (item.itemId == android.R.id.home) {
                onBackPressed()
                true
            } else {
                super.onOptionsItemSelected(item)
            }

    private fun initSearchView(menu: Menu) {
        val item = menu.findItem(R.id.menu_search)
        val actionView = item.actionView as SearchView

        actionView.setOnQueryTextListener(this)
        item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean = true

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                adapter.clearFilter()
                return true
            }
        })
    }

    override fun onQueryTextSubmit(query: String): Boolean = false

    override fun onQueryTextChange(newText: String): Boolean {
        adapter.filter(newText)
        return false
    }
}
