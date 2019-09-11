package edu.gwu.android.metroidapp.MetroInformation

import java.io.Serializable


data class stationInfo(
        val Name: String,
        val ID: String,
        val Lat: Double,
        val Lon: Double,
        val StationCode1: String
        //val StationCode2: String?

):Serializable

