package com.github.colinjeremie.willyoufindit.fragments;

import android.os.Build;
import android.support.v4.app.Fragment;

import com.github.colinjeremie.willyoufindit.BuildConfig;
import com.github.colinjeremie.willyoufindit.R;
import com.github.colinjeremie.willyoufindit.activities.PickACategoryActivity;
import com.github.colinjeremie.willyoufindit.activities.TutoActivity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class PresentationFragmentStep3Test {

    @Test
    public void shouldStartNextActivity() throws Exception{
        Fragment fragment = new PresentationFragmentStep3();
        SupportFragmentTestUtil.startFragment(fragment, TutoActivity.class);
        ShadowActivity activity = Shadow.extract(fragment.getActivity());

        Assert.assertNotNull(fragment.getView());
        fragment.getView().findViewById(R.id.letsplay_btn).performClick();
        Assert.assertEquals(activity.getNextStartedActivity().getComponent().getClassName(), PickACategoryActivity.class.getName());
    }
}
