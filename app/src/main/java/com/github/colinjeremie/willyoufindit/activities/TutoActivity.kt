package com.github.colinjeremie.willyoufindit.activities

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity

import com.github.colinjeremie.willyoufindit.R
import com.github.colinjeremie.willyoufindit.adapters.TutoAdapter
import com.github.colinjeremie.willyoufindit.utils.pagerindicator.CirclePageIndicator

class TutoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tuto)

        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        val indicator = findViewById<CirclePageIndicator>(R.id.view_pager_indicator)

        viewPager.adapter = TutoAdapter(supportFragmentManager)
        viewPager.offscreenPageLimit = TutoAdapter.COUNT
        indicator.setViewPager(viewPager)
    }
}
