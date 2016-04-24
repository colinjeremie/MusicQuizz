package com.github.colinjeremie.willyoufindit.adapters;

import android.os.Build;

import com.deezer.sdk.model.Radio;
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
public class RadioAdapterTest {

    private static final String TEXT_SEARCH = "test";
    private static final String TEXT_SEARCH1 = "test1";
    private static final String TEXT_SEARCH_NO_RESULT = "NO_RESULTS";

    private RadioAdapter mAdapter;

    static private List<Radio> _Radios = initDummyData();

    private static List<Radio> initDummyData() {
        List<Radio> list = new ArrayList<>();

        try {
            list.add(new Radio(new JSONObject("{id: 0, title: 'test', description: 'description', picture: ''}")));
            list.add(new Radio(new JSONObject("{id: 1, title: 'test1', description: 'description1', picture: ''}")));
            list.add(new Radio(new JSONObject("{id: 2, title: 'test2', description: 'description2', picture: ''}")));
            list.add(new Radio(new JSONObject("{id: 3, title: 'test3', description: 'description3', picture: ''}")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Before
    public void setUp(){
        mAdapter = new RadioAdapter();
    }

    @Test
    public void testCallbackAPI() throws Exception{
        Assert.assertEquals(0, mAdapter.getItemCount());
        mAdapter.mListener.onResult(_Radios, null);
        Assert.assertEquals(_Radios.size(), mAdapter.getItemCount());
    }

    @Test
    public void testClearDoubles() throws Exception{
        int size = _Radios.size();
        _Radios.add(new Radio(new JSONObject("{id: 0, title: 'test', description: 'description', picture: ''}")));
        mAdapter.setOriginalDataSet(_Radios);
        Assert.assertEquals(size + 1, mAdapter.getOriginalDataSet().size());
        mAdapter.clearDoubles();

        Assert.assertEquals(size, mAdapter.getOriginalDataSet().size());
    }

    @Test
    public void testFilter() throws Exception{
        mAdapter.mListener.onResult(_Radios, null);
        mAdapter.filter(TEXT_SEARCH);

        Assert.assertEquals(_Radios.size(), mAdapter.getItemCount());
        mAdapter.filter(TEXT_SEARCH1);
        Assert.assertEquals(1, mAdapter.getItemCount());

        mAdapter.filter(TEXT_SEARCH_NO_RESULT);
        Assert.assertEquals(0, mAdapter.getItemCount());
    }

    @Test
    public void testClearFilter() throws Exception{
        mAdapter.mListener.onResult(_Radios, null);
        mAdapter.filter(TEXT_SEARCH1);
        Assert.assertEquals(1, mAdapter.getItemCount());

        mAdapter.clearFilter();
        Assert.assertEquals(_Radios.size(), mAdapter.getItemCount());
    }
}
