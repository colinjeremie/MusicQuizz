package com.github.colinjeremie.willyoufindit.activities;

import android.os.Build;
import android.support.v4.app.Fragment;

import com.github.colinjeremie.willyoufindit.BuildConfig;
import com.github.colinjeremie.willyoufindit.R;
import com.github.colinjeremie.willyoufindit.fragments.ChooseACategoryFragment;
import com.github.colinjeremie.willyoufindit.fragments.PresentationFragmentStep3;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class HomeActivityTest {

    private HomeActivity mActivity;

    @Before
    public void setUp(){
        mActivity = Robolectric.buildActivity(HomeActivity.class).create().resume().get();
    }

    @Test
    public void shouldDisplayChooseACategoryFragmentFirst(){
        Fragment fragment = mActivity.getSupportFragmentManager().findFragmentById(R.id.frame_container);

        Assert.assertNotNull(fragment);
        Assert.assertTrue(fragment instanceof ChooseACategoryFragment);
    }

    @Test
    public void testOnSwitchContent() throws Exception {
        mActivity.onSwitchContent(new PresentationFragmentStep3());
        Fragment fragment = mActivity.getSupportFragmentManager().findFragmentById(R.id.frame_container);

        Assert.assertNotNull(fragment);
        Assert.assertTrue(fragment instanceof PresentationFragmentStep3);
    }

    @Test
    public void onSwitchContentShouldAddTheFragmentToTheBackstack() throws Exception{
        mActivity.onSwitchContent(new PresentationFragmentStep3());
        Assert.assertEquals(1, mActivity.getSupportFragmentManager().getBackStackEntryCount());

        mActivity.onSwitchContent(new PresentationFragmentStep3());
        Assert.assertEquals(2, mActivity.getSupportFragmentManager().getBackStackEntryCount());
    }

    @Test
    public void testOnSwitchContentPopBackstack() throws Exception{
        mActivity.onSwitchContent(new PresentationFragmentStep3());
        Assert.assertEquals(1, mActivity.getSupportFragmentManager().getBackStackEntryCount());

        mActivity.onBackPressed();
        Assert.assertEquals(0, mActivity.getSupportFragmentManager().getBackStackEntryCount());
    }
}