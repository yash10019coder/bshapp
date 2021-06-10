package com.developer.bshapp.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.developer.bshapp.R
import com.developer.bshapp.adapter.JobRecyclerAdapter
import com.developer.bshapp.adapter.RealestateRecyclerAdapter
import com.developer.bshapp.model.JobsNewsModel
import com.developer.bshapp.model.RealstateNewsModel
import org.json.JSONArray
import java.util.ArrayList

class RealEstateFragment : Fragment() {

    private lateinit var list: ArrayList<RealstateNewsModel>
    lateinit var model: RealstateNewsModel
    private lateinit var realestateRecycler: RecyclerView
    lateinit var adapter: RealestateRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=  inflater.inflate(R.layout.fragment_real_estate, container, false)
        setupSlider(view)
        getdata()
        list = ArrayList<RealstateNewsModel>()
        realestateRecycler = view.findViewById<RecyclerView>(R.id.realestaterecycler)
        realestateRecycler.layoutManager = LinearLayoutManager(requireContext())

        adapter = RealestateRecyclerAdapter(list, requireContext())
        realestateRecycler.adapter = adapter

        return view
    }
    private fun getdata() {


        val queue = Volley.newRequestQueue(requireContext())
        val url: String = "http://139.59.61.90/main/api/getData.php?s=VerifiedRealEstate"

        // Request a string response from the provided URL.
        val stringReq = StringRequest(Request.Method.GET, url,
                { response ->

                    try {

//                    val respObj = JSONObject(response)
//                    val success = respObj.getInt("status")
//                    val jsonArray = respObj.getJSONArray("data")
                        val jsonArray= JSONArray(response)


                        for (i in 0 until jsonArray.length()) {
                            Log.d("Hello", "hello")
                            val item = jsonArray.getJSONObject(i)
                            val owner = item.getString("owner")
                            val propertyType = item.getString("propertyType")
                            val transactionType = item.getString("transactionType")
                            val space = item.getString("space")
                            val price = item.getString("price")
                            val address = item.getString("address")
                            val contact = item.getString("contact")
                            val details = item.getString("details")
                            val timestamp = item.getString("timestamp")
                            val date = item.getString("date")
                            val imageName = item.getString("imageName")


                            model = RealstateNewsModel(
                                    id,
                                    owner,
                                    propertyType,
                                    transactionType,
                                    space,
                                    price,address,
                                    contact,
                                    details,
                                    timestamp,
                                    imageName,
                                    date
                            )
                            list.add(model)


                        }
                        Log.d("Hello", list.size.toString())
                        adapter.setData(list)


                    } catch (e: Exception) {
                        Log.d("Job", e.toString())
                    }

                },
                {
                    Log.d("API", "that didn't work")
                })
        queue.add(stringReq)
    }
    private fun setupSlider(view: View) {
        val imageList = ArrayList<SlideModel>() // Create image list

// imageList.add(SlideModel("String Url" or R.drawable)
// imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title

        imageList.add(
                SlideModel(
                        R.drawable.sliderimage
                )
        )
        imageList.add(
                SlideModel(
                        R.drawable.sliderimage1
                )
        )
        imageList.add(SlideModel(R.drawable.sliderimage2))
        imageList.add(SlideModel(R.drawable.sliderimage3))
        val imageSlider = view.findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)
    }

    }


