package edu.gwu.android.metroidapp.Maps


import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.location.Address
import android.widget.ProgressBar
import android.util.Log


import com.google.android.gms.maps.GoogleMap
import android.graphics.Color.RED
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.MarkerOptions
import edu.gwu.android.metroidapp.MetroInformation.stationInfo
import edu.gwu.android.metroidapp.MetroInformation.stationList
import edu.gwu.android.metroidapp.MetroInformation.stationPath
import edu.gwu.android.metroidapp.R
import com.google.android.gms.maps.model.LatLng

class MapsActivity : AppCompatActivity(), OnMapReadyCallback{

    private lateinit var mMap: GoogleMap

    private lateinit var progress: ProgressBar



    private var currentAddress: Address? = null
    private var endAddress: Address? = null

    companion object {
        // ... because we want this constant to be shared with another class
        val INTENT_KEY_START = "Start"
        val INTENT_KEY_END = "end"
    }
    val finalList = mutableListOf<stationInfo>()
    var color = "RD"
    var lineColor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        //val startlocation = intent.getStringExtra("startlocation")
        //val startlocation = intent.getSerializableExtra(INTENT_KEY_START)
        //geocoder.getFromLocationName(INTENT_KEY_START,2)

        //stationList
        val stationlist: List<stationList> = intent.getSerializableExtra("stationList") as List<stationList>
        val pathlist: List<stationPath> = intent.getSerializableExtra("pathList")as List<stationPath>





        for(i in 0 until pathlist.size) {

            for (j in 0 until stationlist.size) {

                if (pathlist[i].StationCode == stationlist[j].Code) {

                    val stationCode = stationlist[j].Code
                    val lat = stationlist[j].Lat
                    val lon = stationlist[j].Lon
                    val name = stationlist[j].Name
                    val linecode1 = stationlist[j].LineCode1
                    val linecode2 = stationlist[j].LineCode2
                    val linecode3 = stationlist[j].LineCode3
                    val linecode4 = stationlist[j].LineCode4
                     color = stationlist[j].LineCode1

                   finalList.add(

                           stationInfo(
                                   Name = name,
                                   ID = stationCode,
                                   Lat = lat,
                                   Lon = lon,
                                   StationCode1 = linecode1
                           )


                    )

                }

            }
        }



        //title = getString(R.string.welcome_name, startlocation)

        //val endlocation = intent.getStringExtra(INTENT_KEY_END)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val beg = LatLng(finalList[0].Lat, finalList[0].Lon)
        val begName = finalList[0].Name
        val end = LatLng(finalList[finalList.size-1].Lat, finalList[finalList.size-1].Lon)
        val endName = finalList[finalList.size-1].Name

        mMap.addMarker(MarkerOptions().position(beg).title("$begName"))
        mMap.addMarker(MarkerOptions().position(end).title("$endName"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(beg))

        if(color == "RD"){
            lineColor = Color.RED
        }
        else if(color == "BL"){
            lineColor = Color.BLUE
        }
        else if(color == "YL"){
            lineColor = Color.YELLOW
        }
        else if(color == "SV"){
            lineColor = Color.GRAY
        }
        else if(color == "GR"){
            lineColor = Color.GREEN
        }
        else if(color == "OR"){
            lineColor =2551650
        }


        val polylineOptions = PolylineOptions()

        polylineOptions.add(beg)
        for(i in 0 until finalList.size-1){
            val latlon1 = LatLng(finalList[i].Lat, finalList[i].Lon)

            polylineOptions.add(latlon1)

        }
        polylineOptions.add(end)

        mMap.addPolyline(polylineOptions.width(16f).color(lineColor))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(beg))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10F),2000,null)




    }




    }
