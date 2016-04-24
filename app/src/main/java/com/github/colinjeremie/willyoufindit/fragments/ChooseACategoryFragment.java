package com.github.colinjeremie.willyoufindit.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.colinjeremie.willyoufindit.R;
import com.github.colinjeremie.willyoufindit.utils.OnSwitchContentListener;


public class ChooseACategoryFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_a_category, container, false);

        view.findViewById(R.id.choose_category_radio_btn).setOnClickListener(this);
        view.findViewById(R.id.choose_category_genre_btn).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.choose_category_genre_btn){
            swichToFragment(new GenreFragment());
        } else if (v.getId() == R.id.choose_category_radio_btn){
            swichToFragment(new RadioFragment());
        }
    }

    private void swichToFragment(Fragment pFragment) {
        ((OnSwitchContentListener) getActivity()).onSwitchContent(pFragment);
    }
}
