package com.vaccify.app.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vaccify.app.R
import com.vaccify.app.model.res.AppointmentSessionListResModel
import kotlinx.android.synthetic.main.session_list_item.view.*

class AppointmentSessionListAdapter(private val children: List<AppointmentSessionListResModel>) :
    RecyclerView.Adapter<AppointmentSessionListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.session_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return children.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val child = children[position]
        holder.dateTv.text = child.date
        holder.ageLimitTv.text = "Age Limit: " + child.min_age_limit.toString()
        holder.availabilityTv.text = "Availability: " + child.available_capacity.toString()
        holder.availabilityDose1Tv.text = "Dose 1: " + child.available_capacity_dose1.toString()
        holder.availabilityDose2Tv.text = "Dose 2: " + child.available_capacity_dose2.toString()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val dateTv: TextView = itemView.dateTv
        val ageLimitTv: TextView = itemView.ageLimitTv
        val availabilityTv: TextView = itemView.availabilityTv
        val availabilityDose1Tv: TextView = itemView.availabilityDose1Tv
        val availabilityDose2Tv: TextView = itemView.availabilityDose2Tv

    }
}
