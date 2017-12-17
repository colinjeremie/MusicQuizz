package com.github.colinjeremie.willyoufindit.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

import com.github.colinjeremie.willyoufindit.R

class PickACategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_acategory)

        val radioBtn = findViewById<View>(R.id.choose_category_radio_btn)
        val genreBtn = findViewById<View>(R.id.choose_category_genre_btn)

        radioBtn.setOnClickListener { startActivity(Intent(this, RadioActivity::class.java)) }
        genreBtn.setOnClickListener { startActivity(Intent(this, GenreActivity::class.java)) }
    }
}
