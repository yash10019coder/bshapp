package com.developer.bshapp.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.developer.bshapp.R
import com.developer.bshapp.model.CategoryModel

class CategoryAdapter(private val list: List<CategoryModel>,private val context:Context,private val listener:OnItemClick) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_single_row,parent,false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem =list[position]
        Glide.with(context).load(currentItem.img).into(holder.img)
        holder.name.text=currentItem.name
        holder.colorLayout.setBackgroundColor(Color.parseColor(currentItem.color))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        val img = itemView.findViewById<ImageView>(R.id.category_img)
        val name = itemView.findViewById<TextView>(R.id.name_category)
        val colorLayout = itemView.findViewById<ConstraintLayout>(R.id.colorLayout)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
           val position:Int=adapterPosition
           if(position!=RecyclerView.NO_POSITION){
               listener.onItemClick(adapterPosition)
           }

       }
    }

    interface OnItemClick{
        fun onItemClick(position:Int)
    }
}