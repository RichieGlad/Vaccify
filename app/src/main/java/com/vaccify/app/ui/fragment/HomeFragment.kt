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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vaccify.app.R
import com.vaccify.app.databinding.FragmentHomeBinding
import com.vaccify.app.interfaces.IPreferenceHelper
import com.vaccify.app.model.res.Beneficiary
import com.vaccify.app.model.req.DeleteBeneficiaryReqModel
import com.vaccify.app.ui.activity.LoginScreenActivity
import com.vaccify.app.ui.adapter.BeneficiaryListAdapter
import com.vaccify.app.utils.PreferenceManager
import com.vaccify.app.utils.Status
import com.vaccify.app.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : Fragment(), BeneficiaryListAdapter.OnDeleteItemClickListener,
    BeneficiaryListAdapter.onScheduleClickListener {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private var beneficiaryList: List<Beneficiary> = emptyList()
    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(requireActivity()) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logoutTv.setOnClickListener {
            preferenceHelper.clearPrefs()
            val intent = Intent(activity, LoginScreenActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity?.startActivity(intent)
        }

        binding.registerMemberBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_fragment_home_to_registerationFragment
            )
        }

        getBeneficiaryList()


    }

    private fun loadRv() {
        // Creates a vertical Layout Manager
        beneficiaryListRv.layoutManager = LinearLayoutManager(activity)

        // Access the RecyclerView Adapter and load the data into it
        if (beneficiaryList.isNotEmpty()) {
            emptyPlaceholderRl.visibility = View.GONE
            if (beneficiaryList.size >= 4) {
                registerMemberBtn.visibility = View.GONE
            } else {
                registerMemberBtn.visibility = View.VISIBLE
            }
            beneficiaryListRv.adapter =
                activity?.let { BeneficiaryListAdapter(beneficiaryList, it, this, this) }
        } else {
            emptyPlaceholderRl.visibility = View.VISIBLE
        }
    }

    private fun getBeneficiaryList() {

        homeViewModel.getBeneficiaryListApi("Bearer " + preferenceHelper.getToken())

        homeViewModel.beneficiaryListLiveData.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data.let { res ->
                        if (res != null) {
                            beneficiaryList = res.beneficiaries
                            loadRv()
                        } else {
                            emptyPlaceholderRl.visibility = View.VISIBLE
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

    override fun onDeleteItemClick(beneficiary_reference_id: String) {
        if (beneficiary_reference_id.isNotEmpty()) {
            deleteBeneficiaryApi(beneficiary_reference_id)
        }
    }

    private fun deleteBeneficiaryApi(beneficiary_reference_id: String) {

        homeViewModel.deleteBeneficiaryApi(
            "Bearer " + preferenceHelper.getToken(),
            DeleteBeneficiaryReqModel(beneficiary_reference_id)
        )

        homeViewModel.deleteBeneficiaryLiveData.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data.let {
                        Toast.makeText(activity, "Deleted Successfully", Toast.LENGTH_LONG).show()
                        refreshCurrentFragment()
                    }
                }
                Status.LOADING -> {
                }
                Status.ERROR -> {
                    Log.e("HomeError", "deleteBeneficiary: " + it.message)
                    if (it.message == "Token Expired") {
                        preferenceHelper.clearPrefs()
                        Toast.makeText(activity, it.message + "- Login Again!", Toast.LENGTH_LONG)
                            .show()
                        val intent = Intent(activity, LoginScreenActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        activity?.startActivity(intent)
                    } else {
                        Toast.makeText(activity, "Api Error - " + it.message, Toast.LENGTH_LONG)
                            .show()
                    }

                }
            }
        })
    }

    private fun refreshCurrentFragment() {
        val id = findNavController().currentDestination?.id
        findNavController().popBackStack(id!!, true)
        findNavController().navigate(id)
    }

    override fun onScheduleItemClick(beneficiary: Beneficiary) {
        findNavController().navigate(
            R.id.action_homeFragment_to_searchAppointmentBottomSheetFragment
        )
    }

}