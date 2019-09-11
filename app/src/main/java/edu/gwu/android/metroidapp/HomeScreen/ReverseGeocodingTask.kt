package edu.gwu.android.metroidapp.HomeScreen

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.AsyncTask
import android.util.Log
import com.google.android.gms.maps.model.LatLng

class ReverseGeocodingTask(  context: Context,
private val onSuccessListener: (Address) -> Unit,
private val onErrorListener: (Exception) -> Unit
) : AsyncTask<String, Void, Address?>() {
    private val geocoder = Geocoder(context)
    /**
     * Executed on a background thread -- perform reverse geocoding.
     */
    override fun doInBackground(vararg params: String): Address? {
        // We will only ever geocode one LatLng at a time, but the doInBackground param is required
        // to be a vararg.
        val firstAddress = params[0]

        // Geocoder can have issues on an emulator
        // https://issuetracker.google.com/issues/64418751
        return try {
            // Perform reverse geocoding, we only care about the 1st result.
            val results = geocoder.getFromLocationName(firstAddress, 3)
            if (results.isEmpty()) null else results[0]
        } catch (exception: Exception) {
            exception.printStackTrace()
            Log.e("ReverseGeocodingTask", "Exception: $exception")
            null
        }
    }

    /**
     * Executed on the UI thread. Invoke the appropriate listener.
     */
    override fun onPostExecute(result: Address?) {
        if (result != null) onSuccessListener.invoke(result)
        else onErrorListener.invoke(Exception("Failed to reverse geocode the location!"))
    }
}
