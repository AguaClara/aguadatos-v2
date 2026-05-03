package com.example.aguadatos_v2

import android.content.Context
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.core.Amplify
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import java.util.Date
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.InflowEntry
import com.amplifyframework.datastore.generated.model.Operator
import com.amplifyframework.datastore.generated.model.Plant

import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.datastore.generated.model.RawEntry

interface AmplifyService {
  fun configureAmplify(context : Context)
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