package com.github.colinjeremie.willyoufindit.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.github.colinjeremie.willyoufindit.R;
import com.github.colinjeremie.willyoufindit.adapters.TutoViewPagerAdapter;
import com.github.colinjeremie.willyoufindit.utils.pagerindicator.CirclePageIndicator;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new TutoViewPagerAdapter(getSupportFragmentManager()));
        mViewPager.setOffscreenPageLimit(TutoViewPagerAdapter.COUNT);

        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.view_pager_indicator);
        if (indicator != null) {
            indicator.setViewPager(mViewPager);
        }
    }
}
