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
import java.util.Date
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.InflowEntry
import com.amplifyframework.datastore.generated.model.Operator
import com.amplifyframework.datastore.generated.model.Plant

import com.example.aguadatos_v2.ui.viewmodels.LoginState
import com.example.aguadatos_v2.ui.viewmodels.SignUpState
import com.example.aguadatos_v2.ui.viewmodels.VerificationState

import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.datastore.generated.model.RawEntry

interface AmplifyService {
  fun configureAmplify(context : Context)
  fun signUp(state : SignUpState, onSuccess : () -> Unit, onError: (String) -> Unit)
  fun verifyCode(state : VerificationState, onSuccess : () -> Unit)
  fun login(state : LoginState, onSuccess : () -> Unit, onError : (String) -> Unit)
  fun logout(onSuccess : () -> Unit)
  fun isLoggedIn(callback : (Boolean) -> Unit)
  fun submitInflowEntry(
    plantID: String,
    operatorID: String,
    inflowRate: Double,
    notes: String?,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  )

  fun submitRawEntry(
    plantID: String,
    operatorID: String,
    turbidity: Double,
    notes: String?,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  )
}

class AguaDatosAmplify : AmplifyService {
  override fun configureAmplify(context: Context) {
    try {
      Amplify.addPlugin(AWSApiPlugin())
      Amplify.addPlugin(AWSCognitoAuthPlugin())
      Amplify.configure(context)

      Amplify.Auth.fetchAuthSession(
        { result -> Log.i("Auth", "Logged in: ${result.isSignedIn}") },
        { error -> Log.e("Auth", "Session error", error) }
      )
      Log.i("AguaDatosv2Amplify", "Initialized Amplify project [AguaDatosv2]")

    } catch (error: AmplifyException) {
      Log.e("AguaDatosv2Amplify", "Could not initialize Amplify", error)
    }
  }

  override fun signUp(state: SignUpState, onSuccess: () -> Unit, onError: (String) -> Unit) {
    val attributes = listOf(
      AuthUserAttribute(AuthUserAttributeKey.name(), state.name),
      AuthUserAttribute(AuthUserAttributeKey.email(), state.email),
//      AuthUserAttribute(AuthUserAttributeKey.custom("plantCode"), state.plantCode)
    )
    val options = AuthSignUpOptions.builder()
    .userAttributes(attributes)
    .build()

    Amplify.Auth.signUp(
      state.email,
      state.password,
      options,
      { result ->
        Log.i("Auth", "SignUp succeeded: $result")
        onSuccess()
      },
      { error ->
        Log.e("Auth", "SignUp failed: ${error.localizedMessage}", error)
        onError(error.localizedMessage ?: "Sign up failed")
      }
    )
  }

  override fun verifyCode(state: VerificationState, onSuccess: () -> Unit) {
    Amplify.Auth.confirmSignUp(
      state.email,
      state.code,
      { onSuccess() },
      { Log.e("Auth", "Verification failed", it) }
    )
  }

  override fun login(state : LoginState, onSuccess: () -> Unit, onError: (String) -> Unit) {
    Amplify.Auth.signIn(
      state.email,
      state.password,
      { onSuccess() },
      { error ->
        Log.e("Auth", "Login failed", error)
        onError(error.localizedMessage ?: "Login Failed")
      }
    )
  }

  override fun logout(onSuccess: () -> Unit) {
    Amplify.Auth.signOut({ onSuccess() } )
  }

  override fun isLoggedIn(callback: (Boolean) -> Unit) {
    Amplify.Auth.fetchAuthSession(
      { result -> callback(result.isSignedIn) },
      { callback(false) }
    )
  }

  override fun submitInflowEntry(
    plantID: String,
    operatorID: String,
    inflowRate: Double,
    notes: String?,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  ) {
    val entry = InflowEntry.builder()
      .createdAt(Temporal.DateTime(Date(),0))
      .inflowRate(inflowRate)
      .plant(Plant.justId(plantID))
      .operator(Operator.justId(operatorID))
      .notes(notes)
      .build()

    Amplify.API.mutate(
      ModelMutation.create(entry),
      {
        onSuccess()
      },
      { error ->
        onError(error.localizedMessage ?: "Entry Failed")
      }
    )
  }

  override fun submitRawEntry(
    plantID: String,
    operatorID: String,
    turbidity: Double,
    notes: String?,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  ) {
    val entry = RawEntry.builder()
      .createdAt(Temporal.DateTime(Date(),0))
      .turbidity(turbidity)
      .plant(Plant.justId(plantID))
      .operator(Operator.justId(operatorID))
      .notes(notes)
      .build()

    Amplify.API.mutate(
      ModelMutation.create(entry),
      {
        onSuccess()
      },
      { error ->
        onError(error.localizedMessage ?: "Entry Failed")
      }
    )
  }
}