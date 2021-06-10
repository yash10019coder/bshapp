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

import com.developer.bshapp.model.MatrimonyNewsModel

class MatrimonyRecyclerAdapter(private var list: List<MatrimonyNewsModel>, private val context: Context):
        RecyclerView.Adapter<MatrimonyRecyclerAdapter.MatrimonyRecyclerModel>(){

    fun setData(list:List<MatrimonyNewsModel>) {
        this.list = list
        notifyDataSetChanged()
    }
    class MatrimonyRecyclerModel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.txmatName)
        val gender = itemView.findViewById<TextView>(R.id.txGender)
        val height = itemView.findViewById<TextView>(R.id.txheight)
        val caste = itemView.findViewById<TextView>(R.id.txCaste)
        val city = itemView.findViewById<TextView>(R.id.txcity)
        val state = itemView.findViewById<TextView>(R.id.txState)
        val education = itemView.findViewById<TextView>(R.id.txedu)
        val image = itemView.findViewById<ImageView>(R.id.imgMatrimony)
        val dateofbirth = itemView.findViewById<TextView>(R.id.txdob)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatrimonyRecyclerAdapter.MatrimonyRecyclerModel {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.matrimony_recycler_single_row, parent, false)
        return MatrimonyRecyclerModel(view)
    }

    override fun onBindViewHolder(holder: MatrimonyRecyclerModel, position: Int) {
        holder.name.text = list[position].name
        holder.gender.text = list[position].gender
        holder.height.text = list[position].height
        holder.caste.text = list[position].caste
        holder.city.text = list[position].city
        holder.state.text = list[position].state
        holder.education.text = list[position].education
        holder.dateofbirth.text = list[position].dateofbirth
        val url = URLs.BASE_URL + "Images/" + list[position].imageName;
        Glide.with(context).load(url).into(holder.image)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}