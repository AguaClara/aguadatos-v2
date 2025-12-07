package com.example.aguadatos_v2.ui.theme

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
//import com.amplifyframework.kotlin.core.Amplify
import kotlinx.coroutines.launch

data class SignUpState(
  val name: String = "",
  val phone: String = "",
  val plantCode : String = "",
  var password: String = "",
  var confirmPassword: String = ""
)

class AuthViewModel : ViewModel() {
  lateinit var navigateTo: (String) -> Unit
  var signUpState = mutableStateOf(SignUpState())
    private set

  fun updateSignUpState(
    name: String? = null,
    phone: String? = null,
    plantCode: String? = null,
    password: String? = null
  ) {
    signUpState.value = signUpState.value.copy(
      name      = name ?: signUpState.value.name,
      phone     = phone ?: signUpState.value.phone,
      plantCode = plantCode ?: signUpState.value.plantCode,
      password  = password ?: signUpState.value.password
    )
  }

  fun signUp(
    onSuccess: () -> Unit,
    onError: (msg : String) -> Unit) {
    val state = signUpState.value

    val attributes = listOf(
      AuthUserAttribute(AuthUserAttributeKey.name(), state.name),
      AuthUserAttribute(AuthUserAttributeKey.phoneNumber(), state.phone),
      AuthUserAttribute(AuthUserAttributeKey.custom("plantCode"), state.plantCode)
    )

    val options = AuthSignUpOptions.builder()
      .userAttributes(attributes)
      .build()

    Amplify.Auth.signUp(
      state.phone,
      state.password,
      options,
      { result ->
        Log.i("AuthViewModel", "SignUp succeeded: $result")
        onSuccess()
      },
      { error: AuthException ->
        Log.e("AuthViewModel", "SignUp failed", error)
        onError(error.localizedMessage ?: "Unknown signup error")
      }
    )
  }
}