package com.github.colinjeremie.willyoufindit.adapters


import android.os.Build
import com.github.colinjeremie.willyoufindit.BuildConfig
import com.github.colinjeremie.willyoufindit.R
import com.github.colinjeremie.willyoufindit.fragments.BaseTutoFragment
import com.github.colinjeremie.willyoufindit.fragments.PresentationFragmentStep3
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(Build.VERSION_CODES.LOLLIPOP))
class TutoAdapterTest {

    @Test
    @Throws(Exception::class)
    fun shouldDisplayRightLayout() {
        val tutoAdapter = TutoAdapter(null)

        Assert.assertEquals(tutoAdapter.getItem(0).arguments!!.getInt(BaseTutoFragment.LAYOUT_RES).toLong(), R.layout.fragment_presentation_step1.toLong())
        Assert.assertEquals(tutoAdapter.getItem(1).arguments!!.getInt(BaseTutoFragment.LAYOUT_RES).toLong(), R.layout.fragment_presentation_step2.toLong())
        Assert.assertTrue(tutoAdapter.getItem(2) is PresentationFragmentStep3)
    }
}
