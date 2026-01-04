package com.example.aguadatos_v2.ui.theme

import com.amplifyframework.core.Amplify
import com.example.aguadatos_v2.AguaDatosAmplify
import com.example.aguadatos_v2.AmplifyService
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class SignUpState(
  val name: String = "",
  val phone: String = "",
  val plantCode : String = "",
  var password: String = ""
)

data class LoginState(
  val name: String = "",
  val phone: String = "",
  var password: String = ""
)

data class VerificationState(
  val phone: String = "",
  val code: String = ""
)

class AuthViewModel : ViewModel() {
  private val amplifyService: AmplifyService = AguaDatosAmplify()
  var signUpState = mutableStateOf(SignUpState())
    private set
  var loginState = mutableStateOf(LoginState())
    private set
  var verificationState = mutableStateOf(VerificationState())
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

  fun updateLoginState(
    name: String? = null,
    phone: String? = null,
    password: String? = null
  ) {
    loginState.value = loginState.value.copy(
      name      = name ?: signUpState.value.name,
      phone     = phone ?: signUpState.value.phone,
      password  = password ?: signUpState.value.password
    )
  }

  fun updateVerificationState(
    code: String? = null,
    phone: String? = null
  ) {
    verificationState.value = verificationState.value.copy(
      phone = phone ?: verificationState.value.phone,
      code  = code ?: verificationState.value.code
    )
  }

  fun configureAmplify(context : Context) {
    amplifyService.configureAmplify(context)
  }
  fun signUp(
    onSuccess: () -> Unit,
    onError: (msg : String) -> Unit) {
    amplifyService.signUp(signUpState.value) {
      viewModelScope.launch(Dispatchers.Main) { onSuccess() }
    }
  }

  fun login(
    onSuccess: () -> Unit,
    onError: (msg : String) -> Unit) {
    amplifyService.login(loginState.value) {
      viewModelScope.launch(Dispatchers.Main) { onSuccess() }
    }
  }

  fun logout(
    onSuccess: () -> Unit,
    onError: (msg : String) -> Unit) {
    amplifyService.logout() {
      viewModelScope.launch(Dispatchers.Main) { onSuccess() }
    }
  }

  fun confirmCode(
    code: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit) {
    val phoneNumber = signUpState.value.phone
    onSuccess()
//    Amplify.Auth.confirmSignUp(
//      phoneNumber,
//      code,
//      { onSuccess },
//      { onError }
//    )
  }

  fun resendCode(
    onSuccess: () -> Unit,
    onError: (String) -> Unit) {
    val phoneNumber = signUpState.value.phone
    Amplify.Auth.resendSignUpCode(
      phoneNumber,
      { onSuccess },
      { onError }
    )

  }
}