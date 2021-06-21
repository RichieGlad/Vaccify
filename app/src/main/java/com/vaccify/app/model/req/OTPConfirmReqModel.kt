package com.vaccify.app.model.req

data class OTPConfirmReqModel(
        val otp: String,
        val txnId: String
)