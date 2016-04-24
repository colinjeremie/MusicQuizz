package com.github.colinjeremie.willyoufindit.adapters;


import android.os.Build;

import com.github.colinjeremie.willyoufindit.BuildConfig;
import com.github.colinjeremie.willyoufindit.R;
import com.github.colinjeremie.willyoufindit.fragments.BaseTutoFragment;
import com.github.colinjeremie.willyoufindit.fragments.PresentationFragmentStep3;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class TutoAdapterTest {

    @Test
    public void shouldDisplayRightLayout() throws Exception{
        TutoAdapter tutoAdapter = new TutoAdapter(null);

        Assert.assertEquals(tutoAdapter.getItem(0).getArguments().getInt(BaseTutoFragment.LAYOUT_RES), R.layout.fragment_presentation_step1);
        Assert.assertEquals(tutoAdapter.getItem(1).getArguments().getInt(BaseTutoFragment.LAYOUT_RES), R.layout.fragment_presentation_step2);
        Assert.assertTrue(tutoAdapter.getItem(2) instanceof PresentationFragmentStep3);
    }
}
