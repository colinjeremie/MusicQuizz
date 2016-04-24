package com.github.colinjeremie.willyoufindit.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deezer.sdk.model.Genre;
import com.deezer.sdk.model.Radio;
import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.github.colinjeremie.willyoufindit.DeezerAPI;
import com.github.colinjeremie.willyoufindit.R;
import com.github.colinjeremie.willyoufindit.adapters.GenreAdapter;
import com.github.colinjeremie.willyoufindit.utils.OnSwitchContentListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenreFragment extends Fragment implements GenreAdapter.OnGenreItemClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genres, container, false);

        RecyclerView genresView = (RecyclerView) view.findViewById(R.id.recycler_view);
        genresView.setLayoutManager(new LinearLayoutManager(getActivity()));
        GenreAdapter adapter = new GenreAdapter();
        genresView.setAdapter(adapter);
        adapter.setOnGenreItemClick(this);

        adapter.init(getActivity());

        return view;
    }

    private final JsonRequestListener mGenreRadioListener = new JsonRequestListener() {

        @SuppressWarnings("unchecked")
        @Override
        public void onResult(Object o, Object o1) {
            List<Radio> radios = (List<Radio>) o;
            int size = radios.size();
            int position = new Random().nextInt(size);
            position %= size;

            Radio radio = radios.get(position);
            requestTrackFromRadio(radio);
        }

        @Override
        public void onUnparsedResult(String s, Object o) {

        }

        @Override
        public void onException(Exception e, Object o) {

        }
    };

    private final JsonRequestListener mTrackListener = new JsonRequestListener() {

        @SuppressWarnings("unchecked")
        @Override
        public void onResult(Object o, Object o1) {
            List<Track> tracks = (List<Track>) o;
            Fragment fragment = new PlayGameFragment();
            Bundle args = new Bundle();
            args.putParcelableArrayList(PlayGameFragment.LIST_TRACKS, new ArrayList<Parcelable>(tracks));
            fragment.setArguments(args);
            ((OnSwitchContentListener) getActivity()).onSwitchContent(fragment);
        }

        @Override
        public void onUnparsedResult(String s, Object o) {

        }

        @Override
        public void onException(Exception e, Object o) {

        }
    };

    private void requestTrackFromRadio(Radio pRadio){
        DeezerAPI.getInstance(getContext()).getRadioTracks(pRadio.getId(), mTrackListener);
    }

    @Override
    public void onGenreItemClick(Genre pGenre) {
        DeezerAPI.getInstance(getContext()).getGenreRadios(pGenre.getId(), mGenreRadioListener);
    }
}
