package com.vaccify.app.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaccify.app.model.req.DeleteBeneficiaryReqModel
import com.vaccify.app.model.res.BeneficiaryListResModel
import com.vaccify.app.repository.HomeRepository
import com.vaccify.app.utils.Resource
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject

class HomeViewModel @ViewModelInject constructor(private val homeRepository: HomeRepository) :
    ViewModel() {

    private val _beneficiaryList = MutableLiveData<Resource<BeneficiaryListResModel>>()

    val beneficiaryListLiveData: LiveData<Resource<BeneficiaryListResModel>> get() = _beneficiaryList

    private val _deleteBeneficiary = MutableLiveData<Resource<ResponseBody>>()

    val deleteBeneficiaryLiveData: LiveData<Resource<ResponseBody>> get() = _deleteBeneficiary


    fun getBeneficiaryListApi(token: String) = viewModelScope.launch {
        _beneficiaryList.postValue(Resource.loading(null))
        homeRepository.getBeneficiaryList(token).let {
            if (it.isSuccessful) {
                _beneficiaryList.postValue(Resource.success(it.body()))
            } else {
                when (it.code()) {
                    401 -> {
                        _beneficiaryList.postValue(Resource.error("Token Expired", null))
                    }
                    400 -> {
                        val json = JSONObject(it.errorBody()!!.string())
                        val errorMsg = json.getString("error")
                        Log.d("API", "getBeneficiaryListApi: $errorMsg")
                        _beneficiaryList.postValue(Resource.error(errorMsg, null))
                    }
                    else -> {
                        _beneficiaryList.postValue(
                            Resource.error(
                                "API Error - " + it.code().toString(), null
                            )
                        )
                    }
                }
            }
        }
    }


    fun deleteBeneficiaryApi(token: String, reqModel: DeleteBeneficiaryReqModel) =
        viewModelScope.launch {
            _deleteBeneficiary.postValue(Resource.loading(null))
            homeRepository.deleteBeneficiary(token, reqModel).let {
                if (it.isSuccessful) {
                    _deleteBeneficiary.postValue(Resource.success(it.body()))
                } else {
                    when (it.code()) {
                        401 -> {
                            _deleteBeneficiary.postValue(Resource.error("Token Expired", null))
                        }
                        400 -> {
                            val json = JSONObject(it.errorBody()!!.string())
                            val errorMsg = json.getString("error")
                            Log.d("API", "getBeneficiaryListApi: $errorMsg")
                            _deleteBeneficiary.postValue(Resource.error(errorMsg, null))
                        }
                        else -> {
                            _deleteBeneficiary.postValue(
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