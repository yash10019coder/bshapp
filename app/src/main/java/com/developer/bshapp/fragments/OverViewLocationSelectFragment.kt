package com.developer.bshapp.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.developer.bshapp.MainActivity
import com.developer.bshapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class OverViewLocationSelectFragment : Fragment() {
    private lateinit var imgBanner: AppCompatImageView
    private lateinit var imgJobs: AppCompatImageView
    private lateinit var imgMarriage: AppCompatImageView
    private lateinit var imgAds: AppCompatImageView
    private lateinit var imgRealEstate: AppCompatImageView
    private lateinit var txtBanner: TextView
    private lateinit var txtJobs: TextView
    private lateinit var txtMarriage: TextView
    private lateinit var txtAds: TextView
    private lateinit var txtRealEstate: TextView
    private lateinit var backBtn: ImageView
    private lateinit var letsGetStarted: TextView
    private var cityName = ""
    private val sharedPrefFile = "languageSharedPreference"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_over_view_location_select, container, false)
        initlizations(view)

        return view
    }

    val args: OverViewLocationSelectFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cityName = args.cityName
        updateData()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun updateData() {
        if (cityName == "tamil") {
            val news = requireContext().getString(R.string.tamilLocalNews)
            val jobs = requireContext().getString(R.string.tamilJobs)
            val marriage = requireContext().getString(R.string.tamilMarriage)
            val ads = requireContext().getString(R.string.tamilAds)
            val realEstate = requireContext().getString(R.string.tamilRealEstate)
            val tamilnaduUrl =
                "https://k6u8v6y8.stackpathcdn.com/blog/wp-content/uploads/2015/11/Best-Places-to-Visit-in-Tamil-Nadu.jpg"
            letsGetStarted.text = requireContext().getString(R.string.tamilLetsGetStarted)

            setUpUI(news, jobs, marriage, ads, realEstate, tamilnaduUrl)
        } else if (cityName == "telugu" || cityName == "teluguAp") {
            val news = requireContext().getString(R.string.teluguLocalNews)
            val jobs = requireContext().getString(R.string.teluguJobs)
            val marriage = requireContext().getString(R.string.teluguMarriage)
            val ads = requireContext().getString(R.string.teluguAds)
            val realEstate = requireContext().getString(R.string.teluguRealEstate)
            letsGetStarted.text = requireContext().getString(R.string.teluguLetsGetStarted)
            if (cityName == "teluguAp") {
                val apUrl =
                    "https://k6u8v6y8.stackpathcdn.com/blog/wp-content/uploads/2015/09/best-places-to-visit-in-andhra-pradesh.png"
                setUpUI(news, jobs, marriage, ads, realEstate, apUrl)
            } else {
                val hyderabadUrl =
                    "https://mytravelsuccess.files.wordpress.com/2015/04/hyderabad3-alamy-d9k7kc.jpg?w=1600&h=1067&crop=1"
                setUpUI(news, jobs, marriage, ads, realEstate, hyderabadUrl)
            }


        } else if (cityName == "kannada") {
            val news = requireContext().getString(R.string.kannadaLocalNews)
            val jobs = requireContext().getString(R.string.kannadaJobs)
            val marriage = requireContext().getString(R.string.kannadaMarriage)
            val ads = requireContext().getString(R.string.kannadaAds)
            val realEstate = requireContext().getString(R.string.kannadaRealEstate)
            val karnatakaUrl =
                "https://www.travelogyindia.com/blog/wp-content/uploads/2015/10/Mysore.jpg"
            letsGetStarted.text = requireContext().getString(R.string.kannadaLetsGetStarted)
            setUpUI(news, jobs, marriage, ads, realEstate, karnatakaUrl)
        }
    }


    private fun setUpUI(
        news: String,
        jobs: String,
        marriage: String,
        ads: String,
        realEstate: String,
        bannerImgUrl: String
    ) {

        txtBanner.text = news;
        txtJobs.text = jobs;
        txtMarriage.text = marriage;
        txtAds.text = ads;
        txtRealEstate.text = realEstate;
        Glide.with(requireContext()).load(bannerImgUrl).skipMemoryCache(false).diskCacheStrategy(
            DiskCacheStrategy.AUTOMATIC
        ).into(imgBanner)
        Glide.with(requireContext())
            .load("https://images.unsplash.com/photo-1486312338219-ce68d2c6f44d?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=752&q=80")
            .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).dontTransform()
            .transition(DrawableTransitionOptions.withCrossFade()).into(imgJobs)
        Glide.with(requireContext())
            .load("https://images.unsplash.com/photo-1597157639073-69284dc0fdaf?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=753&q=80")
            .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).dontTransform()
            .transition(DrawableTransitionOptions.withCrossFade()).into(imgMarriage)
        Glide.with(requireContext())
            .load("https://images.unsplash.com/photo-1502772066658-3006ff41449b?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=856&q=80")
            .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).dontTransform()
            .transition(DrawableTransitionOptions.withCrossFade()).into(imgAds)
        Glide.with(requireContext())
            .load("https://images.unsplash.com/photo-1565402170291-8491f14678db?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=704&q=80")
            .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).dontTransform()
            .transition(DrawableTransitionOptions.withCrossFade()).into(imgRealEstate)
    }

    private fun initlizations(view: View) {
        imgBanner = view.findViewById(R.id.img_city_banner)
        imgJobs = view.findViewById(R.id.img_jobs)
        imgMarriage = view.findViewById(R.id.img_marriage)
        imgAds = view.findViewById(R.id.img_ads)
        imgRealEstate = view.findViewById(R.id.img_real_estate)

        txtBanner = view.findViewById(R.id.txt_local_news)
        txtJobs = view.findViewById(R.id.txt_jobs)
        txtMarriage = view.findViewById(R.id.txt_marriage)
        txtAds = view.findViewById(R.id.txt_ads)
        txtRealEstate = view.findViewById(R.id.txt_real_estate)
        backBtn = view.findViewById(R.id.backBtn)
        letsGetStarted = view.findViewById(R.id.getStarted)
        backBtn.setOnClickListener {
            findNavController().navigateUp()


        }
        val next = view.findViewById<FloatingActionButton>(R.id.next);
        next.setOnClickListener {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences(sharedPrefFile,
                Context.MODE_PRIVATE)


            val editor:SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putString("language",cityName)
            editor.apply()
            editor.commit()
            startActivity(intent)
            requireActivity().finish()


        }

    }


}