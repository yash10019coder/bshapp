package com.developer.bshapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.navigation.Navigation
import com.developer.bshapp.API.RetrofitApi
import com.developer.bshapp.R
import com.developer.bshapp.databinding.FragmentAddMatrimonyBinding
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit


class AddMatrimonyFragment : Fragment() {
    lateinit var binding: FragmentAddMatrimonyBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddMatrimonyBinding.inflate(inflater, container, false)
        var view = binding.root

        binding!!.submitBtn.setOnClickListener {
            verify()
        }

        return view
    }

    private fun verify() {
        if (binding?.etprofilename?.isEmpty() == true)
            binding?.etprofilename?.editText?.error = "This can't be empty"
        else if (binding?.etgender?.isEmpty() == true)
            binding?.etgender?.editText?.error = "This can't be empty"
        else if (binding?.etdob?.isEmpty() == true)
            binding?.etdob?.editText?.error = "This can't be empty"
        else if (binding?.etheight?.isEmpty() == true)
            binding?.etheight?.editText?.error = "This can't be empty"
        else if (binding?.etstat?.isEmpty() == true)
            binding?.etnativeplace?.editText?.error = "This can't be empty"
        else if (binding?.etresidence?.isEmpty() == true)
            binding?.etresidence?.editText?.error = "This can't be empty"
        else if (binding?.etmothertongue?.isEmpty() == true)
            binding?.etmothertongue?.editText?.error = "This can't be empty"
        else if (binding?.etcaste?.isEmpty() == true)
            binding?.etcaste?.editText?.error = "This can't be empty"
        else if (binding?.ethighestdegree?.isEmpty() == true)
            binding?.ethighestdegree?.editText?.error = "This can't be empty"
        else if (binding?.etcollege?.isEmpty() == true)
            binding?.etcollege?.editText?.error = "This can't be empty"
        else if (binding?.etjobsector?.isEmpty() == true)
            binding?.etjobsector?.editText?.error = "This can't be empty"
        else if (binding?.etjob?.isEmpty() == true)
            binding?.etjob?.editText?.error = "This can't be empty"
        else if (binding?.etsalary?.isEmpty() == true)
            binding?.etsalary?.editText?.error = "This can't be empty"
        else if (binding?.etfoodhabits?.isEmpty() == true)
            binding?.etfoodhabits?.editText?.error = "This can't be empty"
        else if (binding?.etdivyangulu?.isEmpty() == true)
            binding?.etdivyangulu?.editText?.error = "This can't be empty"
        else if (binding?.etcollege?.isEmpty() == true)
            binding?.etcollege?.editText?.error = "This can't be empty"
        else if (binding?.etmarriagestatus?.isEmpty() == true)
            binding?.etmarriagestatus?.editText?.error = "This can't be empty"
        else if (binding?.etotherdetails?.isEmpty() == true)
            binding?.etotherdetails?.editText?.error = "This can't be empty"
        else if (binding?.etcontact?.isEmpty() == true)
            binding?.etcontact?.editText?.error = "This can't be empty"
        else if (binding?.etwhatsapp?.isEmpty() == true)
            binding?.etwhatsapp?.editText?.error = "This can't be empty"
        else {
            uploadToDB()
        }
//        if (binding?.et?.editText.toString().equals(""))
//            binding?.et?.editText?.error="This can't be empty"
    }

    private fun uploadToDB() {

        val retrofit = Retrofit.Builder()
            .baseUrl("http://139.59.61.90/main/") // as we are sending data in json format so
            .build()

        // Create Service
        val service = retrofit.create(RetrofitApi::class.java)

        val params = HashMap<String?, String?>()
        params["name"] = binding?.etprofilename?.editText?.text.toString()
        params["gender"] = binding?.etgender?.editText?.text.toString()
        params["dob"] = binding?.etdob?.editText?.text.toString()
        params["height"] = binding?.etheight?.editText?.text.toString()
        params["state"] = binding?.etstat?.editText?.text.toString()
        params["district"] = binding?.etresidence?.editText?.text.toString()
        params["city"] = binding?.etnativeplace?.editText?.text.toString()
        params["language"] = binding?.etmothertongue?.editText?.text.toString()
        params["caste"] = binding?.etcaste?.editText?.text.toString()
        params["education"] = binding?.ethighestdegree?.editText?.text.toString()
        params["college"] = binding?.etcollege?.editText?.text.toString()
        params["job"] = binding?.etjob?.editText?.text.toString()
        params["salary"] = binding?.etsalary?.editText?.text.toString()
        params["food"] = binding?.etfoodhabits?.editText?.text.toString()
        params["handyCaped"] = binding?.etprofilename?.editText?.text.toString()
        params["martialStatus"] = binding?.etmarriagestatus?.editText?.text.toString()
        params["about"] = binding?.etotherdetails?.editText?.text.toString()
        params["mobileNumber"] = binding?.etcontact?.editText?.text.toString()
        params["whatsApp"] = binding?.etwhatsapp?.editText?.text.toString()

        CoroutineScope(Dispatchers.IO).launch {

            // Do the POST request and get response
            val response = service.uploadMatrimony(params)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser().parse(
                            response.body()
                                ?.string()
                        )
                    )
                    view?.let {
                        Navigation.findNavController(it)
                            .navigate(R.id.action_addMatrimonyFragment_to_sentSuccessfullyFragment)
                    }
                    Log.d("Pretty Printed JSON :", prettyJson)

                } else {
                    view?.let {
                        Navigation.findNavController(it)
                            .navigate(R.id.action_addMatrimonyFragment_to_sentFailedFragment)
                    }
                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }

    }

}