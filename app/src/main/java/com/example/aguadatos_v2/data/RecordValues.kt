package com.example.aguadatos_v2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Collection of data classes that store operator data entry from each of the submission screens
 * on the Home page. Collects information from Coagulant Dosage, Chlorine Dosage, Raw Water
 * Turbidity, Filtered Water Turbidity, and Clarified Water Turbidity pages.
 */
@Entity(tableName = "coagulant_records")
data class CoagulantRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val sliderPos: Float,
    val newSliderPos: Float,
    val waterInflow: String,
    val startVolume: String,
    val endVolume: String,
    val timeElapsed: String,
    val sliderPosOverDose: Float,
    val chemFlowRate: String
)

@Entity(tableName = "chlorine_records")
data class ChlorineRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val sliderPos: Float,
    val newSliderPos: Float,
    val waterInflow: String,
    val startVolume: String,
    val endVolume: String,
    val timeElapsed: String,
    val sliderPosOverDose: Float,
    val chemFlowRate: String
)

@Entity(tableName = "raw_water_records")
data class RawWaterRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val rawWater: String
)

@Entity(tableName = "filtered_water_records")
data class FilteredWaterRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val filteredWater: String
)

@Entity(tableName = "clarified_water_records")
data class ClarifiedWaterRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val clarifiedWater: String
)

@Entity(tableName = "plant_flow_records")
data class PlantFlowRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val plantFlow: String
)

@Entity(tableName = "color_records")
data class ColorRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val color: String
)

@Entity(tableName = "tank_volumes_records")
data class TankVolumesRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val coagulantTank1: String,
    val coagulantTank2: String,
    val chlorineTank1: String,
    val chlorineTank2: String
)