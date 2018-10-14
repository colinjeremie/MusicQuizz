package com.github.colinjeremie.willyoufindit.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.github.colinjeremie.willyoufindit.R
import com.github.colinjeremie.willyoufindit.activities.PickACategoryActivity
import com.github.colinjeremie.willyoufindit.utils.SplashScreenHelper

class PresentationFragmentStep3 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_presentation_step3, container, false)

        rootView.findViewById<View>(R.id.letsplay_btn).setOnClickListener {
            SplashScreenHelper.tutorialDone(rootView.context)
            val intent = Intent(activity, PickACategoryActivity::class.java)

            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        return rootView
    }
}
