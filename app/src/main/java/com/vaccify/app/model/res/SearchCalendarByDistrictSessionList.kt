package com.vaccify.app.model.res

data class SearchCalendarByDistrictSessionList(
    val available_capacity: Int,
    val available_capacity_dose1: Int,
    val available_capacity_dose2: Int,
    val date: String,
    val min_age_limit: Int,
    val session_id: String,
    val slots: List<String>,
    val vaccine: String
)