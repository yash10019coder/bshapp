package  com.developer.bshapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.developer.bshapp.R
import com.developer.bshapp.adapter.JobRecyclerAdapter
import com.developer.bshapp.model.JobsNewsModel
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


class JobsFragment : Fragment() {

    private lateinit var list: ArrayList<JobsNewsModel>
    lateinit var model: JobsNewsModel
    private lateinit var jobRecycler: RecyclerView
    lateinit var adapter: JobRecyclerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_jobs, container, false)
        setupSlider(view)
        getdata()
        list = ArrayList<JobsNewsModel>()
        jobRecycler = view.findViewById<RecyclerView>(R.id.jobrecycler)
        jobRecycler.layoutManager = LinearLayoutManager(requireContext())

        adapter = JobRecyclerAdapter(list, requireContext())
        jobRecycler.adapter = adapter

        return view
    }
    private fun getdata() {


        val queue = Volley.newRequestQueue(requireContext())
        val url: String = "http://139.59.61.90/main/api/getData.php?s=VerifiedJobs"

        // Request a string response from the provided URL.
        val stringReq = StringRequest(Request.Method.GET, url,
            { response ->

                try {

//                    val respObj = JSONObject(response)
//                    val success = respObj.getInt("status")
//                    val jsonArray = respObj.getJSONArray("data")
    val jsonArray=JSONArray(response)


                        for (i in 0 until jsonArray.length()) {
                            Log.d("Hello", "hello")
                            val item = jsonArray.getJSONObject(i)
                            val companyname = item.getString("companyName")
                            val jobtitle = item.getString("jobTitle")
                            val description = item.getString("description")
                            val state = item.getString("state")
                            val city = item.getString("city")
                            val district = item.getString("district")
                            val phone = item.getString("phone")
                            val qualification = item.getString("qualification")
                            val experience = item.getString("experience")
                            val minsalary = item.getString("minSalary")
                            val maxsalary = item.getString("maxSalary")
                            val date = item.getString("date")


                            model = JobsNewsModel(
                                id,
                                companyname,
                                jobtitle,
                                description,
                                state,
                                city,
                                district,
                                phone,
                                qualification,
                                experience,
                                minsalary,
                                maxsalary,
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