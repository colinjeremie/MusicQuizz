package com.github.colinjeremie.willyoufindit.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.colinjeremie.willyoufindit.R;

/**
 * The fragment to present the app
 */
public class PresentationFragment extends Fragment {

    public PresentationFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_presentation, container, false);
    }

}
