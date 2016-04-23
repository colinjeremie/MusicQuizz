package com.github.colinjeremie.willyoufindit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.colinjeremie.willyoufindit.R;
import com.github.colinjeremie.willyoufindit.adapters.GenresAdapter;

public class GenresFragment extends Fragment {
    private RecyclerView mGenresView;
    private GenresAdapter mAdapter;

    public GenresFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_genres, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mGenresView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mGenresView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new GenresAdapter();
        mGenresView.setAdapter(mAdapter);

        mAdapter.init(getActivity());
    }
}
