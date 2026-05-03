package com.example.aguadatos_v2.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    // Coagulant
    @Insert
    suspend fun insertCoagulant(record: CoagulantRecord)

    @Query("SELECT * FROM coagulant_records ORDER BY timestamp DESC")
    fun getAllCoagulant(): Flow<List<CoagulantRecord>>

    // Chlorine
    @Insert
    suspend fun insertChlorine(record: ChlorineRecord)

    @Query("SELECT * FROM chlorine_records ORDER BY timestamp DESC")
    fun getAllChlorine(): Flow<List<ChlorineRecord>>

    // Raw Water
    @Insert
    suspend fun insertRawWater(record: RawWaterRecord)

    @Query("SELECT * FROM raw_water_records ORDER BY timestamp DESC")
    fun getAllRawWater(): Flow<List<RawWaterRecord>>

    // Filtered Water
    @Insert
    suspend fun insertFilteredWater(record: FilteredWaterRecord)

    @Query("SELECT * FROM filtered_water_records ORDER BY timestamp DESC")
    fun getAllFilteredWater(): Flow<List<FilteredWaterRecord>>

    // Clarified Water
    @Insert
    suspend fun insertClarifiedWater(record: ClarifiedWaterRecord)

    @Query("SELECT * FROM clarified_water_records ORDER BY timestamp DESC")
    fun getAllClarifiedWater(): Flow<List<ClarifiedWaterRecord>>
}