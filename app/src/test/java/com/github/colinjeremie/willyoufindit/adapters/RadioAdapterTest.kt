package com.github.colinjeremie.willyoufindit.adapters

import android.os.Build
import com.deezer.sdk.model.Radio
import com.github.colinjeremie.willyoufindit.BuildConfig
import io.reactivex.subscribers.TestSubscriber
import junit.framework.Assert
import org.hamcrest.Matchers.any
import org.json.JSONException
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(Build.VERSION_CODES.LOLLIPOP))
class RadioAdapterTest {
    companion object {
        private const val TEXT_SEARCH = "test"
        private const val TEXT_SEARCH1 = "test1"
        private const val TEXT_SEARCH_NO_RESULT = "NO_RESULTS"
    }

    private val radios by lazy { initDummyData() }
    private val adapter: RadioAdapter = RadioAdapter()

    private fun initDummyData(): MutableList<Radio> {
        val list = mutableListOf<Radio>()

        try {
            list.add(Radio(JSONObject().put("id", 0).put("title", "test")))
            list.add(Radio(JSONObject().put("id", 1).put("title", "test1")))
            list.add(Radio(JSONObject().put("id", 2).put("title", "test2")))
            list.add(Radio(JSONObject().put("id", 3).put("title", "test3")))
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return list
    }

    @Test
    @Throws(Exception::class)
    fun testCallbackAPI() {
        Assert.assertEquals(0, adapter.itemCount)
        adapter.listener.onResult(radios, any(Any::class.java))
        Assert.assertEquals(radios.size, adapter.itemCount)
    }

    @Test
    @Throws(Exception::class)
    fun testFilter() {
        var testSubscriber = TestSubscriber<MutableList<Radio>>()

        adapter.getFilterObservable(TEXT_SEARCH, radios).toFlowable().subscribe(testSubscriber)
        Assert.assertEquals(4, testSubscriber.values().single().size)

        testSubscriber = TestSubscriber()
        adapter.getFilterObservable(TEXT_SEARCH1, radios).toFlowable().subscribe(testSubscriber)
        Assert.assertEquals(1, testSubscriber.values().single().size)

        testSubscriber = TestSubscriber()
        adapter.getFilterObservable(TEXT_SEARCH_NO_RESULT, radios).toFlowable().subscribe(testSubscriber)
        Assert.assertEquals(0, testSubscriber.values().single().size)
    }
}
