package com.github.colinjeremie.willyoufindit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.colinjeremie.willyoufindit.R;
import com.github.colinjeremie.willyoufindit.utils.SplashScreenHelper;

public class SplashScreenActivity extends AppCompatActivity {

    private static final long DELAY_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        final boolean isFirstTimeLaunched = SplashScreenHelper.isFirstTimeLaunched(SplashScreenActivity.this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFirstTimeLaunched){
                    launchTutoActivity();
                } else {
                    launchOtherActivity();
                }
            }
        }, DELAY_TIME);
    }

    private void launchTutoActivity() {
        Intent intent = new Intent(this, TutoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }

    private void launchOtherActivity() {
        Intent intent = new Intent(this, PickACategoryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }

}
