package com.developer.bshapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.developer.bshapp.R


class SentSuccessfullyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_sent_successfully, container, false)
        val gotoHomeBtn=view.findViewById<Button>(R.id.gotoHomeBtn)
        gotoHomeBtn.setOnClickListener {
            activity?.finish()
        }
        return view
    }


}