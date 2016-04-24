package com.github.colinjeremie.willyoufindit.utils;

import android.os.AsyncTask;

import com.deezer.sdk.model.Radio;

import java.util.ArrayList;
import java.util.List;

/**
 * * WillYouFindIt
 * Created by jerem_000 on 4/23/2016.
 */
public class FilterRadioListTask extends AsyncTask<String, Void, List<Radio>> {
    private final OnListFilteredListener mCallBack;
    private final List<Radio> mOriginalValues;

    public FilterRadioListTask(List<Radio> pValues, OnListFilteredListener pCallback){
        this.mOriginalValues = pValues;
        this.mCallBack = pCallback;
    }

    @Override
    protected List<Radio> doInBackground(String ... params) {
        if (params.length > 0){
            String search = NormalizeStr.normalizeIt(params[0]).toLowerCase();
            List<Radio> valuesFiltered = new ArrayList<>();

            for (Radio radio : mOriginalValues){
                String title = NormalizeStr.normalizeIt(radio.getTitle()).toLowerCase();

                if (title.contains(search)){
                    valuesFiltered.add(radio);
                }
            }
            return valuesFiltered;
        }
        return mOriginalValues;
    }

    @Override
    protected void onPostExecute(List<Radio> radios) {
        super.onPostExecute(radios);
        if (mCallBack != null){
            mCallBack.onListFiltered(radios);
        }
    }

    public interface OnListFilteredListener{
        void onListFiltered(List<Radio> pList);
    }
}
