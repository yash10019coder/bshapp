package com.developer.bshapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.developer.bshapp.API.RetrofitApi
import com.developer.bshapp.R
import com.developer.bshapp.databinding.FragmentAddAdsBinding
import com.developer.bshapp.databinding.FragmentAddJobsBinding
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import java.util.HashMap


class addJobsFragment : Fragment() {
    private var binding: FragmentAddJobsBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddJobsBinding.inflate(inflater, container, false)
        val view = binding!!.root

        binding!!.submitBtn.setOnClickListener {
            verify()
            uploadToDB()
        }

        return view
    }

    private fun verify() {
        if (binding?.etCmpName?.editText.toString().equals(""))
            binding?.etCmpName?.editText?.error="This can't be empty"
        if (binding?.etCmpName?.editText.toString().equals(""))
            binding?.etCmpName?.editText?.error="This can't be empty"
        if (binding?.etCmpName?.editText.toString().equals(""))
            binding?.etCmpName?.editText?.error="This can't be empty"
        if (binding?.etCmpName?.editText.toString().equals(""))
            binding?.etCmpName?.editText?.error="This can't be empty"
        if (binding?.etCmpName?.editText.toString().equals(""))
            binding?.etCmpName?.editText?.error="This can't be empty"
        if (binding?.etCmpName?.editText.toString().equals(""))
            binding?.etCmpName?.editText?.error="This can't be empty"
        if (binding?.etCmpName?.editText.toString().equals(""))
            binding?.etCmpName?.editText?.error="This can't be empty"
        if (binding?.etCmpName?.editText.toString().equals(""))
            binding?.etCmpName?.editText?.error="This can't be empty"
        if (binding?.etCmpName?.editText.toString().equals(""))
            binding?.etCmpName?.editText?.error="This can't be empty"
        if (binding?.etCmpName?.editText.toString().equals(""))
            binding?.etCmpName?.editText?.error="This can't be empty"
        if (binding?.etCmpName?.editText.toString().equals(""))
            binding?.etCmpName?.editText?.error="This can't be empty"

    }

    private fun uploadToDB(
    ) {

        val retrofit = Retrofit.Builder()
            .baseUrl("http://139.59.61.90/main/") // as we are sending data in json format so
            .build()

        // Create Service
        val service = retrofit.create(RetrofitApi::class.java)

        val params = HashMap<String?, String?>()
        params["companyName"] = binding?.etCmpName?.editText?.text.toString()
        params["jobTitle"] = binding?.etjbtitle?.editText?.text.toString()
        params["description"] = binding?.etdis?.editText?.text.toString()
        params["state"] = binding?.etstat?.editText?.text.toString()
        params["city"] = binding?.etstat?.editText?.text.toString()
        params["district"] = binding?.etdis?.editText?.text.toString()
        params["phone"] = binding?.etphn?.editText?.text.toString()
        params["minSalary"] = binding?.etMinsal?.editText?.text.toString()
        params["maxSalary"] = binding?.etmaxsal?.editText?.text.toString()
        params["qualification"] = binding?.etquali?.editText?.text.toString()
        params["experience"] = binding?.etmaxexp?.editText?.text.toString()

        CoroutineScope(Dispatchers.IO).launch {

            // Do the POST request and get response
            val response = service.uploadJob(params)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    view?.let {
                        Navigation.findNavController(it)
                            .navigate(R.id.action_addJobsFragment_to_sentSuccessfullyFragment)
                    }
                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser().parse(
                            response.body()
                                ?.string()
                        )
                    )

                    Log.d("Pretty Printed JSON :", prettyJson)

                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }

    }
}