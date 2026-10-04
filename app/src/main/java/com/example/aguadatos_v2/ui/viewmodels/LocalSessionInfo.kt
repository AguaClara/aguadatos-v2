package com.example.aguadatos_v2.ui.viewmodels


// stores some local session info. Should be initialized at login
// and used when submitting logs
object UserSession {

    var operatorID: Long? = null
    var plantId: Long? = null

    val isLoggedIn: Boolean
        get() = operatorID != null




    // TODO: call this when a log out feature is added to prevent old IDs
    fun clear() {
        operatorID = null
        plantId = null
    }
}