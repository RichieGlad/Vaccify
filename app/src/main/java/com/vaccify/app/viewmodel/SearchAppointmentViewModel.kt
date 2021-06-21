package com.vaccify.app.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaccify.app.model.res.DistrictResModel
import com.vaccify.app.model.res.StatesResModel
import com.vaccify.app.repository.SearchAppointmentRepository
import com.vaccify.app.utils.Resource
import kotlinx.coroutines.launch
import org.json.JSONObject

class SearchAppointmentViewModel @ViewModelInject constructor(private val searchAppointmentRepository: SearchAppointmentRepository) :
    ViewModel() {

    private val _res = MutableLiveData<Resource<StatesResModel>>()
    val res: LiveData<Resource<StatesResModel>> get() = _res

    private val _distRes = MutableLiveData<Resource<DistrictResModel>>()
    val distRes: LiveData<Resource<DistrictResModel>> get() = _distRes


    fun getStatesApi(acceptLanguage: String) = viewModelScope.launch {
        _res.postValue(Resource.loading(null))
        searchAppointmentRepository.getStateListData(acceptLanguage).let {
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

    fun getDistrictsApi(stateId: String) = viewModelScope.launch {
        _distRes.postValue(Resource.loading(null))
        searchAppointmentRepository.getDistrictListData(stateId).let {
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