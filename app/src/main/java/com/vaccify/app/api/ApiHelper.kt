package com.vaccify.app.api

import com.vaccify.app.model.req.DeleteBeneficiaryReqModel
import com.vaccify.app.model.req.OTPConfirmReqModel
import com.vaccify.app.model.req.OTPReqModel
import com.vaccify.app.model.req.RegisterBeneficiaryNewReqModel
import com.vaccify.app.model.res.*
import okhttp3.ResponseBody
import retrofit2.Response

interface ApiHelper {

    suspend fun getOTP(otpReqModel: OTPReqModel): Response<OTPResponseModel>
    suspend fun confirmOTP(otpConfirmReqModel: OTPConfirmReqModel): Response<OTPConfirmResModel>
    suspend fun getIDType(token: String): Response<IdTypeResModel>
    suspend fun registerNew(
        token: String,
        registerBeneficiaryNewReqModel: RegisterBeneficiaryNewReqModel
    ): Response<NewBeneficiaryResModel>

    suspend fun getBeneficiaryList(token: String): Response<BeneficiaryListResModel>
    suspend fun deleteBeneficiary(
        token: String,
        deleteBeneficiaryReqModel: DeleteBeneficiaryReqModel
    ): Response<ResponseBody>

    suspend fun getStatesData(acceptLanguage: String): Response<StatesResModel>
    suspend fun getDistrictsData(stateId: String): Response<DistrictResModel>

    suspend fun getAppointmentListByPinCodeData(
        token: String,
        acceptLanguage: String,
        pincode: String,
        date: String,
    ): Response<AppointmentResModel>

    suspend fun getAppointmentListByDistrictData(
        token: String,
        acceptLanguage: String,
        districtId: String,
        date: String,
    ): Response<AppointmentResModel>

}