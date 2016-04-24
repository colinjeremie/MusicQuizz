package com.github.colinjeremie.willyoufindit.utils;


import android.os.Build;

import com.deezer.sdk.model.Radio;
import com.github.colinjeremie.willyoufindit.BuildConfig;

import junit.framework.Assert;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class FilterRadioListTaskTest {

    private static final String TEXT_FILTERED = "test1";
    private static final String TEXT_FILTERED_NO_RESULT = "test_no_result";

    @Test
    public void testFilterRadioListTaskWithResults() throws Exception{
        List<Radio> list = new ArrayList<>();
        list.add(new Radio(new JSONObject("{id: 0, title: 'test', description: 'description', picture: ''}")));
        list.add(new Radio(new JSONObject("{id: 1, title: 'test1', description: 'description1', picture: ''}")));
        list.add(new Radio(new JSONObject("{id: 2, title: 'test2', description: 'description2', picture: ''}")));
        list.add(new Radio(new JSONObject("{id: 3, title: 'test3', description: 'description3', picture: ''}")));

        FilterRadioListTask task = new FilterRadioListTask(list, null);

        task.execute(TEXT_FILTERED);
        Robolectric.flushBackgroundThreadScheduler();
        List<Radio> radioList = task.get();

        Assert.assertEquals(1, radioList.size());
        Assert.assertEquals(list.get(1), radioList.get(0));
    }

    @Test
    public void testFilterRadioListTaskWithNoResult() throws Exception{
        List<Radio> list = new ArrayList<>();
        list.add(new Radio(new JSONObject("{id: 0, title: 'test', description: 'description', picture: ''}")));
        list.add(new Radio(new JSONObject("{id: 1, title: 'test1', description: 'description1', picture: ''}")));
        list.add(new Radio(new JSONObject("{id: 2, title: 'test2', description: 'description2', picture: ''}")));
        list.add(new Radio(new JSONObject("{id: 3, title: 'test3', description: 'description3', picture: ''}")));

        FilterRadioListTask task = new FilterRadioListTask(list, null);

        task.execute(TEXT_FILTERED_NO_RESULT);
        Robolectric.flushBackgroundThreadScheduler();
        List<Radio> radioList = task.get();

        Assert.assertEquals(0, radioList.size());
    }
}
