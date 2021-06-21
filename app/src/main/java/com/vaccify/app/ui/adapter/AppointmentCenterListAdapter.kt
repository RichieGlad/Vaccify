package com.vaccify.app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vaccify.app.R
import com.vaccify.app.model.res.AppointmentCenterListResModel
import kotlinx.android.synthetic.main.appointment_list_item.view.*

class AppointmentCenterListAdapter(private val parents: List<AppointmentCenterListResModel>) :
    RecyclerView.Adapter<AppointmentCenterListAdapter.ViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.appointment_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return parents.size
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val parent = parents[position]
        holder.feeType.text = parent.fee_type
        holder.centerName.text = parent.name
        holder.blockName.text = parent.block_name
        holder.address.text = parent.address + " " + parent.state_name
        holder.disdistrictName.text = parent.pincode
        val childLayoutManager =
            LinearLayoutManager(holder.recyclerView.context, LinearLayout.HORIZONTAL, false)
        childLayoutManager.initialPrefetchItemCount = 2
        holder.recyclerView.apply {
            layoutManager = childLayoutManager
            adapter = AppointmentSessionListAdapter(parent.sessions)
            setRecycledViewPool(viewPool)
        }

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerView: RecyclerView = itemView.sessionsRv
        val feeType: TextView = itemView.feeTypeTv
        val centerName: TextView = itemView.centreNameTv
        val blockName: TextView = itemView.blockNameTv
        val address: TextView = itemView.centerAddressTv
        val disdistrictName: TextView = itemView.districtNameTv
    }
}