package com.github.colinjeremie.willyoufindit.adapters

import android.os.Build
import com.deezer.sdk.model.Genre
import com.deezer.sdk.network.request.event.JsonRequestListener
import com.github.colinjeremie.willyoufindit.BuildConfig
import io.reactivex.subscribers.TestSubscriber
import junit.framework.Assert
import org.hamcrest.CoreMatchers.any
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = [(Build.VERSION_CODES.LOLLIPOP)])
class GenreAdapterTest {
    companion object {
        private const val TEXT_SEARCH = "test"
        private const val TEXT_SEARCH1 = "test1"
        private const val TEXT_SEARCH_NO_RESULT = "NO_RESULTS"
    }

    private val adapter = GenreAdapter()
    private val genres = createGenres()

    private fun createGenres(): MutableList<Genre> {
        val genres = mutableListOf<Genre>()

        genres.add(Genre(JSONObject().put("id", 0).put("name", "test")))
        genres.add(Genre(JSONObject().put("id", 1).put("name", "test1")))
        genres.add(Genre(JSONObject().put("id", 2).put("name", "test2")))
        genres.add(Genre(JSONObject().put("id", 3).put("name", "test13")))
        genres.add(Genre(JSONObject().put("id", 4).put("name", "test4")))

        return genres
    }

    @Test
    @Throws(Exception::class)
    fun testCallbackAPI() {
        Assert.assertEquals(0, adapter.itemCount)
        (adapter.listener as JsonRequestListener).onResult(genres, any(Any::class.java))
        Assert.assertEquals(genres.size, adapter.itemCount)
    }

    @Test
    @Throws(Exception::class)
    fun testFilter() {
        var testSubscriber = TestSubscriber<MutableList<Genre>>()

        adapter.getFilterObservable(TEXT_SEARCH, genres).toFlowable().subscribe(testSubscriber)
        Assert.assertEquals(5, testSubscriber.values().single().size)

        testSubscriber = TestSubscriber()
        adapter.getFilterObservable(TEXT_SEARCH1, genres).toFlowable().subscribe(testSubscriber)
        Assert.assertEquals(2, testSubscriber.values().single().size)

        testSubscriber = TestSubscriber()
        adapter.getFilterObservable(TEXT_SEARCH_NO_RESULT, genres).toFlowable().subscribe(testSubscriber)
        Assert.assertEquals(0, testSubscriber.values().single().size)
    }
}
