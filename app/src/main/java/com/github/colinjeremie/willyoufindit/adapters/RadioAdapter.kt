package com.github.colinjeremie.willyoufindit.adapters

import android.content.Context
import android.os.AsyncTask
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
import com.github.colinjeremie.willyoufindit.utils.FilterRadioListTask

/**
 * * WillYouFindIt
 * Created by jerem_000 on 4/1/2016.
 */
class RadioAdapter : RecyclerView.Adapter<RadioAdapter.RadioViewHolder>(), FilterRadioListTask.OnListFilteredListener {
    /**
     * Task used to filter the Radios according to an input
     */
    private var task: FilterRadioListTask? = null

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
            originalDataSet = (o as List<Radio>).toMutableList()
            clearDoubles()
            task = FilterRadioListTask(originalDataSet, this@RadioAdapter)
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
     * Remove all Radios item which already exist in the [.originalDataSet] set of values
     */
    fun clearDoubles() {
        originalDataSet = originalDataSet.distinctBy { it.id }.toMutableList()
    }

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

    /**
     * Callback when the [FilterRadioListTask] finished to filter the results from the user's input
     * @param list List of Radio filtered
     */
    override fun onListFiltered(list: MutableList<Radio>) {
        dataSet = list
        notifyDataSetChanged()
    }

    fun filter(pSearch: String) {
        if (task?.status != AsyncTask.Status.FINISHED) {
            task?.cancel(true)
        }
        task = FilterRadioListTask(originalDataSet, this)
        task?.execute(pSearch)
    }

    /**
     * View holder for a [Radio] item
     */
    class RadioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById<View>(R.id.radio_picture) as ImageView
        val name: TextView = itemView.findViewById<View>(R.id.radio_name) as TextView
    }
}
