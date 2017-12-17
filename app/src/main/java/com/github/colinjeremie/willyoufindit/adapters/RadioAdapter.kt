package com.github.colinjeremie.willyoufindit.adapters

import android.content.Context
import android.support.annotation.VisibleForTesting
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.deezer.sdk.model.Radio
import com.deezer.sdk.network.request.event.JsonRequestListener
import com.github.colinjeremie.willyoufindit.DeezerAPI
import com.github.colinjeremie.willyoufindit.R
import com.github.colinjeremie.willyoufindit.utils.normalize
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * * WillYouFindIt
 * Created by jerem_000 on 4/1/2016.
 */
class RadioAdapter : RecyclerView.Adapter<RadioAdapter.RadioViewHolder>() {
    /**
     * The original dataset returned from the Deezer API
     */
    @VisibleForTesting
    var originalDataSet: MutableList<Radio> = mutableListOf()

    /**
     * The original values used by the adapter
     */
    private var dataSet: MutableList<Radio> = mutableListOf()

    /**
     * Callback for the Deezer API after fetching the Radios
     */
    val listener: JsonRequestListener = object : JsonRequestListener() {

        override fun onResult(o: Any, o1: Any) {
            originalDataSet = (o as List<Radio>).distinctBy { it.id }.toMutableList()
            clearFilter()
        }

        override fun onUnparsedResult(s: String, o: Any) {
        }

        override fun onException(e: Exception, o: Any) {
        }
    }

    /**
     * Callback when an item has been clicked on
     */
    var onRadioClickListener: ((Radio) -> Unit)? = null

    /**
     * Initialize the Adapter
     * @param context Context
     */
    fun init(context: Context) {
        DeezerAPI.getInstance(context).getRadios(listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RadioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.radio_item, parent, false)

        return RadioViewHolder(view)
    }

    override fun onBindViewHolder(holder: RadioViewHolder, position: Int) {
        val model = dataSet[position]

        holder.name.text = model.title
        Glide
                .with(holder.image.context)
                .load(model.pictureUrl)
                .into(holder.image)

        holder.itemView.setOnClickListener { onRadioClickListener?.invoke(model) }
    }

    /**
     * Remove the filter used aka put the adapter in its original state
     */
    fun clearFilter() {
        dataSet.clear()
        dataSet.addAll(originalDataSet)
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataSet.size

    fun filter(search: String) {
        Observable
                .fromIterable(originalDataSet)
                .filter { it.title.normalize().contains(search.normalize(), ignoreCase = true) }
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    dataSet = result
                    notifyDataSetChanged()
                }
    }

    /**
     * View holder for a [Radio] item
     */
    class RadioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById<View>(R.id.radio_picture) as ImageView
        val name: TextView = itemView.findViewById<View>(R.id.radio_name) as TextView
    }
}
