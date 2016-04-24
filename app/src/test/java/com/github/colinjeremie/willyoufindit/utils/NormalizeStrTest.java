package com.github.colinjeremie.willyoufindit.utils;

import android.os.Build;

import com.github.colinjeremie.willyoufindit.BuildConfig;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class NormalizeStrTest {

    private static final String RESULT_NO_ACCENT = "aeiou";
    private static final String STR_ACCENT = "àêiôù";

    @Test
    public void shouldRemoveAccents() throws Exception{
        Assert.assertEquals(RESULT_NO_ACCENT, NormalizeStr.normalizeIt(STR_ACCENT));
    }

    @Test
    public void shouldReturnTheSame() throws Exception{
        Assert.assertEquals(RESULT_NO_ACCENT, NormalizeStr.normalizeIt(RESULT_NO_ACCENT));
    }
}
