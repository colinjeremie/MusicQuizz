package com.github.colinjeremie.willyoufindit.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * * WillYouFindIt
 * Created by jerem_000 on 4/24/2016.
 */
public class SplashScreenHelper {
    public static final String TUTO = "TUTO";
    public static final String IS_FIRST_TIME_LAUNCHED = "IS_FIRST_TIME_LAUNCHED";

    public static void tutoDone(Context pContext){
        SharedPreferences pref = pContext.getSharedPreferences(TUTO, Context.MODE_PRIVATE);

        pref.edit().putBoolean(IS_FIRST_TIME_LAUNCHED, false).apply();
    }

    public static boolean isFirstTimeLaunched(Context pContext){
        return pContext.getSharedPreferences(TUTO, Context.MODE_PRIVATE).getBoolean(IS_FIRST_TIME_LAUNCHED, true);
    }
}
