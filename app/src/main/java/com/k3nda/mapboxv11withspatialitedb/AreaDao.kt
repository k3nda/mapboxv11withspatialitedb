package com.k3nda.mapboxv11withspatialitedb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.k3nda.mapboxv11withspatialitedb.AreaEntity

@Dao
interface AreaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(vararg areas: AreaEntity)

    @Query("SELECT * FROM area WHERE id=:id")
    suspend fun findById(id: String): AreaEntity?

    @Query("DELETE FROM area")
    suspend fun clear()
}
