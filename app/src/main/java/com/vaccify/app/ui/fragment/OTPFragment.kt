package com.vaccify.app.ui.fragment

import `in`.aabhasjindal.otptextview.OTPListener
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.vaccify.app.R
import com.vaccify.app.databinding.FragmentOtpBinding
import com.vaccify.app.interfaces.IPreferenceHelper
import com.vaccify.app.model.req.OTPConfirmReqModel
import com.vaccify.app.model.req.OTPReqModel
import com.vaccify.app.ui.activity.LandingScreenActivity
import com.vaccify.app.utils.PreferenceManager
import com.vaccify.app.utils.Status
import com.vaccify.app.viewmodel.LoginViewModel
import com.vaccify.app.viewmodel.OTPViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_otp.*
import java.math.BigInteger
import java.security.MessageDigest

@AndroidEntryPoint
class OTPFragment : Fragment() {

    private lateinit var binding: FragmentOtpBinding
    private val otpViewModel: OTPViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        otp_view?.requestFocusOTP()
        otp_view?.otpListener = object : OTPListener {
            override fun onInteractionListener() {

            }

            override fun onOTPComplete(otp: String) {

                otpViewModel.confirmOTPApi(
                    OTPConfirmReqModel(
                        otp.sha256(),
                        preferenceHelper.getTxnId()
                    )
                )

                otpViewModel.res.observe(viewLifecycleOwner, {
                    when (it.status) {
                        Status.SUCCESS -> {
                            it.data.let { res ->
                                res?.let { it1 ->
                                    preferenceHelper.clearPrefs()
                                    preferenceHelper.setToken(it1.token)
                                    preferenceHelper.setLoggedIn(true)
                                }
                                val intent = Intent(activity, LandingScreenActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                activity?.startActivity(intent)
                            }
                        }
                        Status.LOADING -> {
                        }
                        Status.ERROR -> {
                            view.let { it1 ->
                                Snackbar.make(
                                    it1,
                                    it.message.toString(),
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                })


            }
        }


        resendOtpTv.setOnClickListener {

            if (arguments != null && requireArguments().containsKey("mobNo")) {
                loginApi(requireArguments().get("mobNo").toString())
            } else {
                view.let { it1 ->
                    Snackbar.make(
                        it1, "Mob number is not available", Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

        }

        acceptTermsTv.setOnClickListener {

            val bottomSheetDialog = layoutInflater.inflate(R.layout.fragment_terms_condition, null)
            val dialog = BottomSheetDialog(this.requireContext())
            dialog.setContentView(bottomSheetDialog)
            bottomSheetDialog.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()

        }

    }

    fun String.sha256(): String {
        val md = MessageDigest.getInstance("SHA-256")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }

    private fun loginApi(mobNo: String) {

        loginViewModel.getOTPApi(OTPReqModel(mobNo))

        loginViewModel.res.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data.let { res ->
                        res?.let { it1 -> preferenceHelper.setTxnId(it1.txnId) }
                        findNavController().navigate(R.id.action_otpFragment_to_loginFragment)
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