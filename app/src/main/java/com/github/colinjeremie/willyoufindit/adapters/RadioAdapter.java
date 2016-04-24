package com.github.colinjeremie.willyoufindit.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deezer.sdk.model.Radio;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;
import com.github.colinjeremie.willyoufindit.DeezerAPI;
import com.github.colinjeremie.willyoufindit.R;
import com.github.colinjeremie.willyoufindit.utils.FilterRadioListTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * * WillYouFindIt
 * Created by jerem_000 on 4/1/2016.
 */
public class RadioAdapter extends RecyclerView.Adapter<RadioAdapter.RadioViewHolder> implements FilterRadioListTask.OnListFilteredListener {
    /**
     * Task used to filter the Radios according to an input
     */
    private FilterRadioListTask mTask;

    /**
     * The original dataset returned from the Deezer API
     */
    private List<Radio> mOriginalDataSet = new ArrayList<>();

    /**
     * The original values used by the adapter
     */
    private List<Radio> mDataSet = new ArrayList<>();

    /**
     * Callback for the Deezer API after fetching the Radios
     */
    private final RequestListener mListener = new JsonRequestListener() {

        @SuppressWarnings("unchecked")
        @Override
        public void onResult(Object o, Object o1) {
            mOriginalDataSet = (List<Radio>) o;
            clearDoubles();
            mTask = new FilterRadioListTask(mOriginalDataSet, RadioAdapter.this);
            clearFilter();
        }

        @Override
        public void onUnparsedResult(String s, Object o) {

        }

        @Override
        public void onException(Exception e, Object o) {

        }
    };

    /**
     * Callback when an item has been clicked on
     */
    private OnRadioItemClickListener mRadioClickListener;

    /**
     * Remove all Radios item which already exist in the {@link #mOriginalDataSet} set of values
     */
    private void clearDoubles() {
        HashMap<Long, Radio> map = new HashMap<>();

        for (Radio tmp : mOriginalDataSet){
            map.put(tmp.getId(), tmp);
        }
        mOriginalDataSet.clear();
        mOriginalDataSet.addAll(map.values());
    }

    /**
     * Initialize the Adapter
     * @param pContext Context
     */
    public void init(Context pContext){
        DeezerAPI.getInstance(pContext).getRadios(mListener);
    }

    @Override
    public RadioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.radio_item, parent, false);
        return new RadioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RadioViewHolder holder, int position) {
        final Radio model = mDataSet.get(position);

        holder.mName.setText(model.getTitle());
        Glide
                .with(holder.mImage.getContext())
                .load(model.getPictureUrl())
                .into(holder.mImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRadioClickListener != null){
                    mRadioClickListener.onRadioItemClick(model);
                }
            }
        });
    }

    /**
     * Remove the filter used aka put the adapter in its original state
     */
    public void clearFilter(){
        mDataSet.clear();
        mDataSet.addAll(mOriginalDataSet);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    /**
     * Callback when the {@link FilterRadioListTask} finished to filter the results from the user's input
     * @param pList List of Radio filtered
     */
    @Override
    public void onListFiltered(List<Radio> pList) {
        mDataSet = pList;
        notifyDataSetChanged();
    }

    public void filter(String pSearch) {
        if (mTask != null && mTask.getStatus() != AsyncTask.Status.FINISHED){
            mTask.cancel(true);
        }
        mTask = new FilterRadioListTask(mOriginalDataSet, this);
        mTask.execute(pSearch);
    }

    public void setOnRadioItemClick(OnRadioItemClickListener pListener) {
        this.mRadioClickListener = pListener;
    }

    /**
     * View holder for a {@link Radio} item
     */
    public static class RadioViewHolder extends RecyclerView.ViewHolder{
        public final ImageView mImage;
        public final TextView mName;

        public RadioViewHolder(View itemView) {
            super(itemView);

            mName = (TextView) itemView.findViewById(R.id.radio_name);
            mImage = (ImageView) itemView.findViewById(R.id.radio_picture);
        }
    }

    public interface OnRadioItemClickListener{
        void onRadioItemClick(Radio pRadio);
    }
}
