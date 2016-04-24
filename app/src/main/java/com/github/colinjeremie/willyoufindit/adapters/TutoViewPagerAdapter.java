package com.github.colinjeremie.willyoufindit.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.github.colinjeremie.willyoufindit.R;
import com.github.colinjeremie.willyoufindit.fragments.BaseTutoFragment;
import com.github.colinjeremie.willyoufindit.fragments.PresentationFragment3;

/**
 * * WillYouFindIt
 * Created by jerem_000 on 4/23/2016.
 */
public class TutoViewPagerAdapter extends FragmentStatePagerAdapter {
    public static int COUNT = 3;

    public TutoViewPagerAdapter(FragmentManager pFragmentManager) {
        super(pFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return BaseTutoFragment.getInstance(R.layout.fragment_presentation);
        } else if (position == 1){
            return BaseTutoFragment.getInstance(R.layout.fragment_presentation2);
        }
        return new PresentationFragment3();
    }

    @Override
    public int getCount() {
        return COUNT;
    }
}