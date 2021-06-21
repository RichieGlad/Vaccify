package com.vaccify.app.interfaces

interface IPreferenceHelper {
    fun setTxnId(txnId: String)
    fun getTxnId(): String
    fun setToken(token: String)
    fun getToken(): String
    fun setLoggedIn(isLoggedIn: Boolean)
    fun getLoggedIn(): Boolean
    fun clearPrefs()
}