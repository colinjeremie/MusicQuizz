package com.github.colinjeremie.willyoufindit.utils

import junit.framework.Assert

import org.junit.Test

class NormalizeStrTest {

    companion object {
        private const val RESULT_NO_ACCENT = "aeiou"
        private const val STR_ACCENT = "àêiôù"
    }

    @Test
    fun shouldRemoveAccents() {
        Assert.assertEquals(RESULT_NO_ACCENT, STR_ACCENT.normalize())
    }
}
