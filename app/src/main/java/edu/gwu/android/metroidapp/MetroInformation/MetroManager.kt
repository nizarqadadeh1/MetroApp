package edu.gwu.android.metroidapp.MetroInformation
import edu.gwu.android.metroidapp.Alerts.Incidents
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit
import org.json.JSONObject
import android.widget.Toast


class MetroManager {



    // Using let to do additional logic during instantiation
    val okHttpClient: OkHttpClient = OkHttpClient.Builder().let { builder ->

        // For printing request / response to logs
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(logging)

        // Network timeouts
        builder.connectTimeout(20, TimeUnit.SECONDS)
        builder.readTimeout(20, TimeUnit.SECONDS)
        builder.writeTimeout(20, TimeUnit.SECONDS)
        builder.build()
    }
    fun retrieveAlerts(

            successCallback: (List<Incidents>) -> Unit,
            errorCallback: (Exception) -> Unit
    ){

        val request = Request.Builder()
                .url("https://api.wmata.com/Incidents.svc/json/Incidents")
                .header("api_key","7540181f756c45d58a2a79c1cd554ac9")
                .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                errorCallback(e)
            }
            override fun onResponse(call: Call, response: Response) {
                val responseString: String? = response.body()?.string()
                if (response.isSuccessful && responseString != null) {
                    val incidents = mutableListOf<Incidents>()
                    val json = JSONObject(responseString)
                    val incident = json.getJSONArray("Incidents")

                    //if (incident.length() > 0) {
                        for (i in 0 until incident.length()) {
                            val curr = incident.getJSONObject(i)
                            val descripton1 = curr.getString("Description")
                            val linesAffected1 = curr.getString("LinesAffected")

                            incidents.add(
                                    Incidents(
                                            Description = descripton1,
                                            LinesAffected = linesAffected1
                                    )

                            )
                        }

                        successCallback(incidents)
                   // }
                }
                 else {
                    errorCallback(Exception("no Alerts"))
                }
            }
        })

    }

    //radius: Int
    fun retrieveEntrance( lat: Double, lon : Double, radius: Int,

            successCallback: (List<stationInfo>) -> Unit,
            errorCallback: (Exception) -> Unit
    ){

        val request = Request.Builder()
                .url("https://api.wmata.com/Rail.svc/json/jStationEntrances?Lat=$lat&Lon=$lon&Radius=$radius")
                .header("api_key","7540181f756c45d58a2a79c1cd554ac9")
                .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                errorCallback(e)
            }
            override fun onResponse(call: Call, response: Response) {
                val responseString: String? = response.body()?.string()
                if (response.isSuccessful && responseString != null) {
                    val entrances = mutableListOf<stationInfo>()
                    val json = JSONObject(responseString)
                    val entrance = json.getJSONArray("Entrances")

                    //if (incident.length() > 0) {
                    for (i in 0 until entrance.length()) {
                        val curr = entrance.getJSONObject(i)
                        val stationName = curr.getString("Name")
                        val lat = curr.getDouble("Lat")
                        val lon = curr.getDouble("Lon")
                        val id = curr.getString("ID")
                        val stationCode1 = curr.getString("StationCode1")
                        val stationCode2: String?
                       // if(curr.getString("StationCode2")!= null) {
                        //    val stationCode2 = curr.getString("StationCode2")
                        //}

                        entrances.add(
                                stationInfo(
                                        //Description = descripton1,
                                        //LinesAffected = linesAffected1
                                Name = stationName,
                                Lat = lat,
                                Lon = lon,
                                ID = id,
                                StationCode1 = stationCode1
                                //StationCode2 = stationCode2
                                )

                        )
                    }

                    successCallback(entrances)
                    // }
                }
                else {
                    errorCallback(Exception("no entrances found"))
                }
            }
        })

    }

    fun retrieveList(   lineCode: String?,

                          successCallback: (List<stationList>) -> Unit,
                          errorCallback: (Exception) -> Unit
    ){

        val request = Request.Builder()
                .url("https://api.wmata.com/Rail.svc/json/jStations?$lineCode")
                .header("api_key","7540181f756c45d58a2a79c1cd554ac9")
                .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                errorCallback(e)
            }
            override fun onResponse(call: Call, response: Response) {
                val responseString: String? = response.body()?.string()
                if (response.isSuccessful && responseString != null) {
                    val sList = mutableListOf<stationList>()
                    val json = JSONObject(responseString)
                    val list = json.getJSONArray("Stations")

                    //if (incident.length() > 0) {
                    for (i in 0 until list.length()) {
                        val curr = list.getJSONObject(i)
                        val stationName = curr.getString("Name")
                        val stationCode = curr.getString("Code")
                        val lat = curr.getDouble("Lat")
                        val lon = curr.getDouble("Lon")
                        val lineCode1 = curr.getString("LineCode1")
                        val lineCode2 = curr.getString("LineCode2")
                        val lineCode3 = curr.getString("LineCode3")
                        val lineCode4 = curr.getString("LineCode4")


                        // if(curr.getString("StationCode2")!= null) {
                        //    val stationCode2 = curr.getString("StationCode2")
                        //}

                        sList.add(
                                stationList(
                                        //Description = descripton1,
                                        //LinesAffected = linesAffected1
                                        Name = stationName,
                                        Code = stationCode,
                                        Lat = lat,
                                        Lon = lon,
                                        LineCode1 = lineCode1,
                                        LineCode2 = lineCode2,
                                        LineCode3 = lineCode3,
                                        LineCode4 = lineCode4
                                )

                        )
                    }

                    successCallback(sList)
                    // }
                }
                else {
                    errorCallback(Exception("no List found"))
                }
            }
        })

    }

    fun retrievePath( code1: String, code2: String,

            successCallback: (List<stationPath>) -> Unit,
            errorCallback: (Exception) -> Unit
    ){

        val request = Request.Builder()
                .url("https://api.wmata.com/Rail.svc/json/jPath?FromStationCode=$code1&ToStationCode=$code2")
                .header("api_key","7540181f756c45d58a2a79c1cd554ac9")
                .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                errorCallback(e)
            }
            override fun onResponse(call: Call, response: Response) {
                val responseString: String? = response.body()?.string()
                if (response.isSuccessful && responseString != null) {
                    val sPath = mutableListOf<stationPath>()
                    val json = JSONObject(responseString)
                    val path = json.getJSONArray("Path")

                    //if (incident.length() > 0) {
                    for (i in 0 until path.length()) {
                        val curr = path.getJSONObject(i)
                        val stationName = curr.getString("StationName")
                        val stationCode = curr.getString("StationCode")
                        val lineCode = curr.getString("LineCode")



                        // if(curr.getString("StationCode2")!= null) {
                        //    val stationCode2 = curr.getString("StationCode2")
                        //}

                        sPath.add(
                                stationPath(
                                        //Description = descripton1,
                                        //LinesAffected = linesAffected1
                                        StationName = stationName,
                                        StationCode = stationCode,
                                        LineCode = lineCode

                                )

                        )
                    }

                    successCallback(sPath)
                    // }
                }
                else {
                    errorCallback(Exception("no path found"))
                }
            }
        })

    }
/*

    fun retrieveInfo( stationcode: String,

                          successCallback: (List<stationInfo>) -> Unit,
                          errorCallback: (Exception) -> Unit
    ){

        val request = Request.Builder()
                .url("https://api.wmata.com/Rail.svc/json/jStationInfo[?StationCode]")
                .header("api_key","7540181f756c45d58a2a79c1cd554ac9")
                .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                errorCallback(e)
            }
            override fun onResponse(call: Call, response: Response) {
                val responseString: String? = response.body()?.string()
                if (response.isSuccessful && responseString != null) {
                    val info = mutableListOf<stationList>()
                    val json = JSONObject(responseString)
                    //val entrance = json.getJSONArray("Entrances")

                    //if (incident.length() > 0) {

                        //val curr = entrance.getJSONObject(i)
                        val stationName = json.getString("Name")
                        val code = json.getString("Code")
                        val lat = json.getDouble("Lat")
                        val lon = json.getDouble("Lon")
                        val id = json.getString("ID")
                        val stationCode1 = json.getString("StationCode1")
                        val stationCode2 = json.getString("StationCode2")
                        val stationCode3 = json.getString("StationCode3")
                        val stationCode4 = json.getString("StationCode4")



                    info.add(
                                stationList(
                                        //Description = descripton1,
                                        //LinesAffected = linesAffected1
                                        Name = stationName,
                                        Code = code,
                                        Lat = lat,
                                        Lon = lon,
                                        LineCode1 = stationCode1,
                                        LineCode2 = stationCode2,
                                        LineCode3 = stationCode3,
                                        LineCode4 = stationCode4
                                )

                    )


                    successCallback(info)
                    // }
                }
                else {
                    errorCallback(Exception("no info Found"))
                }
            }
        })

    }
*/


}
