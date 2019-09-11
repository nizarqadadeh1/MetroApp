package edu.gwu.android.metroidapp.MetroInformation

import java.io.Serializable

data class stationPath(
        val LineCode: String,
        val StationName: String,
        val StationCode: String
):Serializable