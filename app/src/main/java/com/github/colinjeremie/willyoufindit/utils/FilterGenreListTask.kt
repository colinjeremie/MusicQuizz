package com.github.colinjeremie.willyoufindit.utils

import android.os.AsyncTask

import com.deezer.sdk.model.Genre

import java.util.ArrayList

class FilterGenreListTask(private val originalValues: List<Genre>,
                          private val callBack: OnListFilteredListener?) : AsyncTask<String, Void, MutableList<Genre>>() {

    override fun doInBackground(vararg params: String): MutableList<Genre> {
        if (params.isNotEmpty()) {
            val search = params[0].normalize().toLowerCase()
            val valuesFiltered = mutableListOf<Genre>()

            for (tmp in originalValues) {
                val title = tmp.name.normalize().toLowerCase()

                if (title.contains(search)) {
                    valuesFiltered.add(tmp)
                }
            }
            return valuesFiltered
        }
        return originalValues.toMutableList()
    }

    override fun onPostExecute(genres: MutableList<Genre>) {
        super.onPostExecute(genres)
        callBack?.onListFiltered(genres)
    }

    interface OnListFilteredListener {
        fun onListFiltered(list: MutableList<Genre>)
    }
}
