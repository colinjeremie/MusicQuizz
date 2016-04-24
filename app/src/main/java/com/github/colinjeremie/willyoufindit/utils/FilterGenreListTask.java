package com.github.colinjeremie.willyoufindit.utils;

import android.os.AsyncTask;

import com.deezer.sdk.model.Genre;

import java.util.ArrayList;
import java.util.List;

/**
 * * WillYouFindIt
 * Created by jerem_000 on 4/23/2016.
 */
public class FilterGenreListTask extends AsyncTask<String, Void, List<Genre>> {
    private final OnListFilteredListener mCallBack;
    private final List<Genre> mOriginalValues;

    public FilterGenreListTask(List<Genre> pValues, OnListFilteredListener pCallback){
        this.mOriginalValues = pValues;
        this.mCallBack = pCallback;
    }

    @Override
    protected List<Genre> doInBackground(String ... params) {
        if (params.length > 0){
            String search = NormalizeStr.normalizeIt(params[0]).toLowerCase();
            List<Genre> valuesFiltered = new ArrayList<>();

            for (Genre tmp : mOriginalValues){
                String title = NormalizeStr.normalizeIt(tmp.getName()).toLowerCase();

                if (title.contains(search)){
                    valuesFiltered.add(tmp);
                }
            }
            return valuesFiltered;
        }
        return mOriginalValues;
    }

    @Override
    protected void onPostExecute(List<Genre> Genres) {
        super.onPostExecute(Genres);
        if (mCallBack != null){
            mCallBack.onListFiltered(Genres);
        }
    }

    public interface OnListFilteredListener{
        void onListFiltered(List<Genre> pList);
    }
}
