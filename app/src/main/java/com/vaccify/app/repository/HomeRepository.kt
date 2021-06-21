package com.vaccify.app.repository

import com.vaccify.app.api.ApiHelper
import com.vaccify.app.model.req.DeleteBeneficiaryReqModel
import javax.inject.Inject

class HomeRepository @Inject constructor(
        private val apiHelper: ApiHelper
) {

    suspend fun getBeneficiaryList(token: String) = apiHelper.getBeneficiaryList(token)
    suspend fun deleteBeneficiary(token: String, deleteBeneficiaryReqModel: DeleteBeneficiaryReqModel) = apiHelper.deleteBeneficiary(token, deleteBeneficiaryReqModel)


}