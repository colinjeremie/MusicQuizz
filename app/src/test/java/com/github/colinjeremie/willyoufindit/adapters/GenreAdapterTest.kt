package com.github.colinjeremie.willyoufindit.adapters

import com.deezer.sdk.model.Genre
import io.reactivex.subscribers.TestSubscriber
import org.json.JSONObject
import org.junit.Assert
import org.junit.Test

class GenreAdapterTest {
    companion object {
        private const val TEXT_SEARCH = "test"
        private const val TEXT_SEARCH1 = "test1"
        private const val TEXT_SEARCH_NO_RESULT = "NO_RESULTS"
    }

    private val adapter = GenreAdapter {}
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
    fun should_not_filter() {
        // Given
        val testSubscriber = TestSubscriber<MutableList<Genre>>()

        // When
        adapter.getFilterObservable(TEXT_SEARCH, genres).toFlowable().subscribe(testSubscriber)

        // Then
        Assert.assertEquals(5, testSubscriber.values().single().size)
    }

    @Test
    @Throws(Exception::class)
    fun should_filter_2_objects() {
        // Given
        val testSubscriber = TestSubscriber<MutableList<Genre>>()

        // When
        adapter.getFilterObservable(TEXT_SEARCH1, genres).toFlowable().subscribe(testSubscriber)

        // Then
        Assert.assertEquals(2, testSubscriber.values().single().size)
    }

    @Test
    @Throws(Exception::class)
    fun should_all_filter() {
        // Given
        val testSubscriber = TestSubscriber<MutableList<Genre>>()

        // When
        adapter.getFilterObservable(TEXT_SEARCH_NO_RESULT, genres).toFlowable().subscribe(testSubscriber)

        // Then
        Assert.assertEquals(0, testSubscriber.values().single().size)
    }
}
