package com.vaccify.app.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaccify.app.model.req.SearchByDistrictReqModel
import com.vaccify.app.model.req.SearchByPinReqModel
import com.vaccify.app.model.res.AppointmentResModel
import com.vaccify.app.repository.ScheduleAppointmentRepository
import com.vaccify.app.utils.Resource
import kotlinx.coroutines.launch
import org.json.JSONObject

class ScheduleAppointmentViewModel @ViewModelInject constructor(private val scheduleAppointmentRepository: ScheduleAppointmentRepository) :
    ViewModel() {

    private val _res = MutableLiveData<Resource<AppointmentResModel>>()
    val res: LiveData<Resource<AppointmentResModel>> get() = _res

    private val _distRes = MutableLiveData<Resource<AppointmentResModel>>()
    val distRes: LiveData<Resource<AppointmentResModel>> get() = _distRes


    fun getAppointmentListByPinCode(searchReqModel: SearchByPinReqModel) = viewModelScope.launch {
        _res.postValue(Resource.loading(null))
        scheduleAppointmentRepository.getAppointmentListByPinCodeData(
            searchReqModel.token,
            searchReqModel.acceptLanguage,
            searchReqModel.pinCode,
            searchReqModel.date
        ).let {
            if (it.isSuccessful) {
                _res.postValue(Resource.success(it.body()))
            } else {
                _res.postValue(Resource.error(it.errorBody().toString(), null))
                when (it.code()) {
                    400 -> {
                        val json = JSONObject(it.errorBody()!!.string())
                        val errorMsg = json.getString("error")
                        _res.postValue(Resource.error(errorMsg, null))
                    }
                    else -> {
                        _res.postValue(
                            Resource.error(
                                "API Error - " + it.code().toString(), null
                            )
                        )
                    }
                }
            }
        }
    }

    fun getAppointmentListByDistrict(searchReqModel: SearchByDistrictReqModel) = viewModelScope.launch {
        _distRes.postValue(Resource.loading(null))
        scheduleAppointmentRepository.getAppointmentListByDistrictData(
            searchReqModel.token,
            searchReqModel.acceptLanguage,
            searchReqModel.district_id,
            searchReqModel.date
        ).let {
            if (it.isSuccessful) {
                _distRes.postValue(Resource.success(it.body()))
            } else {
                _distRes.postValue(Resource.error(it.errorBody().toString(), null))
                when (it.code()) {
                    400 -> {
                        val json = JSONObject(it.errorBody()!!.string())
                        val errorMsg = json.getString("error")
                        _distRes.postValue(Resource.error(errorMsg, null))
                    }
                    else -> {
                        _distRes.postValue(
                            Resource.error(
                                "API Error - " + it.code().toString(), null
                            )
                        )
                    }
                }
            }
        }
    }

}