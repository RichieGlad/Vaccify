package com.vaccify.app.model.req

import com.vaccify.app.utils.AppConstants

data class OTPReqModel(
        var mobile: String, var secret: String = AppConstants.SECRET_KEY
)