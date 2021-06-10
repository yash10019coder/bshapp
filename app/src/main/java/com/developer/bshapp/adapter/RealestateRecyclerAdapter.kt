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
import com.developer.bshapp.model.RealstateNewsModel

class RealestateRecyclerAdapter(private var list: List<RealstateNewsModel>, private val context: Context):
        RecyclerView.Adapter<RealestateRecyclerAdapter.RealestateRecyclerModel>() {
    fun setData(list:List<RealstateNewsModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    class RealestateRecyclerModel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val propertytype = itemView.findViewById<TextView>(R.id.txproperty)
        val transactiontype = itemView.findViewById<TextView>(R.id.txtransactiontype)
        val space = itemView.findViewById<TextView>(R.id.txspace)
        val price = itemView.findViewById<TextView>(R.id.txprice)
        val ownername = itemView.findViewById<TextView>(R.id.txownername)
        val contact = itemView.findViewById<TextView>(R.id.txcontact)
        val otherinfo = itemView.findViewById<TextView>(R.id.txotherinfo)
        val date = itemView.findViewById<TextView>(R.id.txhours)
        val address = itemView.findViewById<TextView>(R.id.txaddress)
        val image = itemView.findViewById<ImageView>(R.id.realestateimage)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RealestateRecyclerModel {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.realestate_recycler_single_row, parent, false)
        return RealestateRecyclerModel(view)
    }

    override fun onBindViewHolder(holder: RealestateRecyclerModel, position: Int) {
        holder.propertytype.text = list[position].propertyType
        holder.transactiontype.text = list[position].transactionType
        holder.space.text = list[position].space
        holder.price.text = list[position].price
        holder.ownername.text = list[position].owner
        holder.otherinfo.text = list[position].details
        holder.contact.text = list[position].contact
        holder.address.text = list[position].address
        holder.date.text = list[position].date
        val url = URLs.BASE_URL + "Images/" + list[position].imageName;
        Glide.with(context).load(url).into(holder.image)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}