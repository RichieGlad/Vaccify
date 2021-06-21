package com.vaccify.app.repository

import com.vaccify.app.api.ApiHelper
import javax.inject.Inject

class ScheduleAppointmentRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {


    suspend fun getAppointmentListByPinCodeData(
        token: String,
        acceptLanguage: String,
        pincode: String,
        date: String,
    ) =
        apiHelper.getAppointmentListByPinCodeData(token, acceptLanguage, pincode, date)

    suspend fun getAppointmentListByDistrictData(
        token: String,
        acceptLanguage: String,
        districtId: String,
        date: String,
    ) =
        apiHelper.getAppointmentListByDistrictData(token, acceptLanguage, districtId, date)

}