package com.vaccify.app.utils

import java.util.regex.Matcher
import java.util.regex.Pattern

object ValidationClass {

    fun isValidLicenseNo(str: String?): Boolean {
        // Regex to check valid
        // Indian driving license number
        val regex = ("^(([A-Z]{2}[0-9]{2})"
                + "( )|([A-Z]{2}-[0-9]"
                + "{2}))((19|20)[0-9]"
                + "[0-9])[0-9]{7}$")

        // Compile the ReGex
        val p: Pattern = Pattern.compile(regex)

        // If the string is empty
        // return false
        if (str == null) {
            return false
        }

        // Find match between given string
        // and regular expression
        // uSing Pattern.matcher()
        val m: Matcher = p.matcher(str)

        // Return if the string
        // matched the ReGex
        return m.matches()
    }


    fun isValidPanCardNo(panCardNo: String?): Boolean {

        // Regex to check valid PAN Card number.
        val regex = "[A-Z]{5}[0-9]{4}[A-Z]"

        // Compile the ReGex
        val p = Pattern.compile(regex)

        // If the PAN Card number
        // is empty return false
        if (panCardNo == null) {
            return false
        }

        // Pattern class contains matcher() method
        // to find matching between given
        // PAN Card number using regular expression.
        val m = p.matcher(panCardNo)

        // Return if the PAN Card number
        // matched the ReGex
        return m.matches()
    }


    fun isValidAadharNumber(str: String?): Boolean {
        // Regex to check valid Aadhar number.
        val regex = "^[2-9][0-9]{3}\\s[0-9]{4}\\s[0-9]{4}$"

        // Compile the ReGex
        val p = Pattern.compile(regex)

        // If the string is empty
        // return false
        if (str == null) {
            return false
        }

        // Pattern class contains matcher() method
        // to find matching between given string
        // and regular expression.
        val m = p.matcher(str)

        // Return if the string
        // matched the ReGex
        return m.matches()
    }

    fun isValidPassportNo(str: String?): Boolean {
        // Regex to check valid.
        // passport number of India
        val regex = ("^[A-PR-WYa-pr-wy][1-9]\\d"
                + "\\s?\\d{4}[1-9]$")

        // Compile the ReGex
        val p = Pattern.compile(regex)

        // If the string is empty
        // return false
        if (str == null) {
            return false
        }

        // Find match between given string
        // and regular expression
        // using Pattern.matcher()
        val m = p.matcher(str)

        // Return if the string
        // matched the ReGex
        return m.matches()
    }
}