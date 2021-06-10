package com.developer.bshapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import com.developer.bshapp.R

class ChooseStateFragment : Fragment() {
    private lateinit var tamilNaduState:CardView
    private lateinit var telanganaState:CardView
    private lateinit var apState:CardView
    private lateinit var karnatakaState:CardView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_choose_state, container, false)
        initializations(view)
        OnCardClickHandler(view)
        return view
    }

    private fun OnCardClickHandler(view: View) {
        tamilNaduState.setOnClickListener {
            val action=ChooseStateFragmentDirections.actionChooseStateFragmentToOverViewLocationSelectFragment("tamil")
            Navigation.findNavController(view).navigate(action)
        }
        telanganaState.setOnClickListener {
            val action=ChooseStateFragmentDirections.actionChooseStateFragmentToOverViewLocationSelectFragment("telugu")
            Navigation.findNavController(view).navigate(action)
        }
        apState.setOnClickListener {
            val action=ChooseStateFragmentDirections.actionChooseStateFragmentToOverViewLocationSelectFragment("teluguAp")
            Navigation.findNavController(view).navigate(action)
        }
        karnatakaState.setOnClickListener {
            val action=ChooseStateFragmentDirections.actionChooseStateFragmentToOverViewLocationSelectFragment("kannada")
            Navigation.findNavController(view).navigate(action)
        }

    }

    private fun initializations(view: View) {
        tamilNaduState=view.findViewById(R.id.tamilnadu_Card)
        telanganaState=view.findViewById(R.id.hyderabad_card)
        apState=view.findViewById(R.id.andrapradesh_card)
        karnatakaState=view.findViewById(R.id.karnataka_card)
    }


}