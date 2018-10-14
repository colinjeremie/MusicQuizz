package com.github.colinjeremie.willyoufindit.activities

import android.os.Build
import android.support.v4.view.ViewPager
import com.github.colinjeremie.willyoufindit.BuildConfig
import com.github.colinjeremie.willyoufindit.R
import com.github.colinjeremie.willyoufindit.adapters.TutoAdapter
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = [(Build.VERSION_CODES.LOLLIPOP)])
class TutorialActivityTest {

    @Test
    @Throws(Exception::class)
    fun should_have_3_pages() {
        // Given
        val activity = Robolectric.setupActivity(TutorialActivity::class.java)
        val viewPager = activity.findViewById<ViewPager>(R.id.view_pager)

        // When
        // Then
        Assert.assertEquals(viewPager.adapter!!.count, TutoAdapter.COUNT)
    }
}