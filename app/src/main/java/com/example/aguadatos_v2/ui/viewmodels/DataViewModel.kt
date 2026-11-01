package com.example.aguadatos_v2.ui.viewmodels

import com.amplifyframework.core.Amplify
import com.example.aguadatos_v2.AguaDatosAmplify
import com.example.aguadatos_v2.AmplifyService
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.RawEntry

import java.time.LocalDate
import java.time.ZoneId
import java.time.LocalTime
import java.time.format.DateTimeFormatter

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class DataViewModel : ViewModel() {
    private val amplifyService: AmplifyService = AguaDatosAmplify

    fun submitInflowEntry(
        plantID: String,
        operatorID: String,
        inflowRate: Float,
        notes: String?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit)
    {
        Log.d(
            "DataViewModel",
            "Submitting InFlowEntry: plantID=$plantID, operatorID=$operatorID, inflowRate=$inflowRate"
        )
        amplifyService.submitInflowEntry(
            plantID = plantID,
            operatorID = operatorID,
            inflowRate = inflowRate,
            notes = notes,
            onSuccess = {
                viewModelScope.launch(Dispatchers.Main) { onSuccess() }
            },
            onError = { error ->
                viewModelScope.launch(Dispatchers.Main) { onError(error) }
            }
        )
    }

    fun submitRawWaterEntry(
        plantID: String,
        operatorID: String,
        turbidity: Float,
        notes: String?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit) {
        Log.d(
            "DataViewModel",
            "Submitting RawWaterEntry: plantID=$plantID, operatorID=$operatorID, turbidity=$turbidity"
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

    fun submitCoagulantDosageEntry(
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
            "DataViewModel",
            "Submitting coagulantDosageEntry: plantID=$plantID, operatorID=$operatorID, " +
                    "sliderPosition=$sliderPosition, " +
                    "inflowRate=$inflowRate, " +
                    "startVolume=$startVolume, " +
                    "endVolume=$endVolume, " +
                    "timeElapsed=$timeElapsed, " +
                    "chemicalDose=$chemicalDose, " +
                    "chemicalFlowRate=$chemicalFlowRate"
        )
        amplifyService.submitCoagulantDosage(
            plantID = plantID,
            operatorID = operatorID,
            sliderPosition = sliderPosition,
            inflowRate = inflowRate,
            startVolume = startVolume,
            endVolume = endVolume,
            timeElapsed = timeElapsed,
            chemicalDose = chemicalDose,
            chemicalFlowRate = chemicalFlowRate,
            notes = notes,
            onSuccess = {
                viewModelScope.launch(Dispatchers.Main) { onSuccess() }
            },
            onError = { error ->
                viewModelScope.launch(Dispatchers.Main) { onError(error) }
            }
        )
    }

    fun submitFilteredWaterTurbidityEntry(
        plantID: String,
        operatorID: String,
        turbidity: Float,
        notes: String?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit) {
        Log.d(
            "DataViewModel",
            "Submitting FilteredWaterTurbidity: plantID=$plantID, operatorID=$operatorID, turbidity=$turbidity"
        )
        amplifyService.submitFilteredWaterTurbidity(
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

    fun submitClarifiedWaterTurbidityEntry(
        plantID: String,
        operatorID: String,
        turbidity: Float,
        notes: String?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit) {
        Log.d(
            "DataViewModel",
            "Submitting ClarifiedWaterTurbidity: plantID=$plantID, operatorID=$operatorID, turbidity=$turbidity"
        )
        amplifyService.submitClarifiedWaterTurbidity(
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

    fun submitChlorineDosageEntry(
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
            "DataViewModel",
            "Submitting ChlorineDosageEntry: plantID=$plantID, operatorID=$operatorID, " +
                    "sliderPosition=$sliderPosition, " +
                    "inflowRate=$inflowRate, " +
                    "startVolume=$startVolume, " +
                    "endVolume=$endVolume, " +
                    "timeElapsed=$timeElapsed, " +
                    "chemicalDose=$chemicalDose, " +
                    "chemicalFlowRate=$chemicalFlowRate"
        )
        amplifyService.submitChlorineDosage(
            plantID = plantID,
            operatorID = operatorID,
            sliderPosition = sliderPosition,
            inflowRate = inflowRate,
            startVolume = startVolume,
            endVolume = endVolume,
            timeElapsed = timeElapsed,
            chemicalDose = chemicalDose,
            chemicalFlowRate = chemicalFlowRate,
            notes = notes,
            onSuccess = {
                viewModelScope.launch(Dispatchers.Main) { onSuccess() }
            },
            onError = { error ->
                viewModelScope.launch(Dispatchers.Main) { onError(error) }
            }
        )
    }

    fun submitColorEntry(
        plantID: String,
        operatorID: String,
        color: Float,
        notes: String?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit) {
        Log.d(
            "DataViewModel",
            "Submitting Color: plantID=$plantID, operatorID=$operatorID, color=$color"
        )
        amplifyService.submitColor(
            plantID = plantID,
            operatorID = operatorID,
            color = color,
            notes = notes,
            onSuccess = {
                viewModelScope.launch(Dispatchers.Main) { onSuccess() }
            },
            onError = { error ->
                viewModelScope.launch(Dispatchers.Main) { onError(error) }
            }
        )
    }

    fun submitTankVolumesEntry(
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
            "DataViewModel",
            "Submitting TankVolumesEntry: plantID=$plantID, operatorID=$operatorID, " +
                    "coagulantTank1=$coagulantTank1, " +
                    "coagulantTank2=$coagulantTank2, " +
                    "chlorineTank1=$chlorineTank1, " +
                    "chlorineTank2=$chlorineTank2"
        )
        amplifyService.submitTankVolumes(
            plantID = plantID,
            operatorID = operatorID,
            coagulantTank1 = coagulantTank1,
            coagulantTank2 = coagulantTank2,
            chlorineTank1 = chlorineTank1,
            chlorineTank2 = chlorineTank2,
            notes = notes,
            onSuccess = {
                viewModelScope.launch(Dispatchers.Main) { onSuccess() }
            },
            onError = { error ->
                viewModelScope.launch(Dispatchers.Main) { onError(error) }
            }
        )
    }

    /**
     * Retrieve the Raw Water entries from the specified plant and specific date range.
     * The default endDate is the end of the current day.
     */
    suspend fun getRawEntries(
        plantID: String,
        startDate: Temporal.DateTime,
        endDate: Temporal.DateTime = Temporal.DateTime(
            LocalDate.now()
                .plusDays(1)
                .atStartOfDay(ZoneId.systemDefault())
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        )
    ) : List<RawEntry> {
        return suspendCancellableCoroutine { continuation ->
            amplifyService.getRawEntries(
                plantID = plantID,
                startDate = startDate,
                endDate = endDate,
                onSuccess = { entries ->
                    if (continuation.isActive) {
                        continuation.resume(entries)
                    }
                },
                onError = { error ->
                    if (continuation.isActive) {
                        continuation.resumeWithException(Exception(error))
                    }
                }
            )
        }
    }

    fun testGetRawEntries(
        plantID: String,
        startDate: Temporal.DateTime
    ) {
        viewModelScope.launch {
            try {
                Log.d("RawEntryTest", "Starting database query...")

                val entries = getRawEntries(
                    plantID = plantID,
                    startDate = startDate
                )

                Log.d("RawEntryTest", "Number of records: ${entries.size}")

                entries.forEachIndexed { index, entry ->
                    Log.d("RawEntryTest", """
                    Record #$index
                    ID: ${entry.id}
                    Plant: ${entry.plant?.id}
                    Operator: ${entry.operator?.id}
                    Turbidity: ${entry.turbidity}
                    Created At: ${entry.createdAt}
                    Notes: ${entry.notes}
                """.trimIndent())
                }

            } catch (e: Exception) {
                Log.e("RawEntryTest", "Query failed: ${e.message}", e)
            }
        }
    }
}