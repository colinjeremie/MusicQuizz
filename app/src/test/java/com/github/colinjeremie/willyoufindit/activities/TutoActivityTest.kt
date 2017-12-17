package com.github.colinjeremie.willyoufindit.activities

import android.os.Build
import android.support.v4.view.ViewPager

import com.github.colinjeremie.willyoufindit.BuildConfig
import com.github.colinjeremie.willyoufindit.R
import com.github.colinjeremie.willyoufindit.adapters.TutoAdapter

import junit.framework.Assert

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(Build.VERSION_CODES.LOLLIPOP))
class TutoActivityTest {

    private lateinit var mViewPager: ViewPager

    @Before
    fun setUp() {
        val activity = Robolectric.setupActivity(TutoActivity::class.java)
        mViewPager = activity.findViewById(R.id.view_pager)
    }

    @Test
    @Throws(Exception::class)
    fun shouldHave3Pages() {
        Assert.assertEquals(mViewPager.adapter!!.count, TutoAdapter.COUNT)
    }
}