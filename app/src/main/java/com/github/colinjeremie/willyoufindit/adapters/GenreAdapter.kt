package com.github.colinjeremie.willyoufindit.adapters

import android.support.annotation.VisibleForTesting
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.deezer.sdk.model.Genre
import com.deezer.sdk.network.request.event.JsonRequestListener
import com.deezer.sdk.network.request.event.RequestListener
import com.github.colinjeremie.willyoufindit.MyApplication
import com.github.colinjeremie.willyoufindit.R
import com.github.colinjeremie.willyoufindit.utils.normalize
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class GenreAdapter(private val onGenreClickListener: ((Genre) -> Unit), private val loadingCallback: (Boolean) -> Unit) : RecyclerView.Adapter<GenreAdapter.GenresViewHolder>() {

    private var dataSet: MutableList<Genre> = ArrayList()
    private var originalDataSet: List<Genre> = mutableListOf()

    @VisibleForTesting
    val listener: RequestListener = object : JsonRequestListener() {

        override fun onResult(o: Any, o1: Any) {
            originalDataSet = o as List<Genre>
            clearFilter()
            loadingCallback.invoke(false)
        }

        override fun onUnparsedResult(s: String, o: Any) {
            loadingCallback.invoke(false)
        }

        override fun onException(e: Exception, o: Any) {
            loadingCallback.invoke(false)
        }
    }

    fun fetchGenres() {
        loadingCallback.invoke(true)
        MyApplication.instance.deezerApi.getGenres(listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.genre_item, parent, false)

        return GenresViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        val model = dataSet[position]

        holder.name.text = model.name
        holder.itemView.setOnClickListener {
            onGenreClickListener.invoke(model)
        }
    }

    override fun getItemCount() = dataSet.size

    fun clearFilter() {
        dataSet.clear()
        dataSet.addAll(originalDataSet)
        notifyDataSetChanged()
    }

    fun filter(search: String) {
        getFilterObservable(search, originalDataSet)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    dataSet = result
                    notifyDataSetChanged()
                }
    }

    fun getFilterObservable(search: String, list: List<Genre>): Single<MutableList<Genre>> =
            Observable
                    .fromIterable(list)
                    .filter { it.name.normalize().contains(search.normalize(), ignoreCase = true) }
                    .toList()

    inner class GenresViewHolder(itemView: View,
                                 val name: TextView = itemView.findViewById(R.id.genre_name)) : RecyclerView.ViewHolder(itemView)
}
