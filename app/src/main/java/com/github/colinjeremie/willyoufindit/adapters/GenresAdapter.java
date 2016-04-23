package com.github.colinjeremie.willyoufindit.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deezer.sdk.model.Genre;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;
import com.github.colinjeremie.willyoufindit.DeezerAPI;
import com.github.colinjeremie.willyoufindit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * * WillYouFindIt
 * Created by jerem_000 on 4/1/2016.
 */
public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.GenresViewHolder> {
    private List<Genre> mDataSet = new ArrayList<>();

    private RequestListener mListener = new JsonRequestListener() {
        @Override
        public void onResult(Object o, Object o1) {
            mDataSet = (List<Genre>) o;
            notifyDataSetChanged();
        }

        @Override
        public void onUnparsedResult(String s, Object o) {

        }

        @Override
        public void onException(Exception e, Object o) {

        }
    };

    public void init(Context pContext){
        DeezerAPI.getInstance(pContext).getGenres(mListener);
    }


    @Override
    public GenresViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_item, parent, false);
        return new GenresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GenresViewHolder holder, int position) {
        Genre model = mDataSet.get(position);

        holder.mName.setText(model.getName());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class GenresViewHolder extends RecyclerView.ViewHolder{
        public TextView mName;

        public GenresViewHolder(View itemView) {
            super(itemView);

            mName = (TextView) itemView.findViewById(R.id.genre_name);
        }
    }
}
