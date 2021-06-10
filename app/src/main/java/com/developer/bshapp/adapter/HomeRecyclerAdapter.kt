package com.developer.bshapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.developer.bshapp.R
import com.developer.bshapp.classes.URLs
import com.developer.bshapp.model.HomeNewsModel

class HomeRecyclerAdapter(private var list: List<HomeNewsModel>, private val context: Context) :
    RecyclerView.Adapter<HomeRecyclerAdapter.HomeRecyclerModel>() {

    fun setData(list:List<HomeNewsModel>){
        this.list=list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerModel {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_recycler_single_row, parent, false)
        return HomeRecyclerModel(view)

    }

    override fun onBindViewHolder(holder: HomeRecyclerModel, position: Int) {
        val url = URLs.BASE_URL + "Images/" + list[position].Images;
        Glide.with(context).load(url).into(holder.image)
        holder.headlineText.text = list[position].headline
        holder.content.text = list[position].news
        holder.uploadedBy.text = list[position].uploader

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class HomeRecyclerModel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.image)
        val headlineText = itemView.findViewById<TextView>(R.id.headline)
        val content = itemView.findViewById<TextView>(R.id.content)
        val uploadedBy = itemView.findViewById<TextView>(R.id.name)


    }
}