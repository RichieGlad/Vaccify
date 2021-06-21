package com.vaccify.app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.vaccify.app.R
import com.vaccify.app.databinding.FragmentLoginBinding
import com.vaccify.app.interfaces.IPreferenceHelper
import com.vaccify.app.model.req.OTPReqModel
import com.vaccify.app.utils.PreferenceManager
import com.vaccify.app.utils.Status
import com.vaccify.app.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var mobileNo: String
    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(requireActivity()) }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.getOtpBtn.setOnClickListener {

            validation()


        }
    }

    private fun validation() {
        //check if the EditText have values or not
        mobileNo = binding.mobNoEt.text.toString().trim()

        if (mobileNo.isEmpty()) {
            view?.let { it1 ->
                Snackbar.make(
                        it1,
                        "Please enter a valid Mobile Number",
                        Snackbar.LENGTH_SHORT
                ).show()
            }
        } else {
            loginApi(mobileNo)
        }
    }

    private fun loginApi(mobNo: String) {

        loginViewModel.getOTPApi(OTPReqModel(mobNo))

        loginViewModel.res.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data.let { res ->
                        res?.let { it1 -> preferenceHelper.setTxnId(it1.txnId) }
                        val args = Bundle()
                        args.putString("mobNo", mobNo)
                        findNavController().navigate(R.id.action_loginFragment_to_otpFragment, args)
                    }
                }
                Status.LOADING -> {
                }
                Status.ERROR -> {
                    view?.let { it1 ->
                        Snackbar.make(
                                it1, it.message.toString(), Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })


    }


}