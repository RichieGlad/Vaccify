package com.vaccify.app.model.req

import java.io.Serializable

data class SearchByPinReqModel(
    val token: String,
    val acceptLanguage: String,
    val date: String,
    val pinCode: String,
) : Serializable