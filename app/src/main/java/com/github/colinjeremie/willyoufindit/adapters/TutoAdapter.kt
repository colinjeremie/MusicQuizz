package com.github.colinjeremie.willyoufindit.adapters

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.github.colinjeremie.willyoufindit.R
import com.github.colinjeremie.willyoufindit.fragments.BaseTutoFragment
import com.github.colinjeremie.willyoufindit.fragments.PresentationFragmentStep3

class TutoAdapter(fragmentManager: FragmentManager?) : FragmentStatePagerAdapter(fragmentManager) {

    companion object {
        val COUNT = 3
    }

    override fun getItem(position: Int) =
            when (position) {
                0 -> BaseTutoFragment.getInstance(R.layout.fragment_presentation_step1)
                1 -> BaseTutoFragment.getInstance(R.layout.fragment_presentation_step2)
                else -> PresentationFragmentStep3()
            }

    override fun getCount() = COUNT
}