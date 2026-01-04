package com.example.aguadatos_v2

import android.content.Context
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.core.Amplify
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.auth.options.AuthSignUpOptions

import com.example.aguadatos_v2.ui.theme.LoginState
import com.example.aguadatos_v2.ui.theme.SignUpState
import com.example.aguadatos_v2.ui.theme.VerificationState

interface AmplifyService {
  fun configureAmplify(context : Context)
  fun signUp(state : SignUpState, onSuccess : () -> Unit)
  fun verifyCode(state : VerificationState, onSuccess: () -> Unit)
  fun login(state : LoginState, onSuccess: () -> Unit)
  fun logout(onSuccess: () -> Unit)
}

class AguaDatosAmplify : AmplifyService {
  override fun configureAmplify(context: Context) {
    try {
      Amplify.addPlugin(AWSApiPlugin())
      Amplify.addPlugin(AWSCognitoAuthPlugin())
      Amplify.configure(context)
      Log.i("AguaDatosv2Amplify", "Initialized Amplify project [AguaDatosv2]")
    } catch (error: AmplifyException) {
      Log.e("AguaDatosv2Amplify", "Could not initialize Amplify", error)
    }
  }

  override fun signUp(state: SignUpState, onSuccess: () -> Unit) {
    val attributes = listOf(
      AuthUserAttribute(AuthUserAttributeKey.name(), state.name),
      AuthUserAttribute(AuthUserAttributeKey.phoneNumber(), state.phone),
//      AuthUserAttribute(AuthUserAttributeKey.custom("plantCode"), state.plantCode)
    )
    val options = AuthSignUpOptions.builder()
    .userAttributes(attributes)
    .build()

    Amplify.Auth.signUp(
      state.phone,
      state.password,
      options,
      { result ->
        Log.i("Auth", "SignUp succeeded: $result")
        onSuccess()
      },
      { Log.e("Auth", "SignUp failed", it) }
    )
  }

  override fun verifyCode(state: VerificationState, onSuccess: () -> Unit) {
    Amplify.Auth.confirmSignUp(
      state.phone,
      state.code,
      { onSuccess },
      { Log.e("Auth", "Verification failed", it) }
    )
  }

  override fun login(state : LoginState, onSuccess: () -> Unit) {
    Amplify.Auth.signIn(
      state.phone,
      state.password,
      { onSuccess() },
      { Log.e("Auth", "Login failed", it) }
    )
  }

  override fun logout(onSuccess: () -> Unit) {
    Amplify.Auth.signOut({ onSuccess() } )
  }
}