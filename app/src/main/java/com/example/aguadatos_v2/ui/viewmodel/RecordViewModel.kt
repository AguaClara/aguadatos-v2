package com.example.aguadatos_v2.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.aguadatos_v2.data.*
import com.example.aguadatos_v2.AmplifyService
import com.example.aguadatos_v2.AguaDatosAmplify
import com.amplifyframework.datastore.generated.model.RawEntry
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RecordViewModel(application: Application) : AndroidViewModel(application) {
    private val amplifyService: AmplifyService = AguaDatosAmplify()
    private val _cloudRawWaterRecords = MutableStateFlow<List<RawEntry>>(emptyList())
    val cloudRawWaterRecords: StateFlow<List<RawEntry>> = _cloudRawWaterRecords.asStateFlow()
    private val _isLoadingCloudRecords = MutableStateFlow(false)
    val isLoadingCloudRecords: StateFlow<Boolean> = _isLoadingCloudRecords.asStateFlow()
    private val _cloudRecordsError = MutableStateFlow<String?>(null)
    val cloudRecordsError: StateFlow<String?> = _cloudRecordsError.asStateFlow()
    private val dao = AppDatabase.getDatabase(application).recordDao()
    val coagulantRecords = dao.getAllCoagulant()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val chlorineRecords = dao.getAllChlorine()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val rawWaterRecords = dao.getAllRawWater()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredWaterRecords = dao.getAllFilteredWater()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val clarifiedWaterRecords = dao.getAllClarifiedWater()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun saveCoagulant(
        sliderPosition: Float,
        newSliderPos: Float,
        inflowRate: String,
        startVolume: String,
        endVolume: String,
        timeElapsed: String,
        sliderPosOverDose: Float,
        chemicalFlowRate: String
    ) = viewModelScope.launch {
        dao.insertCoagulant(
            CoagulantRecord(
                sliderPos = sliderPosition,
                newSliderPos = newSliderPos,
                waterInflow = inflowRate,
                startVolume = startVolume,
                endVolume = endVolume,
                timeElapsed = timeElapsed,
                sliderPosOverDose = sliderPosOverDose,
                chemFlowRate = chemicalFlowRate
            )
        )
    }
    fun updateCoagulant(record: CoagulantRecord) = viewModelScope.launch {
        dao.updateCoagulant(record)
    }
    fun saveChlorine(
        sliderPos: Float,
        newSliderPos: Float,
        waterInflow: String,
        startVolume: String,
        endVolume: String,
        timeElapsed: String,
        sliderPosOverDose: Float,
        chemFlowRate: String
    ) = viewModelScope.launch {
        dao.insertChlorine(
            ChlorineRecord(
                sliderPos = sliderPos,
                newSliderPos = newSliderPos,
                waterInflow = waterInflow,
                startVolume = startVolume,
                endVolume = endVolume,
                timeElapsed = timeElapsed,
                sliderPosOverDose = sliderPosOverDose,
                chemFlowRate = chemFlowRate
            )
        )
    }

    fun saveRawWater(rawWater: String) = viewModelScope.launch {
        dao.insertRawWater(RawWaterRecord(rawWater = rawWater))
    }

    fun saveFilteredWater(filteredWater: String) = viewModelScope.launch {
        dao.insertFilteredWater(FilteredWaterRecord(filteredWater = filteredWater))
    }

    fun saveClarifiedWater(clarifiedWater: String) = viewModelScope.launch {
        dao.insertClarifiedWater(ClarifiedWaterRecord(clarifiedWater = clarifiedWater))
    }

    fun loadRawEntries() {
        _isLoadingCloudRecords.value = true
        _cloudRecordsError.value = null

        amplifyService.getRawEntries(
            onSuccess = { entries ->
                _cloudRawWaterRecords.value = entries
                _isLoadingCloudRecords.value = false
            },
            onError = { error ->
                _cloudRecordsError.value = error
                _isLoadingCloudRecords.value = false
            }
        )
    }
}