package com.vaccify.app.api

import com.vaccify.app.model.req.DeleteBeneficiaryReqModel
import com.vaccify.app.model.req.OTPConfirmReqModel
import com.vaccify.app.model.req.OTPReqModel
import com.vaccify.app.model.req.RegisterBeneficiaryNewReqModel
import com.vaccify.app.model.res.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("auth/generateMobileOTP")
    suspend fun getOTP(@Body otpReqModel: OTPReqModel): Response<OTPResponseModel>

    @POST("auth/validateMobileOtp")
    suspend fun confirmOTP(@Body otpConfirmReqModel: OTPConfirmReqModel): Response<OTPConfirmResModel>

    @GET("registration/beneficiary/idTypes")
    suspend fun getIdType(@Header("Authorization") token: String): Response<IdTypeResModel>

    @POST("registration/beneficiary/new")
    suspend fun createNewBeneficiary(
        @Header("Authorization") token: String,
        @Body beneficiaryNewReqModel: RegisterBeneficiaryNewReqModel
    ): Response<NewBeneficiaryResModel>

    @GET("appointment/beneficiaries")
    suspend fun getBeneficiaryList(@Header("Authorization") token: String): Response<BeneficiaryListResModel>

    @POST("registration/beneficiary/delete")
    suspend fun deleteBeneficiaryData(
        @Header("Authorization") token: String,
        @Body deleteBeneficiaryReqModel: DeleteBeneficiaryReqModel
    ): Response<ResponseBody>

    @GET("admin/location/states")
    suspend fun getStatesList(@Header("Accept-Language") acceptLanguage: String): Response<StatesResModel>

    @GET("admin/location/districts/{state_id}")
    suspend fun getDistrictList(@Path("state_id") stateId: String): Response<DistrictResModel>

    @GET("appointment/sessions/calendarByPin")
    suspend fun getAppointmentListByPinCode(
        @Header("Authorization") token: String,
        @Header("Accept-Language") acceptLanguage: String, @Query("pincode") pincode: String,
        @Query("date") date: String
    ): Response<AppointmentResModel>

    @GET("appointment/sessions/calendarByDistrict")
    suspend fun getAppointmentListByDistrict(
        @Header("Authorization") token: String,
        @Header("Accept-Language") acceptLanguage: String, @Query("district_id") districtId: String,
        @Query("date") date: String
    ): Response<AppointmentResModel>
}