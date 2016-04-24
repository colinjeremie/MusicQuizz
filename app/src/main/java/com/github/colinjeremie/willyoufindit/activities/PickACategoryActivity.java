package com.github.colinjeremie.willyoufindit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.colinjeremie.willyoufindit.R;

public class PickACategoryActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_acategory);

        View radioBtn = findViewById(R.id.choose_category_radio_btn);
        View genreBtn = findViewById(R.id.choose_category_genre_btn);
        if (radioBtn != null) {
            radioBtn.setOnClickListener(this);
        } if (genreBtn != null){
            genreBtn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.choose_category_genre_btn){
            startActivity(new Intent(this, GenreActivity.class));
        } else if (v.getId() == R.id.choose_category_radio_btn){
            startActivity(new Intent(this, RadioActivity.class));
        }
    }
}
