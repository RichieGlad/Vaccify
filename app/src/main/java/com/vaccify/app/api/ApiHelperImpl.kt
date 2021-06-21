package com.vaccify.app.api

import com.vaccify.app.model.req.DeleteBeneficiaryReqModel
import com.vaccify.app.model.req.OTPConfirmReqModel
import com.vaccify.app.model.req.OTPReqModel
import com.vaccify.app.model.req.RegisterBeneficiaryNewReqModel
import com.vaccify.app.model.res.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getOTP(otpReqModel: OTPReqModel): Response<OTPResponseModel> =
        apiService.getOTP(otpReqModel)

    override suspend fun confirmOTP(otpConfirmReqModel: OTPConfirmReqModel): Response<OTPConfirmResModel> =
        apiService.confirmOTP(otpConfirmReqModel)

    override suspend fun getIDType(token: String): Response<IdTypeResModel> =
        apiService.getIdType(token)

    override suspend fun registerNew(
        token: String,
        registerBeneficiaryNewReqModel: RegisterBeneficiaryNewReqModel
    ): Response<NewBeneficiaryResModel> =
        apiService.createNewBeneficiary(token, registerBeneficiaryNewReqModel)

    override suspend fun getBeneficiaryList(token: String): Response<BeneficiaryListResModel> =
        apiService.getBeneficiaryList(token)

    override suspend fun deleteBeneficiary(
        token: String,
        deleteBeneficiaryReqModel: DeleteBeneficiaryReqModel
    ): Response<ResponseBody> =
        apiService.deleteBeneficiaryData(token, deleteBeneficiaryReqModel)

    override suspend fun getStatesData(acceptLanguage: String): Response<StatesResModel> =
        apiService.getStatesList(acceptLanguage)

    override suspend fun getDistrictsData(stateId: String): Response<DistrictResModel> =
        apiService.getDistrictList(stateId)

    override suspend fun getAppointmentListByPinCodeData(token: String,acceptLanguage: String, pincode: String, date: String): Response<AppointmentResModel> =
        apiService.getAppointmentListByPinCode(token,acceptLanguage, pincode, date)

    override suspend fun getAppointmentListByDistrictData(token: String,acceptLanguage: String, districtId: String, date: String): Response<AppointmentResModel> =
        apiService.getAppointmentListByDistrict(token,acceptLanguage, districtId, date)

}
