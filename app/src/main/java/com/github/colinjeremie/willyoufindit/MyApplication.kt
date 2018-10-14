package com.github.colinjeremie.willyoufindit

import android.app.Application

class MyApplication: Application() {
    companion object {
        lateinit var instance: MyApplication
    }

    val deezerApi: DeezerAPI by lazy { DeezerAPI(this) }

    override fun onCreate() {
        super.onCreate()

        instance = this
    }
}
