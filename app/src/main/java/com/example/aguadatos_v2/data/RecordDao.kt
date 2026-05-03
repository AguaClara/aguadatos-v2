package com.example.aguadatos_v2.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * RecordDao (Data Access Object)
 *
 * This defines all database operations for the AguaDatos Records page. Each screen that collects
 * operator entry data has a corresponding insert function here.
 */
@Dao
interface RecordDao {
    // Coagulant

    // Inserts a new Coagulant Dosage entry into the database.
    @Insert
    suspend fun insertCoagulant(record: CoagulantRecord)

    // Returns all Coagulant Dosage entries, ordered by timestamp in descending order.
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