package com.github.colinjeremie.willyoufindit.activities


import android.os.Build

import com.github.colinjeremie.willyoufindit.BuildConfig
import com.github.colinjeremie.willyoufindit.R

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadow.api.Shadow
import org.robolectric.shadows.ShadowActivity

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(Build.VERSION_CODES.LOLLIPOP))
class PickACategoryActivityTest {

    private lateinit var activity: ShadowActivity

    @Before
    fun setUp() {
        activity = Shadow.extract<ShadowActivity>(Robolectric.buildActivity(PickACategoryActivity::class.java).create().resume().get())
    }

    @Test
    @Throws(Exception::class)
    fun shouldDisplayRadioFragmentOnClick() {
        activity.findViewById(R.id.choose_category_radio_btn).performClick()
        Assert.assertEquals(RadioActivity::class.java.name, activity.nextStartedActivity.component!!.className)
    }

    @Test
    @Throws(Exception::class)
    fun shouldDisplayGenreFragmentOnClick() {
        activity.findViewById(R.id.choose_category_genre_btn).performClick()
        Assert.assertEquals(GenreActivity::class.java.name, activity.nextStartedActivity.component!!.className)
    }
}
