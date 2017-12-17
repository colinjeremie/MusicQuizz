package com.github.colinjeremie.willyoufindit.activities


import android.os.Build
import com.github.colinjeremie.willyoufindit.BuildConfig
import com.github.colinjeremie.willyoufindit.R
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadow.api.Shadow
import org.robolectric.shadows.ShadowActivity

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = [(Build.VERSION_CODES.LOLLIPOP)])
class PickACategoryActivityTest {

    @Test
    @Throws(Exception::class)
    fun should_display_radio_fragment_on_click() {
        // Given
        val activity = Shadow.extract<ShadowActivity>(Robolectric.buildActivity(PickACategoryActivity::class.java).create().resume().get())

        // When
        activity.findViewById(R.id.choose_category_radio_btn).performClick()

        // Then
        Assert.assertEquals(RadioActivity::class.java.name, activity.nextStartedActivity.component!!.className)
    }

    @Test
    @Throws(Exception::class)
    fun should_display_genre_fragment_on_click() {
        // Given
        val activity = Shadow.extract<ShadowActivity>(Robolectric.buildActivity(PickACategoryActivity::class.java).create().resume().get())

        // When
        activity.findViewById(R.id.choose_category_genre_btn).performClick()

        // Then
        Assert.assertEquals(GenreActivity::class.java.name, activity.nextStartedActivity.component!!.className)
    }
}
