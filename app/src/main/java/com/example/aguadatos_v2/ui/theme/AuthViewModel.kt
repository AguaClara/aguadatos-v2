package com.example.aguadatos_v2.ui.theme

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
//import com.amplifyframework.kotlin.core.Amplify
import kotlinx.coroutines.launch

data class SignUpState(
  var phone: String = "",
  var email: String = "",
  var password: String = ""
)

class AuthViewModel : ViewModel() {
  lateinit var navigateTo: (String) -> Unit
  var signUpState = mutableStateOf(SignUpState())
    private set

  fun updateSignUpState(
    username: String? = null, phone: String? = null, password: String? = null) {
    password?.let {
      signUpState.value = signUpState.value.copy(password = it)
    }
    }
  }