package com.github.colinjeremie.willyoufindit.activities;


import android.os.Build;

import com.github.colinjeremie.willyoufindit.BuildConfig;
import com.github.colinjeremie.willyoufindit.R;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.internal.ShadowExtractor;
import org.robolectric.shadows.ShadowActivity;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class PickACategoryActivityTest {

    private ShadowActivity mActivity;

    @Before
    public void setUp(){
        mActivity = (ShadowActivity) ShadowExtractor.extract(Robolectric.buildActivity(PickACategoryActivity.class).create().resume().get());
    }

    @Test
    public void shouldDisplayRadioFragmentOnClick() throws Exception{
        mActivity.findViewById(R.id.choose_category_radio_btn).performClick();
        Assert.assertEquals(RadioActivity.class.getName(), mActivity.getNextStartedActivity().getComponent().getClassName());
    }

    @Test
    public void shouldDisplayGenreFragmentOnClick() throws Exception{
        mActivity.findViewById(R.id.choose_category_genre_btn).performClick();
        Assert.assertEquals(GenreActivity.class.getName(), mActivity.getNextStartedActivity().getComponent().getClassName());
    }
}
