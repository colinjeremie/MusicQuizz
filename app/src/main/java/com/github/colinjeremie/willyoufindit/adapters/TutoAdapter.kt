package com.github.colinjeremie.willyoufindit.adapters

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.github.colinjeremie.willyoufindit.R
import com.github.colinjeremie.willyoufindit.fragments.BaseTutoFragment
import com.github.colinjeremie.willyoufindit.fragments.PresentationFragmentStep3

class TutoAdapter(fragmentManager: FragmentManager?) : FragmentStatePagerAdapter(fragmentManager) {

    companion object {
        const val COUNT = 3
        private const val STEP1_POSITION = 0
        private const val STEP2_POSITION = 1
    }

    override fun getItem(position: Int) =
            when (position) {
                STEP1_POSITION -> BaseTutoFragment.getInstance(R.layout.fragment_presentation_step1)
                STEP2_POSITION -> BaseTutoFragment.getInstance(R.layout.fragment_presentation_step2)
                else -> PresentationFragmentStep3()
            }

    override fun getCount() = COUNT
}