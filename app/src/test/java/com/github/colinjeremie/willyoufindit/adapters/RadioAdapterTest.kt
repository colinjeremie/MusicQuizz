package com.github.colinjeremie.willyoufindit.adapters

import com.deezer.sdk.model.Radio
import io.reactivex.subscribers.TestSubscriber
import org.json.JSONException
import org.json.JSONObject
import org.junit.Assert
import org.junit.Test


class RadioAdapterTest {
    companion object {
        private const val TEXT_SEARCH = "test"
        private const val TEXT_SEARCH1 = "test1"
        private const val TEXT_SEARCH_NO_RESULT = "NO_RESULTS"
    }

    private val radios by lazy { initDummyData() }
    private val adapter: RadioAdapter = RadioAdapter {}

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
    fun should_filter_4_objects() {
        // Given
        val testSubscriber = TestSubscriber<MutableList<Radio>>()

        // When
        adapter.getFilterObservable(TEXT_SEARCH, radios).toFlowable().subscribe(testSubscriber)

        // Then
        Assert.assertEquals(4, testSubscriber.values().single().size)

    }
    @Test
    @Throws(Exception::class)
    fun should_filter_1_object() {
        // Given
        val testSubscriber = TestSubscriber<MutableList<Radio>>()

        // When
        adapter.getFilterObservable(TEXT_SEARCH1, radios).toFlowable().subscribe(testSubscriber)

        // Then
        Assert.assertEquals(1, testSubscriber.values().single().size)
    }

    @Test
    @Throws(Exception::class)
    fun should_all_filter() {
        // Given
        val testSubscriber = TestSubscriber<MutableList<Radio>>()

        // When
        adapter.getFilterObservable(TEXT_SEARCH_NO_RESULT, radios).toFlowable().subscribe(testSubscriber)

        // Then
        Assert.assertEquals(0, testSubscriber.values().single().size)
    }
}
