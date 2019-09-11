package edu.gwu.android.metroidapp.MetroInformation

import java.io.Serializable

data class stationList(
        val Name: String,
        val Code: String,
        val Lat: Double,
        val Lon: Double,
        val LineCode1: String,
        val LineCode2: String?,
        val LineCode3: String?,
        val LineCode4: String?
): Serializable