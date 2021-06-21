package com.vaccify.app.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaccify.app.model.req.RegisterBeneficiaryNewReqModel
import com.vaccify.app.model.res.NewBeneficiaryResModel
import com.vaccify.app.repository.RegistrationRepository
import com.vaccify.app.utils.Resource
import kotlinx.coroutines.launch
import org.json.JSONObject

class RegistrationViewModel @ViewModelInject constructor(private val registrationRepository: RegistrationRepository) :
    ViewModel() {

    private val _newBeneficiary = MutableLiveData<Resource<NewBeneficiaryResModel>>()
    val newBeneficiary: LiveData<Resource<NewBeneficiaryResModel>> get() = _newBeneficiary


    fun registerBeneficiaryApi(token: String, reqModel: RegisterBeneficiaryNewReqModel) =
        viewModelScope.launch {
            _newBeneficiary.postValue(Resource.loading(null))
            registrationRepository.createNewBeneficiary(token, reqModel).let {
                if (it.isSuccessful) {
                    _newBeneficiary.postValue(Resource.success(it.body()))
                } else {
                    when (it.code()) {
                        401 -> {
                            _newBeneficiary.postValue(Resource.error("Token Expired", null))
                        }
                        400 -> {
                            val json = JSONObject(it.errorBody()!!.string())
                            val errorMsg = json.getString("error")
                            Log.d("API", "getBeneficiaryListApi: $errorMsg")
                            _newBeneficiary.postValue(Resource.error(errorMsg, null))
                        }
                        else -> {
                            _newBeneficiary.postValue(
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