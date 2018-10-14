package com.github.colinjeremie.willyoufindit.fragments

import android.os.Build
import android.view.View
import com.github.colinjeremie.willyoufindit.BuildConfig
import com.github.colinjeremie.willyoufindit.R
import com.github.colinjeremie.willyoufindit.activities.PickACategoryActivity
import com.github.colinjeremie.willyoufindit.activities.TutorialActivity
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadow.api.Shadow
import org.robolectric.shadows.ShadowActivity
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = [(Build.VERSION_CODES.LOLLIPOP)])
class PresentationFragmentStep3Test {

    @Test
    @Throws(Exception::class)
    fun should_start_next_activity_on_click() {
        // Given
        val fragment = PresentationFragmentStep3()
        SupportFragmentTestUtil.startFragment(fragment, TutorialActivity::class.java)
        val activity = Shadow.extract<ShadowActivity>(fragment.activity)
        Assert.assertNotNull(fragment.view)

        // When
        fragment.view!!.findViewById<View>(R.id.letsplay_btn).performClick()

        // Then
        Assert.assertEquals(activity.nextStartedActivity.component!!.className, PickACategoryActivity::class.java.name)
    }
}
