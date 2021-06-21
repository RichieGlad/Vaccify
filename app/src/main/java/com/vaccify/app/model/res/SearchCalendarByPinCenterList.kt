package com.vaccify.app.model.res

data class SearchCalendarByPinCenterList(
    val address: String,
    val address_l: String,
    val block_name: String,
    val block_name_l: String,
    val center_id: Int,
    val district_name: String,
    val district_name_l: String,
    val fee_type: String,
    val from: String,
    val lat: Double,
    val long: Double,
    val name: String,
    val name_l: String,
    val pincode: String,
    val sessions: List<SearchCalendarByPinSessionList>,
    val state_name: String,
    val state_name_l: String,
    val to: String,
    val vaccine_fees: List<SearchCalendarByPinVaccineFeeList>
)