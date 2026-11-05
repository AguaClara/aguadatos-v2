package com.example.aguadatos_v2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

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
        ClarifiedWaterRecord::class,
        PlantFlowRecord::class,
        ColorRecord::class,
        TankVolumesRecord::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `plant_flow_records` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `timestamp` INTEGER NOT NULL,
                        `plantFlow` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `color_records` (
                    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    `timestamp` INTEGER NOT NULL,
                    `color` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `tank_volumes_records` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `timestamp` INTEGER NOT NULL,
                        `coagulantTank1` TEXT NOT NULL,
                        `coagulantTank2` TEXT NOT NULL,
                        `chlorineTank1` TEXT NOT NULL,
                        `chlorineTank2` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
