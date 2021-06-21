package com.vaccify.app.model.req

data class RegisterBeneficiaryNewReqModel(
    val birth_year: String,
    val comorbidity_ind: String,
    val consent_version: String,
    val gender_id: Int,
    val name: String,
    val photo_id_number: String,
    val photo_id_type: Int
)