package com.vaccify.app.model.res

data class BeneficiaryListResModel(val beneficiaries: List<Beneficiary>)

data class Beneficiary(
    val appointments: List<Appointment>,
    val beneficiary_reference_id: String,
    val birth_year: String,
    val comorbidity_ind: String,
    val dose1_date: String,
    val dose2_date: String,
    val gender: String,
    val mobile_number: String,
    val name: String,
    val photo_id_number: String,
    val photo_id_type: String,
    val vaccination_status: String,
    val vaccine: String
)

data class Appointment(
        val appointment_id: String,
        val block_name: String,
        val block_name_l: String,
        val center_id: Int,
        val date: String,
        val district_name: String,
        val district_name_l: String,
        val dose: Int,
        val fee_type: String,
        val from: String,
        val lat: Double,
        val long: Double,
        val name: String,
        val name_l: String,
        val pincode: String,
        val session_id: String,
        val slot: String,
        val state_name: String,
        val state_name_l: String,
        val to: String
)