package com.github.colinjeremie.willyoufindit.fragments;


import android.os.Build;

import com.github.colinjeremie.willyoufindit.BuildConfig;
import com.github.colinjeremie.willyoufindit.R;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class BaseTutoFragmentTest {

    @Test
    public void shouldUseLayoutResIdInTheArguments() throws Exception{
        BaseTutoFragment fragment = BaseTutoFragment.getInstance(R.layout.activity_tuto);
        SupportFragmentTestUtil.startFragment(fragment);

        Assert.assertNotNull(fragment.getView());
        Assert.assertNotNull(fragment.getView().findViewById(R.id.view_pager_indicator));
    }
}
