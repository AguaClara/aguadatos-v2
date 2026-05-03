package com.example.aguadatos_v2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * This is the main Room database for AguaDatos. It is a singleton class that provides access to the
 * local SQLite database used to store operator entry data from each screen.
 *
 * Tables:
 * - coagulant_records: stores entries from the Coagulant Dosage screen
 * - chlorine_records: stores entries from the Chlorine Dosage screen
 * - raw_water_records: stores entries from the Raw Water Turbidity screen
 * - filtered_water_records: stores entries from the Filtered Water Turbidity screen
 * - clarified_water_records: stores entries from the Clarified Water Turbidity screen
 *
 * This database stores data locally on the device only.  It is not connected to the DynamoDB
 * backend. Future work may require a connection with DynamoDB.
 */
@Database(
    entities = [
        CoagulantRecord::class,
        ChlorineRecord::class,
        RawWaterRecord::class,
        FilteredWaterRecord::class,
        ClarifiedWaterRecord::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
