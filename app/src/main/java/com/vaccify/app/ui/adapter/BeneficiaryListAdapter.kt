package com.vaccify.app.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vaccify.app.R
import com.vaccify.app.model.res.Beneficiary
import kotlinx.android.synthetic.main.beneficiary_list_item.view.*

class BeneficiaryListAdapter(
    private val beneficiaryList: List<Beneficiary>,
    val context: Context,
    private val mDeleteListener: OnDeleteItemClickListener,
    private val mScheduleListener: onScheduleClickListener
) : RecyclerView.Adapter<ViewHolder>() {

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return beneficiaryList.size
    }

    interface OnDeleteItemClickListener {
        fun onDeleteItemClick(beneficiary_reference_id: String)

    }

    interface onScheduleClickListener {
        fun onScheduleItemClick(beneficiary: Beneficiary)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.beneficiary_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val beneficiary: Beneficiary = beneficiaryList[position]
        holder.nameTv.text = beneficiary.name
        holder.yearOfBirthTv.text = "Year of Birth: " + beneficiary.birth_year
        holder.idProofTv.text = "ID Proof: " + beneficiary.photo_id_type
        holder.photoIdNoTv.text = "ID Number: " + beneficiary.photo_id_number
        holder.vaccinationStatusTv.text = beneficiary.vaccination_status

        if (beneficiary.dose1_date.isEmpty()) {
            holder.noOfDoseTv.text = "Dose 1"
            holder.scheduledStatusTv.text = "Not Scheduled"
        } else if (beneficiary.dose1_date.isNotEmpty() && beneficiary.dose2_date.isEmpty()) {
            holder.noOfDoseTv.text = "Dose 2"
            holder.scheduledStatusTv.text = "Not Scheduled"
        } else {
            holder.noOfDoseTv.text = "Dose 2"
            holder.scheduledStatusTv.text = "Scheduled"
        }

        holder.deleteTv.setOnClickListener {
            mDeleteListener.onDeleteItemClick(beneficiaryList[position].beneficiary_reference_id)
        }

        holder.scheduleTv.setOnClickListener {
            mScheduleListener.onScheduleItemClick(beneficiary)
        }

    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val nameTv = view.nameTv
    val idProofTv = view.photoIdTv
    val photoIdNoTv = view.photoIdNoTv
    val deleteTv = view.deleteTv
    val noOfDoseTv = view.noOfDoseTv
    val scheduleTv = view.scheduleTv
    val yearOfBirthTv = view.yearOfBirthTv
    val vaccinationStatusTv = view.vaccinationStatusTv
    val scheduledStatusTv = view.scheduledStatusTv
}