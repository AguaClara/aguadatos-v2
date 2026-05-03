package com.example.aguadatos_v2.ui.viewmodels

import com.amplifyframework.core.Amplify
import com.example.aguadatos_v2.AguaDatosAmplify
import com.example.aguadatos_v2.AmplifyService
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataViewModel : ViewModel() {
    private val amplifyService: AmplifyService = AguaDatosAmplify

    fun submitInflowEntry(
        plantID: String,
        operatorID: String,
        inflowRate: Double,
        notes: String?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        Amplify.Auth.fetchAuthSession(
            { result -> Log.i("Auth", "Signed in: ${result.isSignedIn}") },
            { error -> Log.e("Auth", "Session error", error) }
        )
        amplifyService.submitInflowEntry(
            plantID = plantID,
            operatorID = operatorID,
            inflowRate = inflowRate,
            notes = notes,
            onSuccess = { viewModelScope.launch(Dispatchers.Main) { onSuccess() } },
            onError = { error ->
                viewModelScope.launch(Dispatchers.Main) { onError(error) }
            }
        )
    }

    fun submitRawEntry(
        plantID: String,
        operatorID: String,
        turbidity: Double,
        notes: String?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        Amplify.Auth.fetchAuthSession(
            { result -> Log.i("Auth", "Signed in: ${result.isSignedIn}") },
            { error -> Log.e("Auth", "Session error", error) }
        )
        amplifyService.submitRawEntry(
            plantID = plantID,
            operatorID = operatorID,
            turbidity = turbidity,
            notes = notes,
            onSuccess = {
                viewModelScope.launch(Dispatchers.Main) { onSuccess() }
            },
            onError = { error ->
                viewModelScope.launch(Dispatchers.Main) { onError(error) }
            }
        )
    }
}