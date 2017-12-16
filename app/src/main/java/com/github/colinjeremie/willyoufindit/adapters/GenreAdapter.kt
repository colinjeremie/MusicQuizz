package com.github.colinjeremie.willyoufindit.adapters

import android.content.Context
import android.os.AsyncTask
import android.support.annotation.VisibleForTesting
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.deezer.sdk.model.Genre
import com.deezer.sdk.network.request.event.JsonRequestListener
import com.deezer.sdk.network.request.event.RequestListener
import com.github.colinjeremie.willyoufindit.DeezerAPI
import com.github.colinjeremie.willyoufindit.R
import com.github.colinjeremie.willyoufindit.utils.FilterGenreListTask
import com.github.colinjeremie.willyoufindit.utils.FilterRadioListTask

import java.util.ArrayList

class GenreAdapter : RecyclerView.Adapter<GenreAdapter.GenresViewHolder>(), FilterGenreListTask.OnListFilteredListener {
    /**
     * Task used to filter the Radios according to an input
     */
    private var task: FilterGenreListTask? = null

    /**
     * Listener when an item is clicked
     */
    var onGenreClickListener: ((Genre) -> Unit)? = null

    /**
     * The data used for the adapter
     */
    private var dataSet: MutableList<Genre> = ArrayList()

    /**
     * The original data
     */
    private var originalDataSet: List<Genre> = ArrayList()

    @VisibleForTesting
    val listener: RequestListener = object : JsonRequestListener() {

        override fun onResult(o: Any, o1: Any) {
            originalDataSet = o as List<Genre>
            task = FilterGenreListTask(originalDataSet, this@GenreAdapter)

            clearFilter()
        }

        override fun onUnparsedResult(s: String, o: Any) {
        }

        override fun onException(e: Exception, o: Any) {
        }
    }


    fun init(context: Context) {
        DeezerAPI.getInstance(context).getGenres(listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.genre_item, parent, false)

        return GenresViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        val model = dataSet[position]

        holder.name.text = model.name
        holder.itemView.setOnClickListener {
            onGenreClickListener?.invoke(model)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    /**
     * Remove the filter used aka put the adapter in its original state
     */
    fun clearFilter() {
        dataSet.clear()
        dataSet.addAll(originalDataSet)
        notifyDataSetChanged()
    }

    /**
     * Callback when the [FilterRadioListTask] finished to filter the results from the user's input
     * @param list List of Radio filtered
     */
    override fun onListFiltered(list: MutableList<Genre>) {
        dataSet = list
        notifyDataSetChanged()
    }

    fun filter(pSearch: String) {
        if (task?.status != AsyncTask.Status.FINISHED) {
            task?.cancel(true)
        }
        task = FilterGenreListTask(originalDataSet, this)
        task?.execute(pSearch)
    }

    inner class GenresViewHolder(itemView: View,
                                 val name: TextView = itemView.findViewById(R.id.genre_name)) : RecyclerView.ViewHolder(itemView)
}
