package com.example.aguadatos_v2.ui.viewmodels


// stores some local session info. Should be initialized at login
// and used when submitting logs
object UserSession {

    private var operatorID: Long? = null
    private var plantId: Long? = null

    private var initalized = false

    public val isLoggedIn: Boolean
        get() = operatorID != null

    public fun getOperatorID(): String {
        return (operatorID ?: throw IllegalStateException()).toString()
    }

    public fun getPlantID(): String {
        return (plantId ?: throw IllegalStateException()).toString()
    }

    public fun setSessionInfo(setOperatorID : Long, setPlantId : Long){
        if (initalized){
            throw IllegalStateException("session info already set. Perhaps forgot to call clear after previous logout?")
        }
        operatorID = setOperatorID;
        plantId = setPlantId;
        initalized = true;
    }



    // TODO: call this when a log out feature is added to prevent old IDs
    public fun clear() {
        operatorID = null
        plantId = null
        initalized = false;
    }
}