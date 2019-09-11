package edu.gwu.android.metroidapp.Alerts

import android.support.v7.widget.RecyclerView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import edu.gwu.android.metroidapp.R
import android.support.v7.widget.LinearLayoutManager
import edu.gwu.android.metroidapp.MetroInformation.MetroManager
import android.widget.Toast
import android.support.v7.app.AlertDialog
import android.widget.ArrayAdapter



class AlertsActivity : AppCompatActivity(),IncidentsAdapter.OnRowClickListener {



    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alerts)

        title = "Incidents"

        recyclerView = findViewById(R.id.AlertsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        MetroManager().retrieveAlerts(
                successCallback = { AlertsList ->
                    runOnUiThread {
                        recyclerView.adapter = IncidentsAdapter(AlertsList, this)

                        if(AlertsList.size<1){
                            Toast.makeText(this, "No alerts available", Toast.LENGTH_LONG).show()
                        }
                    }
                },
                errorCallback = {
                    runOnUiThread {
                        Toast.makeText(this, "No alerts available", Toast.LENGTH_LONG).show()
                    }
                }
        )

    }

    override fun onRowItemClicked(incident: Incidents) {
        // Data
        val choicesList = listOf("A", "B", "C")

        // Adapts the data to a UI
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice)
        arrayAdapter.addAll(choicesList)

        /*
        AlertDialog.Builder(this)
                .setTitle("Choose One")
                .setAdapter(arrayAdapter) { dialog, index ->
                    Toast.makeText(this, "You picked ${choicesList[index]}", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("CANCEL") { dialog, id ->
                    dialog.dismiss()
                }
                .show()

                */
    }


}





