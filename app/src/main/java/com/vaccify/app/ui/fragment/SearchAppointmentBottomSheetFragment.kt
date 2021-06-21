package com.vaccify.app.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vaccify.app.R
import com.vaccify.app.databinding.FragmentSearchAppointmentBinding
import com.vaccify.app.interfaces.IPreferenceHelper
import com.vaccify.app.model.req.SearchByDistrictReqModel
import com.vaccify.app.model.req.SearchByPinReqModel
import com.vaccify.app.model.res.DistrictListResModel
import com.vaccify.app.model.res.StateListResModel
import com.vaccify.app.ui.activity.LoginScreenActivity
import com.vaccify.app.ui.adapter.CustomDistrictSpinnerAdapter
import com.vaccify.app.ui.adapter.CustomStateSpinnerAdapter
import com.vaccify.app.utils.AppConstants
import com.vaccify.app.utils.PreferenceManager
import com.vaccify.app.utils.Status
import com.vaccify.app.viewmodel.SearchAppointmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_appointment.*
import java.util.*

@AndroidEntryPoint
class SearchAppointmentBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentSearchAppointmentBinding
    private val searchAppointmentViewModel: SearchAppointmentViewModel by viewModels()
    private var stateList: List<StateListResModel> = emptyList()
    private var distList: List<DistrictListResModel> = emptyList()
    private var districtId: String = ""
    private var date: String = ""
    private lateinit var radioButton: RadioButton
    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(requireActivity()) }


    companion object {

        const val TAG = "SearchAppointment"

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pinRb.isChecked = true
        searchByInputEt.visibility = View.VISIBLE
        searchByDistrictLl.visibility = View.GONE

        getSelectedDate()
        getStatesList()
        doOperation(view)


    }


    private fun getStatesList() {

        searchAppointmentViewModel.getStatesApi(AppConstants.ACCEPT_LANGUAGE)

        searchAppointmentViewModel.res.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data.let { res ->
                        if (res != null) {
                            stateList = res.states
                            loadStateData(stateList)
                        }
                    }
                }
                Status.LOADING -> {
                }
                Status.ERROR -> {
                    if (it.message == "Token Expired") {
                        preferenceHelper.clearPrefs()
                        Toast.makeText(activity, it.message + "- Login Again!", Toast.LENGTH_LONG)
                            .show()
                        val intent = Intent(activity, LoginScreenActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        activity?.startActivity(intent)
                    } else {
                        Log.e( TAG, "getBeneficiaryList: " + it.message)
                    }

                }
            }
        })

    }

    private fun getDistrictList(stateId: String) {

        searchAppointmentViewModel.getDistrictsApi(stateId)

        searchAppointmentViewModel.distRes.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data.let { res ->
                        if (res != null) {
                            distList = res.districts
                            loadDistricts(distList)
                        }
                    }
                }
                Status.LOADING -> {
                }
                Status.ERROR -> {
                    if (it.message == "Token Expired") {
                        preferenceHelper.clearPrefs()
                        Toast.makeText(activity, it.message + "- Login Again!", Toast.LENGTH_LONG)
                            .show()
                        val intent = Intent(activity, LoginScreenActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        activity?.startActivity(intent)
                    } else {
                        Log.e("Error ", "getBeneficiaryList: " + it.message)
                    }

                }
            }
        })

    }

    private fun loadDistricts(distList: List<DistrictListResModel>) {
        val customDropDownAdapter = activity?.let { CustomDistrictSpinnerAdapter(it, distList) }
        districtACTV.adapter = customDropDownAdapter

        districtACTV.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {

                    districtId = distList[position].district_id.toString()


                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
    }

    private fun loadStateData(stateList: List<StateListResModel>) {
        val customDropDownAdapter = activity?.let { CustomStateSpinnerAdapter(it, stateList) }
        stateACTv.adapter = customDropDownAdapter

        stateACTv.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    getDistrictList(stateList[position].state_id.toString())
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
    }

    private fun doOperation(view: View) {
        backIb.setOnClickListener { dismiss() }


        searchByRg.setOnCheckedChangeListener { radioGroup, _ ->
            val selectedOption: Int = radioGroup!!.checkedRadioButtonId

            // Assigning id of the checked radio button
            radioButton = view.findViewById(selectedOption)

            if (radioButton.text.equals(AppConstants.SEARCH_BY_PIN)) {
                searchByInputEt.visibility = View.VISIBLE
                searchByDistrictLl.visibility = View.GONE

            } else if (radioButton.text.equals(AppConstants.SEARCH_BY_DISTRICT)) {
                searchByInputEt.visibility = View.GONE
                searchByDistrictLl.visibility = View.VISIBLE
            }

        }

        searchBtn.setOnClickListener {
            validation()
        }
    }

    private fun validation() {
        if (pinRb.isChecked) {
            val pinCode: String = searchByInputEt.text.toString().trim()
            when {
                pinCode.isEmpty() -> {
                    Toast.makeText(activity, "Pin code is required", Toast.LENGTH_LONG).show()
                }
                date.isEmpty() -> {
                    Toast.makeText(activity, "Please select a valid date", Toast.LENGTH_LONG).show()
                }
                else -> {
                    val searchByPinReqModel = SearchByPinReqModel(
                        "Bearer " + preferenceHelper.getToken(),
                        AppConstants.ACCEPT_LANGUAGE,
                        date,
                        pinCode
                    )

                    val bundle = Bundle()
                    bundle.putSerializable("searchByPinReqModel", searchByPinReqModel)

                    findNavController().navigate(
                        R.id.action_searchAppointmentBottomSheetFragment_to_scheduleAppointmentFragment2,
                        bundle
                    )

                }
            }
        } else if (districtRb.isChecked) {
            if (date.isEmpty()) {
                Toast.makeText(activity, "Please select a valid date", Toast.LENGTH_LONG).show()
            } else {

                val searchByDistrictReqModel = SearchByDistrictReqModel(
                    "Bearer " + preferenceHelper.getToken(),
                    AppConstants.ACCEPT_LANGUAGE,
                    date,
                    districtId
                )

                val bundle = Bundle()
                bundle.putSerializable("searchByDistrictReqModel", searchByDistrictReqModel)

                findNavController().navigate(
                    R.id.action_searchAppointmentBottomSheetFragment_to_scheduleAppointmentFragment2,
                    bundle
                )
            }

        }

    }

    private fun getSelectedDate() {
        val today = Calendar.getInstance()
        datePicker.minDate = System.currentTimeMillis() - 1000
        datePicker.init(
            today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { _, year, month, day ->
            val month = month + 1
            date = "$day-$month-$year"
        }
    }


}