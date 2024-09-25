package com.k3nda.mapboxv11withspatialitedb

import androidx.room.Entity
import androidx.room.PrimaryKey
import co.anbora.labs.spatia.geometry.Geometry

@Entity(tableName = "area")
data class AreaEntity(
    @PrimaryKey
    val id: String,
    val geometrySpatial: Geometry,
)

data class GeoJsonObject(val id: String, val geoJson: String)
