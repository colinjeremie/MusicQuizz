package com.github.colinjeremie.willyoufindit.adapters;

import android.os.Build;

import com.deezer.sdk.model.Genre;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.github.colinjeremie.willyoufindit.BuildConfig;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class GenreAdapterTest {

    private GenreAdapter mAdapter;

    static private List<Genre> _genres = initDummyData();

    private static List<Genre> initDummyData() {
        List<Genre> list = new ArrayList<>();

        try {
            list.add(new Genre(new JSONObject("{id: 0, name: 'test'}")));
            list.add(new Genre(new JSONObject("{id: 1, name: 'test1'}")));
            list.add(new Genre(new JSONObject("{id: 2, name: 'test2'}")));
            list.add(new Genre(new JSONObject("{id: 3, name: 'test3'}")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Before
    public void setUp(){
        mAdapter = new GenreAdapter();
    }

    @Test
    public void testCallbackAPI() throws Exception{
        Assert.assertEquals(0, mAdapter.getItemCount());
        ((JsonRequestListener) mAdapter.mListener).onResult(_genres, null);
        Assert.assertEquals(_genres.size(), mAdapter.getItemCount());
    }
}
