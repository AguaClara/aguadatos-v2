package com.example.aguadatos_v2.ui.viewmodels

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
  val email: String = "",
  val plantCode : String = "",
  var password: String = ""
)

data class LoginState(
  val name: String = "",
  val email: String = "",
  var password: String = ""
)

data class VerificationState(
  val email: String = "",
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
    email: String? = null,
    plantCode: String? = null,
    password: String? = null
  ) {
    signUpState.value = signUpState.value.copy(
      name      = name ?: signUpState.value.name,
      email     = email ?: signUpState.value.email,
      plantCode = plantCode ?: signUpState.value.plantCode,
      password  = password ?: signUpState.value.password
    )
  }

  fun updateLoginState(
    name: String? = null,
    email: String? = null,
    password: String? = null
  ) {
    loginState.value = loginState.value.copy(
      name      = name ?: signUpState.value.name,
      email     = email ?: signUpState.value.email,
      password  = password ?: signUpState.value.password
    )
  }

  fun updateVerificationState(
    code: String? = null,
    email: String? = null
  ) {
    verificationState.value = verificationState.value.copy(
      email = email ?: verificationState.value.email,
      code  = code ?: verificationState.value.code
    )
  }

  fun configureAmplify(context : Context) {
    amplifyService.configureAmplify(context)
  }
  fun signUp(
    onSuccess: () -> Unit,
    onError: (msg : String) -> Unit
  ) {
    amplifyService.signUp(
      signUpState.value,
      { viewModelScope.launch(Dispatchers.Main) { onSuccess() } },
      onError
    )
  }

  fun login(
    onSuccess: () -> Unit,
    onError: (msg : String) -> Unit
  ) {
    amplifyService.login(
      loginState.value,
      { viewModelScope.launch(Dispatchers.Main) { onSuccess() } },
      onError
    )
  }

  fun logout(
    onSuccess: () -> Unit,
    onError: (msg : String) -> Unit
  ) {
    amplifyService.logout() {
      viewModelScope.launch(Dispatchers.Main) { onSuccess() }
    }
  }

  fun confirmCode(
    code: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  ) {
    val email = signUpState.value.email
    Amplify.Auth.confirmSignUp(
      email,
      code,
      {
        viewModelScope.launch(Dispatchers.Main) { onSuccess() }
      },
      { error ->
        viewModelScope.launch(Dispatchers.Main) { onError(error.localizedMessage ?: "Confirm code error") }
      }
    )
  }

  fun resendCode(
    onSuccess: () -> Unit,
    onError: (String) -> Unit) {
    val email = signUpState.value.email
    Amplify.Auth.resendSignUpCode(
      email,
      {
        viewModelScope.launch(Dispatchers.Main) { onSuccess() }
      },
      { error ->
        viewModelScope.launch(Dispatchers.Main) { onError(error.localizedMessage ?: "Resend code error") }
      }
    )

  }
}