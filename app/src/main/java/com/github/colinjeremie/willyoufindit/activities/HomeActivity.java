package com.github.colinjeremie.willyoufindit.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.github.colinjeremie.willyoufindit.R;
import com.github.colinjeremie.willyoufindit.fragments.ChooseACategoryFragment;
import com.github.colinjeremie.willyoufindit.utils.OnSwitchContentListener;

public class HomeActivity extends AppCompatActivity implements OnSwitchContentListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportFragmentManager().beginTransaction().add(R.id.frame_container, new ChooseACategoryFragment()).commit();
    }

    /**
     *
     * @param pFragment Fragment to insert
     */
    @Override
    public void onSwitchContent(Fragment pFragment) {
        if (pFragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.frame_container, pFragment).setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
