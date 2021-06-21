package com.vaccify.app.repository

import com.vaccify.app.api.ApiHelper
import com.vaccify.app.model.req.RegisterBeneficiaryNewReqModel
import javax.inject.Inject

class RegistrationRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {


    suspend fun createNewBeneficiary(
        token: String,
        registerBeneficiaryNewReqModel: RegisterBeneficiaryNewReqModel
    ) = apiHelper.registerNew(token, registerBeneficiaryNewReqModel)

}