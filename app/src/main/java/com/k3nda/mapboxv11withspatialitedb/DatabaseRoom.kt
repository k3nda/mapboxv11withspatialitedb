package com.k3nda.mapboxv11withspatialitedb

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import co.anbora.labs.spatia.builder.SpatiaRoom

@Database(
    /**
     * When changing version delete build folder to make fresh build needed for schema json file generation
     * Test your new version by switching to previous release version, run app, then back to your branch and run app
     */
    version = 1,
    exportSchema = true,
    entities = [
        AreaEntity::class,
    ],
    autoMigrations = [

    ]
)
@TypeConverters()
abstract class DatabaseRoom : RoomDatabase() {

    abstract fun areaDao(): AreaDao

    companion object {

        private const val DATABASE_NAME = "my_db"

        @Volatile
        private var instance: DatabaseRoom? = null

        fun getInstance(context: Context): DatabaseRoom =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context): DatabaseRoom =
            SpatiaRoom.databaseBuilder(context, DatabaseRoom::class.java, DATABASE_NAME)
                .addMigrations(
                )
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        db.query("SELECT RecoverGeometryColumn('area', 'geometrySpatial', 4326, 'GEOMETRY', 'XY');").moveToNext()
                        db.query("SELECT CreateSpatialIndex('area', 'geometrySpatial');").moveToNext()
                    }
                })
                .build()
    }
}
