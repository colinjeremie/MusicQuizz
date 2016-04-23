package com.github.colinjeremie.willyoufindit.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.colinjeremie.willyoufindit.R;

/**
 * * WillYouFindIt
 * Created by jerem_000 on 4/23/2016.
 */
public class BaseTutoFragment extends Fragment {
    public static final java.lang.String LAYOUT_RES = "LAYOUT_RES";


    public static BaseTutoFragment getInstance(int pLayoutResId){
        BaseTutoFragment fragment = new BaseTutoFragment();
        Bundle args = new Bundle();

        args.putInt(LAYOUT_RES, pLayoutResId);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutResId = R.layout.fragment_presentation;

        if (getArguments() != null){
            layoutResId = getArguments().getInt(LAYOUT_RES, R.layout.fragment_presentation);
        }
        return inflater.inflate(layoutResId, container, false);
    }
}
