package com.example.aguadatos_v2

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.core.Amplify

class AguaDatosv2Amplify : Application() {
    override fun onCreate() {
        super.onCreate()

        try {
            Amplify.configure(applicationContext)
            Log.i("AguaDatosv2Amplify", "Initialized Amplify project [AguaDatosv2]")
        } catch (error: AmplifyException) {
            Log.e("AguaDatosv2Amplify", "Could not initialize Amplify", error)
        }
    }
}