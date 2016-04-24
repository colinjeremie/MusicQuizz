package com.github.colinjeremie.willyoufindit.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.colinjeremie.willyoufindit.R;
import com.github.colinjeremie.willyoufindit.activities.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PresentationFragment3 extends Fragment {
    public PresentationFragment3() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_presentation3, container, false);

        rootView.findViewById(R.id.letsplay_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });

        return rootView;
    }
}
