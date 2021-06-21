package com.vaccify.app.repository

import com.vaccify.app.api.ApiHelper
import com.vaccify.app.model.req.OTPConfirmReqModel
import javax.inject.Inject

class OTPRepository @Inject constructor(
        private val apiHelper: ApiHelper
) {

    suspend fun confirmOTP(otpConfirmReqModel: OTPConfirmReqModel) = apiHelper.confirmOTP(otpConfirmReqModel)

}