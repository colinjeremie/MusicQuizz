package com.github.colinjeremie.willyoufindit.utils

import junit.framework.Assert

import org.junit.Test

class NormalizeStrTest {
    @Test
    fun should_remove_the_accents() {
        // Given
        val test = "àêiôù"

        // When
        val normalized = test.normalize()

        // Then
        Assert.assertEquals("aeiou", normalized)
    }
}
