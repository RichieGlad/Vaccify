package com.vaccify.app.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaccify.app.model.req.OTPReqModel
import com.vaccify.app.model.res.OTPResponseModel
import com.vaccify.app.repository.LoginRepository
import com.vaccify.app.utils.Resource
import kotlinx.coroutines.launch
import org.json.JSONObject

class LoginViewModel @ViewModelInject constructor(private val loginRepository: LoginRepository) :
    ViewModel() {

    private val _res = MutableLiveData<Resource<OTPResponseModel>>()


    val res: LiveData<Resource<OTPResponseModel>> get() = _res


    fun getOTPApi(otpReqModel: OTPReqModel) = viewModelScope.launch {
        _res.postValue(Resource.loading(null))
        loginRepository.getOTPData(otpReqModel).let {
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

}