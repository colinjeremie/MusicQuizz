package com.github.colinjeremie.willyoufindit.activities;

import android.os.Build;
import android.support.v4.view.ViewPager;

import com.github.colinjeremie.willyoufindit.BuildConfig;
import com.github.colinjeremie.willyoufindit.R;
import com.github.colinjeremie.willyoufindit.adapters.TutoAdapter;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class TutoActivityTest {

    private ViewPager mViewPager;

    @Before
    public void setUp(){
        TutoActivity activity = Robolectric.setupActivity(TutoActivity.class);
        mViewPager = (ViewPager) activity.findViewById(R.id.view_pager);
    }

    @Test
    public void shouldHave3Pages() throws Exception {
        Assert.assertEquals(mViewPager.getAdapter().getCount(), TutoAdapter.COUNT);
    }
}