package com.vaccify.app.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaccify.app.model.req.OTPConfirmReqModel
import com.vaccify.app.model.res.OTPConfirmResModel
import com.vaccify.app.repository.OTPRepository
import com.vaccify.app.utils.Resource
import kotlinx.coroutines.launch
import org.json.JSONObject

class OTPViewModel @ViewModelInject constructor(private val otpRepository: OTPRepository) :
    ViewModel() {

    private val _res = MutableLiveData<Resource<OTPConfirmResModel>>()

    val res: LiveData<Resource<OTPConfirmResModel>> get() = _res


    fun confirmOTPApi(otpConfirmReqModel: OTPConfirmReqModel) = viewModelScope.launch {
        _res.postValue(Resource.loading(null))
        otpRepository.confirmOTP(otpConfirmReqModel).let {
            if (it.isSuccessful) {
                _res.postValue(Resource.success(it.body()))
            } else {
                when (it.code()) {
                    400 -> {
                        val json = JSONObject(it.errorBody()!!.string())
                        val errorMsg = json.getString("error")
                        Log.d("API", "getBeneficiaryListApi: $errorMsg")
                        _res.postValue(Resource.error(errorMsg, null))
                    }else -> {
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