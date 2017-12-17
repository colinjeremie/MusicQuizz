package com.github.colinjeremie.willyoufindit.adapters

import android.os.Build
import com.deezer.sdk.model.Genre
import com.deezer.sdk.network.request.event.JsonRequestListener
import com.github.colinjeremie.willyoufindit.BuildConfig
import junit.framework.Assert
import org.hamcrest.CoreMatchers.any
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(Build.VERSION_CODES.LOLLIPOP))
class GenreAdapterTest {

    @Test
    @Throws(Exception::class)
    fun testCallbackAPI() {
        val genres = mutableListOf<Genre>()
        val adapter = GenreAdapter()

        genres.add(Genre(JSONObject().put("id", 0).put("name", "test")))
        genres.add(Genre(JSONObject().put("id", 1).put("name", "test1")))
        genres.add(Genre(JSONObject().put("id", 2).put("name", "test2")))
        genres.add(Genre(JSONObject().put("id", 3).put("name", "test3")))
        genres.add(Genre(JSONObject().put("id", 4).put("name", "test4")))

        Assert.assertEquals(0, adapter.itemCount)
        (adapter.listener as JsonRequestListener).onResult(genres, any(String.javaClass))
        Assert.assertEquals(genres.size, adapter.itemCount)
    }
}
