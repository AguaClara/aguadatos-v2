package com.example.aguadatos_v2

import android.content.Context
import android.util.Log
import java.util.Date

import com.amplifyframework.AmplifyException
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.datastore.generated.model.Operator
import com.amplifyframework.datastore.generated.model.Plant
import com.amplifyframework.datastore.generated.model.InflowEntry
import com.amplifyframework.datastore.generated.model.RawEntry
import com.amplifyframework.datastore.generated.model.ChemicalType
import com.amplifyframework.datastore.generated.model.DoseEntry
import com.amplifyframework.datastore.generated.model.FilteredEntry
import com.amplifyframework.datastore.generated.model.ClarifiedEntry
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery

interface AmplifyService {
  fun configureAmplify(context : Context)
  fun submitInflowEntry(
    plantID: String,
    operatorID: String,
    inflowRate: Float,
    notes: String?,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  )

  fun submitRawEntry(
    plantID: String,
    operatorID: String,
    turbidity: Float,
    notes: String?,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  )

  fun submitCoagulantDosage(
    plantID: String,
    operatorID: String,
    sliderPosition: Float,
    inflowRate: Float,
    startVolume: Float,
    endVolume: Float,
    timeElapsed: Float,
    chemicalDose: Float,
    chemicalFlowRate: Float,
    notes: String?,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  )

  fun submitFilteredWaterTurbidity(
    plantID: String,
    operatorID: String,
    turbidity: Float,
    notes: String?,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  )

  fun submitClarifiedWaterTurbidity(
    plantID: String,
    operatorID: String,
    turbidity: Float,
    notes: String?,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  )

  fun submitChlorineDosage(
    plantID: String,
    operatorID: String,
    sliderPosition: Float,
    inflowRate: Float,
    startVolume: Float,
    endVolume: Float,
    timeElapsed: Float,
    chemicalDose: Float,
    chemicalFlowRate: Float,
    notes: String?,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  )

  fun submitColor(
    plantID: String,
    operatorID: String,
    color: Float,
    notes: String?,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  )

  fun submitTankVolumes(
    plantID: String,
    operatorID: String,
    coagulantTank1: Float,
    coagulantTank2: Float,
    chlorineTank1: Float,
    chlorineTank2: Float,
    notes: String?,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  )

  fun getRawEntries(
    plantID: String,
    startDate: Temporal.DateTime,
    endDate: Temporal.DateTime,
    onSuccess: (List<RawEntry>) -> Unit,
    onError: (String) -> Unit
  )
}

object AguaDatosAmplify : AmplifyService {
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
    inflowRate: Float,
    notes: String?,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  ) {
    val entry = InflowEntry.builder()
      .createdAt(Temporal.DateTime(Date(),0))
      .inflowRate(inflowRate.toDouble())
      .plant(Plant.justId(plantID))
      .operator(Operator.justId(operatorID))
      .notes(notes)
      .build()

    Amplify.API.mutate(
      ModelMutation.create(entry),
      { response ->
        if (response.hasErrors()) {
          Log.e("AmplifyService", "GraphQL errors: ${response.errors}")
          onError(response.errors.first().message)
        } else {
          Log.i("AmplifyService", "Success: ${response.data}")
          onSuccess()
        }
      },
      { error ->
        onError(error.localizedMessage ?: "Entry Failed")
      }
    )
  }

  override fun submitRawEntry(
    plantID: String,
    operatorID: String,
    turbidity: Float,
    notes: String?,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  ) {

    Log.d(
      "AmplifyService",
      "Submitting RawEntry: plantID=$plantID, operatorID=$operatorID, turbidity=$turbidity"
    )
    val entry = RawEntry.builder()
      .createdAt(Temporal.DateTime(Date(),0))
      .turbidity(turbidity.toDouble())
      .plant(Plant.justId(plantID))
      .operator(Operator.justId(operatorID))
      .notes(notes)
      .build()

    Amplify.API.mutate(
      ModelMutation.create(entry),
      { response ->
        Log.d("AmplifyService", "MUTATION CALLBACK RECEIVED")
        Log.d("AmplifyService", "Response data: ${response.data}")
        Log.d("AmplifyService", "Response errors: ${response.errors}")
        if (response.hasErrors()) {
          Log.e("AmplifyService", "GraphQL errors: ${response.errors}")
          onError(response.errors.first().message)
        } else {
          Log.i("AmplifyService", "Success: ${response.data}")
          onSuccess()
        }
      },
      { error ->
        Log.e("AmplifyService", "========== MUTATION FAILURE ==========")
        Log.e("AmplifyService", "Class: ${error.javaClass.name}")
        Log.e("AmplifyService", "Message: ${error.message}")
        Log.e("AmplifyService", "Cause: ${error.cause?.javaClass?.name}")
        Log.e("AmplifyService", "Cause message: ${error.cause?.message}")
        Log.e("AmplifyService", "Full exception:", error)
        Log.e("AmplifyService", "======================================")

        onError(error.message ?: "Entry Failed")
      }
    )
    Log.d("AmplifyService", "Amplify.API.mutate() returned")
  }






  // TODO: all the following depend on the AWS console I believe
  // -Anthony Querbach 10/25/2026

  override fun submitCoagulantDosage(
    plantID: String,
    operatorID: String,
    sliderPosition: Float,
    inflowRate: Float,
    startVolume: Float,
    endVolume: Float,
    timeElapsed: Float,
    chemicalDose: Float,
    chemicalFlowRate: Float,
    notes: String?,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  ) {
    val entry = DoseEntry.builder()
      .createdAt(Temporal.DateTime(Date(), 0))
      .chemicalType(ChemicalType.COAGULANT)
      .targetDose(chemicalDose.toDouble())
      .updatedSliderPosition(sliderPosition.toDouble())
      .updatedFlowRate(chemicalFlowRate.toDouble())
      .plant(Plant.justId(plantID))
      .operator(Operator.justId(operatorID))
      .build()

    Amplify.DataStore.save(
      entry,
      { result ->
        Log.i("AmplifyService","Successfully saved DoseEntry: ${result.item().id}")
        onSuccess()
      },
      { error ->
        Log.e("AmplifyService", "Failed to save DoseEntry", error)
        onError(error.localizedMessage ?: "Failed to save dosage entry")
      }
    )
  }


  override fun submitFilteredWaterTurbidity(
    plantID: String,
    operatorID: String,
    filteredWaterTurbidity: Float,
    notes: String?,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  ) {

    Log.d(
      "AmplifyService",
      "Submitting FilteredEntry: plantID=$plantID, " +
              "operatorID=$operatorID, turbidity=$filteredWaterTurbidity"
    )

    val entry = FilteredEntry.builder()
      .createdAt(Temporal.DateTime(Date(), 0))
      .turbidities(listOf(filteredWaterTurbidity.toDouble()))
      .notes(notes)
      .plant(Plant.justId(plantID))
      .operator(Operator.justId(operatorID))
      .build()

    Amplify.DataStore.save(
      entry,
      { result ->
        Log.i("AmplifyService", "FilteredEntry saved: ${result.item().id}")
        onSuccess()
      },
      { error ->
        Log.e("AmplifyService", "Failed to save FilteredEntry", error)
        onError(error.localizedMessage ?: "Failed to save filtered water turbidity")
      }
    )
  }



  override fun submitClarifiedWaterTurbidity(
    plantID: String,
    operatorID: String,
    clarifiedWaterTurbidity: Float,
    notes: String?,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  ) {

    Log.d(
      "AmplifyService",
      "Submitting ClarifiedEntry: plantID=$plantID, " +
              "operatorID=$operatorID, turbidity=$clarifiedWaterTurbidity"
    )

    val entry = ClarifiedEntry.builder()
      .createdAt(Temporal.DateTime(Date(), 0))
      .turbidity(clarifiedWaterTurbidity.toDouble())
      .notes(notes)
      .plant(Plant.justId(plantID))
      .operator(Operator.justId(operatorID))
      .build()

    Amplify.DataStore.save(
      entry,
      { result ->
        Log.i("AmplifyService", "ClarifiedEntry saved: ${result.item().id}")
        onSuccess()
      },
      { error ->
        Log.e("AmplifyService", "Failed to save ClarifiedEntry", error)
        onError(error.localizedMessage ?: "Failed to save clarified water turbidity")
      }
    )
  }



  override fun submitChlorineDosage(
    plantID: String,
    operatorID: String,
    sliderPosition: Float,
    inflowRate: Float,
    startVolume: Float,
    endVolume: Float,
    timeElapsed: Float,
    chemicalDose: Float,
    chemicalFlowRate: Float,
    notes: String?,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  ) {

    Log.d(
      "AmplifyService",
      "Submitting ChlorineEntry: plantID=$plantID, " +
              "operatorID=$operatorID, chemicalDose=$chemicalDose"
    )

    val entry = DoseEntry.builder()
      .createdAt(Temporal.DateTime(Date(), 0))
      .chemicalType(ChemicalType.CHLORINE)
      .targetDose(chemicalDose.toDouble())
      .updatedSliderPosition(sliderPosition.toDouble())
      .updatedFlowRate(chemicalFlowRate.toDouble())
      .plant(Plant.justId(plantID))
      .operator(Operator.justId(operatorID))
      .build()

    Amplify.DataStore.save(
      entry,
      { result ->
        Log.i("AmplifyService", "ChlorineEntry saved: ${result.item().id}")
        onSuccess()
      },
      { error ->
        Log.e("AmplifyService", "Failed to save ChlorineEntry", error)
        onError(error.localizedMessage ?: "Failed to save chlorine dosage")
      }
    )
  }


  override fun submitColor(
    plantID: String,
    operatorID: String,
    color: Float,
    notes: String?,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  ) {
    TODO("Not yet implemented")
  }

  override fun submitTankVolumes(
    plantID: String,
    operatorID: String,
    coagulantTank1: Float,
    coagulantTank2: Float,
    chlorineTank1: Float,
    chlorineTank2: Float,
    notes: String?,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  ) {

    Log.d(
      "AmplifyService",
      "Submitting TankVolumes: " +
              "plantID=$plantID, operatorID=$operatorID, " +
              "coagulantTank1=$coagulantTank1, " +
              "coagulantTank2=$coagulantTank2, " +
              "chlorineTank1=$chlorineTank1, " +
              "chlorineTank2=$chlorineTank2, "
    )
    TODO("Not yet implemented")
  }

  /**
   * This communicates with Amplify asynchronously, so callbacks
   * are used instead of directly returning a value.
   */
  override fun getRawEntries(
    plantID: String,
    startDate: Temporal.DateTime,
    endDate: Temporal.DateTime,
    onSuccess: (List<RawEntry>) -> Unit,
    onError: (String) -> Unit
  ) {
    val predicate = RawEntry.PLANT.eq(plantID)
      .and(RawEntry.CREATED_AT.ge(startDate))
      .and(RawEntry.CREATED_AT.le(endDate))

    Amplify.API.query(
      ModelQuery.list(RawEntry::class.java, predicate),
      { response ->
        if (response.hasErrors()) { onError(response.errors.first().message) }
        else { onSuccess(response.data.items.toList()) }
      },
      { error ->
        onError(error.localizedMessage ?: "Failed to retrieve raw water records")
      }
    )
  }
}