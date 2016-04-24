package com.github.colinjeremie.willyoufindit.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.github.colinjeremie.willyoufindit.R;
import com.github.colinjeremie.willyoufindit.adapters.TutoAdapter;
import com.github.colinjeremie.willyoufindit.utils.pagerindicator.CirclePageIndicator;

public class TutoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuto);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        if (viewPager != null) {
            viewPager.setAdapter(new TutoAdapter(getSupportFragmentManager()));
            viewPager.setOffscreenPageLimit(TutoAdapter.COUNT);
            CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.view_pager_indicator);
            if (indicator != null) {
                indicator.setViewPager(viewPager);
            }
        }
    }
}
