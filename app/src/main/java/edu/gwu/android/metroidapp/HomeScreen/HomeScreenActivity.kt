package edu.gwu.android.metroidapp.HomeScreen

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.text.TextWatcher
import android.location.Geocoder
import android.util.Log
import android.text.Editable
import 	java.net.URI
import android.support.v7.app.AlertDialog
import android.content.pm.PackageManager

import android.location.Address
import android.net.Uri
import android.widget.Toast
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import edu.gwu.android.metroidapp.Alerts.AlertsActivity
import edu.gwu.android.metroidapp.Maps.MapsActivity
import edu.gwu.android.metroidapp.MetroInformation.MetroManager
import edu.gwu.android.metroidapp.MetroInformation.stationInfo
import edu.gwu.android.metroidapp.R
import kotlinx.android.synthetic.main.activity_alerts.*
import kotlinx.android.synthetic.main.activity_home_screen.*

class HomeScreenActivity : AppCompatActivity() {

    private val PREF_FILENAME = "MetroidApp"
    private var currentAddress: Address? = null
    private var endAddress: Address? = null
    private var Radius = 400

    private val PREF_SAVED_STARTLOCATION = "SAVED_START"
    private val PREF_SAVED_ENDLOCATION = "SAVED_END"

    companion object {

        val INTENT_KEY_STARTLOCATION = "LOCATION_START"
        val INTENT_KEY_ENDLOCATION = "LOCATION_END"

    }

    public lateinit var startTextView: EditText

    public lateinit var endTextView: EditText

    public lateinit var searchButton: Button

    private lateinit var AlertsButton: Button

    public lateinit var sendButton: Button
    public lateinit var intentMessage: Button



        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_home_screen)

            //val address1: Address = intent.getParcelableExtra(INTENT_KEY_ENDLOCATION)
            //title = getString(R.string.Location_title, address1.getAddressLine(0))


            val preferences = getSharedPreferences(PREF_FILENAME, Context.MODE_PRIVATE)



            startTextView = findViewById(R.id.startLocation)
            endTextView = findViewById(R.id.endLocation)
            searchButton = findViewById(R.id.search)
            AlertsButton = findViewById(R.id.AlertsButton)
            sendButton = findViewById(R.id.openWith)
            intentMessage = findViewById(R.id.intentMessage)

            startTextView.addTextChangedListener(textWatcher)
            endTextView.addTextChangedListener(textWatcher)

            val savedStartlocation: String = preferences.getString(PREF_SAVED_STARTLOCATION, " ")
            val savedEndlocation: String = preferences.getString(PREF_SAVED_ENDLOCATION, "")


            //val address: Address = intent.getParcelableExtra(INTENT_KEY_STARTLOCATION)
            //title = getString(R.string.Location_title, address.getAddressLine(0))
            //startTextView.setText(savedStartlocation)
            //endTextView.setText(savedEndlocation)

            intentMessage.setOnClickListener {

                val location: String = endLocation.text.toString()
                val intent3 = Intent()
                intent3.action = Intent.ACTION_SEND
                intent3.type = "text/plain"
                intent3.putExtra(Intent.EXTRA_TEXT, location)

                startActivity(Intent.createChooser(intent3, "final location"))
            }

            AlertsButton.setOnClickListener {
                val intent = Intent(this, AlertsActivity::class.java)
                android.widget.Toast.makeText(this, "No Alerts Available", android.widget.Toast.LENGTH_LONG).show()

                startActivity(intent)
            }

            sendButton.setOnClickListener {

                val navigationUri = Uri.parse("google.navigation:q=$endLocation")

                val mapIntent1 = Intent(Intent.ACTION_VIEW, navigationUri)
                mapIntent1.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent1)


                /*
            val compatibleApps = packageManager.queryIntentActivities(
                    intent,
                    PackageManager.MATCH_DEFAULT_ONLY
            )


            val sendIntent = Intent().apply {

                intent.action = Intent.ACTION_SEND
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, "use this for metro")

            }
            startActivity(sendIntent)
    */

            }


            searchButton.setOnClickListener {
                // Save the inputted username (usually would be controlled by a Switch)

                val startLocation = startTextView.text.toString()
                val endLocation = endTextView.text.toString()
                //Log.d("MapsActivity", "I am going to pass " + startLocation + " to the MapsActivity!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")

                //preferences.edit().putString(PREF_SAVED_STARTLOCATION, startLocation).apply()

                //preferences.edit().putString(PREF_SAVED_ENDLOCATION, endLocation).apply()

                val currentAddress = Geocoder(this).getFromLocationName(startLocation, 1).get(0)
                val endAddress = Geocoder(this).getFromLocationName(endLocation, 1).get(0)

                /*
                val builder = AlertDialog.Builder(this)
                builder.setTitle("confirm Location ")
                builder.setMessage("is this correct: $currentAddress")
                        .setPositiveButton("confirm") { dialogInterface: DialogInterface, i: Int -> }

                */

                            /*
            val reverseGeocodingTask = ReverseGeocodingTask(
                    context = this,
                    onSuccessListener = { address -> handleGeocodingSuccess(address)

                    },
                    onErrorListener = { handleGeocodingError()

                    }
            )
            currentAddress = reverseGeocodingTask.execute(startLocation).get()
            //endAddress = reverseGeocodingTask.execute(endLocation).get()

            */
                            Log.d("MapsActivity", "I am going to pass " + currentAddress.latitude + " to the MapsActivity!")
                            //Log.d("MapsActivity", "I am going to pass " +  + " to the MapsActivity")
                            //startLocation = geocoder.getFromLocationName
                            // Start the maps activity, sending it the inputed location
                            //val intent = Intent(this, MapsActivity::class.java)
                            //intent.putExtra(INTENT_KEY_STARTLOCATION, startLocation)
                            //intent.putExtra("startlocation",currentAddress)
                            //startActivity(intent)
                            if (currentAddress != null) {
                                MetroManager().retrieveEntrance(
                                        currentAddress.latitude, currentAddress.longitude, Radius,

                                        successCallback = { EntranceList ->
                                            runOnUiThread {
                                                if (EntranceList.size < 1) {
                                                    android.widget.Toast.makeText(this, "Metro not Availabe", android.widget.Toast.LENGTH_LONG).show()
                                                    return@runOnUiThread
                                                }
                                                val entranceCode = EntranceList[0].StationCode1
                                                Log.d("MapsActivity", "Station code 1 " + EntranceList[0].StationCode1 + " to the MapsActivity!")



                                                if (endAddress != null) {
                                                    Log.d("code1 ", "I am going to pass " + currentAddress.latitude + " to the MapsActivity!" + currentAddress.longitude)
                                                    Log.d("code1 ", "I am going to pass " + endAddress.latitude + " to the MapsActivity!" + endAddress.longitude)
                                                    MetroManager().retrieveEntrance(
                                                            endAddress.latitude, endAddress.longitude, Radius,

                                                            successCallback = { EntranceList1 ->
                                                                runOnUiThread {
                                                                    if (EntranceList1.size < 1) {
                                                                        android.widget.Toast.makeText(this, "Metro not Availabe", android.widget.Toast.LENGTH_LONG).show()
                                                                        return@runOnUiThread
                                                                    }

                                                                    val entranceCode1 = EntranceList1[0].StationCode1

                                                                    Log.d("MapsActivity", "Station code 2 " + EntranceList1[0].StationCode1 + " to the MapsActivity!")
                                                                    Log.d("code1 ", "I am going to pass " + entranceCode + " and code2 !" + entranceCode1)
                                                                    MetroManager().retrievePath(

                                                                            entranceCode1, entranceCode,


                                                                            successCallback = { pathList ->
                                                                                runOnUiThread {
                                                                                    //val pathintent = Intent(this, MapsActivity::class.java)
                                                                                    //pathintent.putExtra("pathList", ArrayList(pathList))


                                                                                    if (pathList.size >= 1) {
                                                                                        //we have a list of stationCodes
                                                                                        MetroManager().retrieveList(
                                                                                                pathList[0].LineCode,

                                                                                                successCallback = { sList ->
                                                                                                    runOnUiThread {

                                                                                                        val mapsintent = Intent(this, MapsActivity::class.java)
                                                                                                        mapsintent.putExtra("stationList", ArrayList(sList))
                                                                                                        mapsintent.putExtra("pathList", ArrayList(pathList))
                                                                                                        //mapsintent.putExtra("maps intent", mapsintent)
                                                                                                        startActivity(mapsintent)


                                                                                                    }
                                                                                                },
                                                                                                errorCallback = {
                                                                                                    runOnUiThread {
                                                                                                        android.widget.Toast.makeText(this, "List error", android.widget.Toast.LENGTH_LONG).show()
                                                                                                    }
                                                                                                }
                                                                                        )
                                                                                    } else {
                                                                                        android.widget.Toast.makeText(this, "No path", android.widget.Toast.LENGTH_LONG).show()
                                                                                    }
                                                                                }
                                                                            },
                                                                            errorCallback = {
                                                                                runOnUiThread {
                                                                                    android.widget.Toast.makeText(this, "No path available", android.widget.Toast.LENGTH_LONG).show()
                                                                                }
                                                                            }

                                                                    )

                                                                }
                                                            },
                                                            errorCallback = {
                                                                runOnUiThread {
                                                                    android.widget.Toast.makeText(this, "No entrance2 available", android.widget.Toast.LENGTH_LONG).show()
                                                                }
                                                            }
                                                    )


                                                }
                                            }
                                        },
                                        errorCallback = {
                                            runOnUiThread {
                                                Toast.makeText(this, "No entrance1 available", Toast.LENGTH_LONG).show()
                                            }
                                        }
                                )
                            }





            }
        }

    private fun handleGeocodingSuccess(address: Address) {
        //progress.visibility = View.GONE
        val latLng = LatLng(address.latitude, address.longitude)
        // A when statement is similar to a switch. In this case, we use it to set an appropriate
        currentAddress = address
        endAddress = address

    }

    private fun handleGeocodingError() {
        //progress.visibility = View.GONE
        Toast.makeText(
                this,
                getString(R.string.no_address_found),
                Toast.LENGTH_SHORT
        ).show()
    }


    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {}

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // Only enable the Login button if both the username & password have been inputted
            val startText: String = startTextView.text.toString()
            val endText: String = endTextView.text.toString()
            searchButton.isEnabled = startText.isNotEmpty() && endText.isNotEmpty()
        }
    }
}



