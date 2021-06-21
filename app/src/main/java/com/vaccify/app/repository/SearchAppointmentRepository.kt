package com.vaccify.app.repository

import com.vaccify.app.api.ApiHelper
import javax.inject.Inject

class SearchAppointmentRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {


    suspend fun getStateListData(acceptLanguage: String) = apiHelper.getStatesData(acceptLanguage)
    suspend fun getDistrictListData(stateId: String) = apiHelper.getDistrictsData(stateId)

}