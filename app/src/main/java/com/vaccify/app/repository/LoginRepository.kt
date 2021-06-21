package com.vaccify.app.repository

import com.vaccify.app.api.ApiHelper
import com.vaccify.app.model.req.OTPReqModel
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {

    suspend fun getOTPData(otpReqModel: OTPReqModel) = apiHelper.getOTP(otpReqModel)

}