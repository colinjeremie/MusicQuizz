package com.github.colinjeremie.willyoufindit.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.colinjeremie.willyoufindit.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PresentationFragment2 extends Fragment implements View.OnClickListener {
    private RadioFragment mRadioFragment;
    private GenresFragment mGenresFragment;

    public PresentationFragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_presentation2, container, false);

        rootView.findViewById(R.id.presentation2_radio_btn).setOnClickListener(this);
        rootView.findViewById(R.id.presentation2_genre_btn).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.presentation2_radio_btn:
                if (mRadioFragment == null){
                    mRadioFragment = new RadioFragment();
                }
                switchFragment(mRadioFragment);
                break;
            case R.id.presentation2_genre_btn:
                if (mGenresFragment == null) {
                    mGenresFragment = new GenresFragment();
                }
                switchFragment(mGenresFragment);
                break;
            default:
                break;
        }
    }

    private void switchFragment(Fragment pFragment) {
        getChildFragmentManager().beginTransaction().replace(R.id.frame_container, pFragment).commit();
    }
}
