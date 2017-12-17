package com.github.colinjeremie.willyoufindit.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

import com.github.colinjeremie.willyoufindit.R
import com.github.colinjeremie.willyoufindit.utils.SplashScreenHelper

class SplashScreenActivity : AppCompatActivity() {

    companion object {
        private const val DELAY_TIME = 2000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        val isFirstTimeLaunched = SplashScreenHelper.isFirstTimeLaunched(this@SplashScreenActivity)
        Handler().postDelayed({
            if (isFirstTimeLaunched) {
                launchTutoActivity()
            } else {
                launchOtherActivity()
            }
        }, DELAY_TIME)
    }

    private fun launchTutoActivity() {
        startActivity(
                Intent(this, TutoActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }

    private fun launchOtherActivity() {
        startActivity(
                Intent(this, PickACategoryActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }
}
