package com.github.colinjeremie.willyoufindit.fragments

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.github.colinjeremie.willyoufindit.R

class BaseTutoFragment : Fragment() {

    companion object {
        const val LAYOUT_RES = "LAYOUT_RES"

        fun getInstance(@LayoutRes layoutRes: Int): BaseTutoFragment {
            val arguments = Bundle().apply { putInt(LAYOUT_RES, layoutRes) }

            return BaseTutoFragment().apply {
                this.arguments = arguments
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layoutResId = arguments?.getInt(LAYOUT_RES, R.layout.fragment_presentation_step1) ?: R.layout.fragment_presentation_step1

        return inflater.inflate(layoutResId, container, false)
    }
}
