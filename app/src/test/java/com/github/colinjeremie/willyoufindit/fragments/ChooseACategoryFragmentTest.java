package com.github.colinjeremie.willyoufindit.fragments;


import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;

import com.github.colinjeremie.willyoufindit.BuildConfig;
import com.github.colinjeremie.willyoufindit.R;
import com.github.colinjeremie.willyoufindit.activities.HomeActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class ChooseACategoryFragmentTest {

    private ChooseACategoryFragment mFragment;

    @Before
    public void setUp(){
        mFragment = new ChooseACategoryFragment();
        SupportFragmentTestUtil.startFragment(mFragment, HomeActivity.class);
    }

    private Fragment clickOnBtnAndDisplayed(@IdRes int pRes){
        Assert.assertNotNull(mFragment.getView());
        mFragment.getView().findViewById(pRes).performClick();
        Assert.assertTrue(!mFragment.isVisible());

        return mFragment.getActivity().getSupportFragmentManager().findFragmentById(R.id.frame_container);
    }

    @Test
    public void shouldDisplayRadioFragmentOnClick() throws Exception{
        Assert.assertTrue(clickOnBtnAndDisplayed(R.id.choose_category_radio_btn) instanceof RadioFragment);
    }

    @Test
    public void shouldDisplayGenreFragmentOnClick() throws Exception{
        Assert.assertTrue(clickOnBtnAndDisplayed(R.id.choose_category_genre_btn) instanceof GenreFragment);
    }

}
