package com.vaccify.app.model.req

import java.io.Serializable

data class SearchByDistrictReqModel(
    val token: String,
    val acceptLanguage: String,
    val date: String,
    val district_id: String,
) : Serializable