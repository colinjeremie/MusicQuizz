package com.github.colinjeremie.willyoufindit.utils

import android.os.AsyncTask

import com.deezer.sdk.model.Radio

import java.util.ArrayList

class FilterRadioListTask(private val originalValues: List<Radio>,
                          private val callBack: OnListFilteredListener?) : AsyncTask<String, Void, MutableList<Radio>>() {

    override fun doInBackground(vararg params: String): MutableList<Radio> {
        if (params.isNotEmpty()) {
            val search = params[0].normalize().toLowerCase()
            val valuesFiltered = mutableListOf<Radio>()

            for (radio in originalValues) {
                val title = radio.title.normalize().toLowerCase()

                if (title.contains(search)) {
                    valuesFiltered.add(radio)
                }
            }
            return valuesFiltered
        }
        return originalValues.toMutableList()
    }

    override fun onPostExecute(radios: MutableList<Radio>) {
        super.onPostExecute(radios)
        callBack?.onListFiltered(radios)
    }

    interface OnListFilteredListener {
        fun onListFiltered(list: MutableList<Radio>)
    }
}
