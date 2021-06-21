package com.vaccify.app.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.vaccify.app.R
import com.vaccify.app.databinding.FragmentRegistrationBinding
import com.vaccify.app.interfaces.IPreferenceHelper
import com.vaccify.app.model.req.RegisterBeneficiaryNewReqModel
import com.vaccify.app.ui.activity.LoginScreenActivity
import com.vaccify.app.utils.*
import com.vaccify.app.viewmodel.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_registration.*


@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var cardType: String
    private lateinit var cardNo: String
    private lateinit var cardHolderName: String
    private lateinit var dob: String
    private var genderId: Int = 0
    private var cardSelectionId: Int = 0
    lateinit var radioButton: RadioButton
    private val registrationViewModel: RegistrationViewModel by viewModels()
    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadCardType()
        doOperation(view)

    }

    private fun doOperation(view: View) {

        idTypeAutoCompleteTv.setOnClickListener { idTypeAutoCompleteTv.showDropDown() }
        genderRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            val selectedOption: Int = radioGroup!!.checkedRadioButtonId

            // Assigning id of the checked radio button
            radioButton = view.findViewById(selectedOption)

            when (radioButton.text) {
                AppConstants.MALE -> {
                    genderId = AppConstants.MALE_ID
                }
                AppConstants.FEMALE -> {
                    genderId = AppConstants.FEMALE_ID
                }
                AppConstants.OTHERS -> {
                    genderId = AppConstants.OTHERS_ID
                }
            }

        }


        registerTv.setOnClickListener {
            validation()
        }

    }

    private fun loadCardType() {
        val arrayAdapter = ArrayAdapter(
            requireActivity(),
            R.layout.support_simple_spinner_dropdown_item,
            AppConstants.idCardTypeArray,
        )

        idTypeAutoCompleteTv.setAdapter(arrayAdapter)
        idTypeAutoCompleteTv.isCursorVisible = false
        idTypeAutoCompleteTv.setOnItemClickListener { parent, _, position, id ->
            idTypeAutoCompleteTv.showDropDown()
            cardSelectionId = AppConstants.idCardTypeIdArray[position]

            when (cardSelectionId) {
                1 -> {
                    cardNoEt.hint = "Aadhaar No as (XXXX XXXX XXXX)"
                    cardHolderNameEt.hint = "Name as in Aadhaar card"
                    dobEt.hint = "Year of birth (YYYY)"
                }
                2 -> {
                    cardNoEt.hint = "Driving License Number"
                    cardHolderNameEt.hint = "Name as in Driving License"
                    dobEt.hint = "Year of birth (YYYY)"
                }
                6 -> {
                    cardNoEt.hint = "Pan Number"
                    cardHolderNameEt.hint = "Name as in Pan card"
                    dobEt.hint = "Year of birth (YYYY)"
                }
                8 -> {
                    cardNoEt.hint = "Passport Number"
                    cardHolderNameEt.hint = "Name as in Passport"
                    dobEt.hint = "Year of birth (YYYY)"
                }
            }

        }

    }


    private fun validation() {


        cardType = idTypeAutoCompleteTv.text.toString().trim()
        cardNo = cardNoEt.text.toString().trim()
        cardHolderName = cardHolderNameEt.text.toString().trim()
        dob = dobEt.text.toString().trim()

        if (cardType.isEmpty()) {
            view?.let { it1 ->
                Snackbar.make(
                    it1,
                    "Please select a card type",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        } else if (cardNo.isEmpty()) {
            view?.let { it1 ->
                Snackbar.make(
                    it1,
                    "Please enter a card number",
                    Snackbar.LENGTH_SHORT
                ).show()
            }

        } else if (cardSelectionId == 1 && !ValidationClass.isValidAadharNumber(cardNo)) {
            view?.let { it1 ->
                Snackbar.make(
                    it1,
                    "Please enter a valid aadhaar card number",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        } else if (cardSelectionId == 2 && !ValidationClass.isValidLicenseNo(cardNo)) {
            view?.let { it1 ->
                Snackbar.make(
                    it1,
                    "Please enter a valid driving license number",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        } else if (cardSelectionId == 6 && !ValidationClass.isValidPanCardNo(cardNo)) {
            view?.let { it1 ->
                Snackbar.make(
                    it1,
                    "Please enter a valid pan number",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        } else if (cardSelectionId == 8 && !ValidationClass.isValidPassportNo(cardNo)) {
            view?.let { it1 ->
                Snackbar.make(
                    it1,
                    "Please enter a valid passport number",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        } else if (cardHolderName.isEmpty()) {
            view?.let { it1 ->
                Snackbar.make(
                    it1,
                    "Please enter the card holder name",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        } else if (genderId < 1) {
            view?.let { it1 ->
                Snackbar.make(
                    it1,
                    "Please choose the gender",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        } else if (dob.isEmpty() && dob.length < 5) {
            view?.let { it1 ->
                Snackbar.make(
                    it1,
                    "Please enter the valid year of birth(YYYY)",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        } else {
            registerNewBeneficiary(
                RegisterBeneficiaryNewReqModel(
                    dob,
                    "Y",
                    "1",
                    genderId,
                    cardHolderName,
                    cardNo,
                    cardSelectionId
                )
            )
        }


    }

    private fun registerNewBeneficiary(registerBeneficiaryNewReqModel: RegisterBeneficiaryNewReqModel) {

        registrationViewModel.registerBeneficiaryApi(
            "Bearer " + preferenceHelper.getToken(),
            registerBeneficiaryNewReqModel
        )

        registrationViewModel.newBeneficiary.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data.let { res ->
                        res?.beneficiary_reference_id
                        findNavController().navigate(
                            R.id.action_registrationFragment_to_homeFragment
                        )
                    }
                }
                Status.LOADING -> {
                }
                Status.ERROR -> {
                    Log.e("ERROR", "registerNewBeneficiary: " + it.message)
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


}

