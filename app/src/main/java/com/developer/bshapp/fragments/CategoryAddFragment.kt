package com.developer.bshapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.developer.bshapp.R
import com.developer.bshapp.adapter.CategoryAdapter
import com.developer.bshapp.model.CategoryModel


class CategoryAddFragment : Fragment(), CategoryAdapter.OnItemClick {
    private val sharedPrefFile = "languageSharedPreference"
    lateinit var categoryList: ArrayList<CategoryModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category_add, container, false)


        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences(
            sharedPrefFile,
            Context.MODE_PRIVATE
        )
        val language = sharedPreferences.getString("language", "empty")
        val categorySelectTv = view.findViewById<TextView>(R.id.categorySelectTv)
        val toolbar:Toolbar=view.findViewById(R.id.toolbar2)
        toolbar.setNavigationOnClickListener {
            activity?.finish()
        }


        when (language) {
            "kannada" -> {
                initCategoryKannadaList()
                categorySelectTv.text = requireContext().getString(R.string.kannadaSelectCategory)

            }
            "tamil" -> {
                initCategoryTamilList()
                categorySelectTv.text = requireContext().getString(R.string.tamilSelectCategory)

            }
            else -> {
                initCategoryTeluguList()
                categorySelectTv.text = requireContext().getString(R.string.teluguSelectCategory)

            }
        }
        categoryRecyclerSetUp(view)
        return view
    }

    private fun initCategoryTeluguList() {
        categoryList = ArrayList()
        categoryList.clear()
        categoryList.add(
            CategoryModel(
                R.drawable.news_category,
                requireContext().getString(R.string.teluguLocalNews),
                "#f72585"
            )
        )
        categoryList.add(
            CategoryModel(
                R.drawable.jobs_category,
                requireContext().getString(R.string.teluguJobs),

                "#ffe227"
            )
        )

        categoryList.add(
            CategoryModel(
                R.drawable.ic_house,
                requireContext().getString(R.string.teluguRealEstate),
                "#33a1fd"
            )
        )

        categoryList.add(
            CategoryModel(
                R.drawable.ic_tear_off_ads,
                requireContext().getString(R.string.teluguAds),
                "#06d6a0"
            )
        )
        categoryList.add(
            CategoryModel(
                R.drawable.marriage_category,
                requireContext().getString(R.string.teluguMarriage),
                "#b69121"
            )
        )
    }

    private fun initCategoryKannadaList() {
        categoryList = ArrayList()
        categoryList.clear()
        categoryList.add(
            CategoryModel(
                R.drawable.news_category,
                requireContext().getString(R.string.kannadaLocalNews),
                "#f72585"
            )
        )
        categoryList.add(
            CategoryModel(
                R.drawable.jobs_category,
                requireContext().getString(R.string.kannadaJobs),
                "#ffe227"
            )
        )



        categoryList.add(
            CategoryModel(
                R.drawable.ic_house,
                requireContext().getString(R.string.kannadaVillageStory),
                "#867ae9"
            )
        )
        categoryList.add(
            CategoryModel(
                R.drawable.ic_tear_off_ads,
                requireContext().getString(R.string.kannadaAds),

                "#06d6a0"
            )
        )
        categoryList.add(
            CategoryModel(
                R.drawable.marriage_category,
                requireContext().getString(R.string.kannadaMarriage),
                "#b69121"
            )
        )
    }

    private fun initCategoryTamilList() {
        categoryList = ArrayList()
        categoryList.clear()
        categoryList.add(
            CategoryModel(
                R.drawable.news_category,
                requireContext().getString(R.string.tamilLocalNews),
                "#f72585"
            )
        )
        categoryList.add(
            CategoryModel(
                R.drawable.jobs_category,
                requireContext().getString(R.string.tamilJobs),
                "#867ae9"
            )
        )


        categoryList.add(
            CategoryModel(
                R.drawable.ic_house,
                requireContext().getString(R.string.tamilVillageStory),
                "#ffe227"
            )
        )
        categoryList.add(
            CategoryModel(
                R.drawable.ic_tear_off_ads,
                requireContext().getString(R.string.tamilAds),

                "#06d6a0"
            )
        )
        categoryList.add(
            CategoryModel(
                R.drawable.marriage_category,
                requireContext().getString(R.string.tamilMarriage),
                "#b69121"
            )
        )
    }

    private fun categoryRecyclerSetUp(view: View) {
        val categoryRecycler = view.findViewById<RecyclerView>(R.id.categoryRecycler)
        val adapter = CategoryAdapter(categoryList, requireContext(), this)
        categoryRecycler.adapter = adapter
        categoryRecycler.setHasFixedSize(true)

    }

    override fun onItemClick(position: Int) {
        if (position == 0) {
            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_categoryAddFragment_to_addNewsFragment)
            }
        }
        else if(position==1)
            view?.let{
                Navigation.findNavController(it)
                    .navigate(R.id.action_categoryAddFragment_to_addJobsFragment)
            }
//        else if(position==2)
//            view?.let{
//                Navigation.findNavController(it)
//                    .navigate(R.id.)
//            }
        else if(position==3)
            view?.let{
                Navigation.findNavController(it)
                    .navigate(R.id.action_categoryAddFragment_to_addAdsFragment)
            }
//        else if(position==4)
//            view?.let{
//                Navigation.findNavController(it)
//                    .navigate(R.id.action_categoryAddFragment_to_addJobsFragment2)
//            }

    }


}