package com.vaccify.app.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vaccify.app.databinding.FragmentScheduleAppointmentBinding
import com.vaccify.app.interfaces.IPreferenceHelper
import com.vaccify.app.model.req.SearchByDistrictReqModel
import com.vaccify.app.model.req.SearchByPinReqModel
import com.vaccify.app.model.res.AppointmentCenterListResModel
import com.vaccify.app.model.res.AppointmentResModel
import com.vaccify.app.ui.activity.LoginScreenActivity
import com.vaccify.app.ui.adapter.AppointmentCenterListAdapter
import com.vaccify.app.utils.PreferenceManager
import com.vaccify.app.utils.Status
import com.vaccify.app.viewmodel.ScheduleAppointmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_schedule_appointment.*

@AndroidEntryPoint
class ScheduleAppointmentFragment : Fragment() {

    private val scheduleAppointmentViewModel: ScheduleAppointmentViewModel by viewModels()
    private lateinit var binding: FragmentScheduleAppointmentBinding
    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(requireActivity()) }
    private lateinit var appointmentResModel: AppointmentResModel
    private lateinit var searchByPinReqModel: SearchByPinReqModel
    private lateinit var searchByDistrictReqModel: SearchByDistrictReqModel
    private var appointmentCenterList: List<AppointmentCenterListResModel> = emptyList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        if (args != null) {
            when {
                args.containsKey("searchByPinReqModel") -> {
                    searchByPinReqModel =
                        ((args.getSerializable("searchByPinReqModel") as SearchByPinReqModel?)!!)
                    getAppointmentListByPinCode(searchByPinReqModel)
                }
                args.containsKey("searchByDistrictReqModel") -> {
                    searchByDistrictReqModel =
                        (args.getSerializable("searchByDistrictReqModel") as SearchByDistrictReqModel?)!!
                    getAppointmentListByDistrict(searchByDistrictReqModel)
                }
                else -> {
                    Log.e("ScheduleAppointment", "onViewCreated: Null")
                }
            }
        }

    }

    private fun getAppointmentListByDistrict(searchByDistrictReqModel: SearchByDistrictReqModel) {

        scheduleAppointmentViewModel.getAppointmentListByDistrict(searchByDistrictReqModel)

        scheduleAppointmentViewModel.distRes.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data.let { res ->
                        if (res != null) {
                            appointmentResModel = res
                            loadAppointmentList(appointmentResModel)
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
                        Log.e("Error ", "getAppointmentList: " + it.message)
                    }

                }
            }
        })

    }

    private fun loadAppointmentList(appointmentResModel: AppointmentResModel) {

        appointmentCenterList = appointmentResModel.centers

        appointmentRv.layoutManager = LinearLayoutManager(activity)

        // Access the RecyclerView Adapter and load the data into it
        if (appointmentCenterList.isNotEmpty()) {
            centersPlaceHolderTv.visibility = View.GONE
            appointmentRv.adapter =
                activity?.let { AppointmentCenterListAdapter(appointmentCenterList) }
        } else {
            centersPlaceHolderTv.visibility = View.VISIBLE
        }


    }

    private fun getAppointmentListByPinCode(searchByPinReqModel: SearchByPinReqModel) {
        scheduleAppointmentViewModel.getAppointmentListByPinCode(searchByPinReqModel)

        scheduleAppointmentViewModel.res.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data.let { res ->
                        if (res != null) {
                            appointmentResModel = res
                            loadAppointmentList(appointmentResModel)
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
                        Log.e("Error ", "getAppointmentList: " + it.message)
                    }

                }
            }
        })
    }


}