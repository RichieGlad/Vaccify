package com.vaccify.app.utils

object AppConstants {

    //const val BASE_URL = "https://api.demo.co-vin.in/api/v2/"

   // const val BASE_URL = "https://cdn-api.co-vin.in/api/v2/"
    const val BASE_URL = "https://cdn-api.co-vin.in/api/v2/"
    const val SECRET_KEY =
        "U2FsdGVkX19xUTBk80wSgIR6u2Zm6rxWdpesZ5UsdXmKiihLhdfsHTCw+ss6ybqXd0z6KxaPVREmVbk+YGXiHA=="
    const val MALE_ID = 1
    const val FEMALE_ID = 2
    const val OTHERS_ID = 3
    const val MALE = "Male"
    const val FEMALE = "Female"
    const val OTHERS = "Others"
    const val SEARCH_BY_PIN = "Search by PIN"
    const val SEARCH_BY_DISTRICT = "Search by District"
    const val ACCEPT_LANGUAGE = "hi_IN"
    val idCardTypeArray = arrayOf(
        "Aadhaar Card", "Driving License", "PAN Card", "Passport"
    )

    val idCardTypeIdArray = arrayOf(1, 2, 6, 8)

    val vaccineTypeArray = arrayOf("COVISHIELD", "COVAXIN")
}