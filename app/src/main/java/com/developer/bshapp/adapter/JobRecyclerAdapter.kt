package com.developer.bshapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.developer.bshapp.R
import com.developer.bshapp.model.HomeNewsModel
import com.developer.bshapp.model.JobsNewsModel

class JobRecyclerAdapter(private var list: List<JobsNewsModel>, private val context: Context):
    RecyclerView.Adapter<JobRecyclerAdapter.JobRecyclerModel>() {
    fun setData(list:List<JobsNewsModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    class JobRecyclerModel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val jobtitle = itemView.findViewById<TextView>(R.id.txjobtitle)
        val companyname = itemView.findViewById<TextView>(R.id.txcompanyname)
        val location = itemView.findViewById<TextView>(R.id.txlocation)
        val qualification = itemView.findViewById<TextView>(R.id.txqualification)
        val salary = itemView.findViewById<TextView>(R.id.txsalary)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobRecyclerModel {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.job_recycler_single_row, parent, false)
        return JobRecyclerModel(view)
    }

    override fun onBindViewHolder(holder: JobRecyclerModel, position: Int) {
        holder.jobtitle.text = list[position].jobtitle
        holder.companyname.text = list[position].companyname
        holder.location.text = list[position].city
        holder.qualification.text = list[position].Qualification
        holder.salary.text = list[position].MinSalary
    }

    override fun getItemCount(): Int {
        return list.size
    }
}