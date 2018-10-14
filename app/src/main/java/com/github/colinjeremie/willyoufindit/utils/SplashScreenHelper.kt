package com.github.colinjeremie.willyoufindit.utils

import android.content.Context

object SplashScreenHelper {
    private const val TUTO = "TUTO"
    private const val IS_FIRST_TIME_LAUNCHED = "IS_FIRST_TIME_LAUNCHED"

    fun tutorialDone(pContext: Context) {
        val pref = pContext.getSharedPreferences(TUTO, Context.MODE_PRIVATE)

        pref.edit().putBoolean(IS_FIRST_TIME_LAUNCHED, false).apply()
    }

    fun isFirstTimeLaunched(context: Context) =
            context.getSharedPreferences(TUTO, Context.MODE_PRIVATE).getBoolean(IS_FIRST_TIME_LAUNCHED, true)
}
