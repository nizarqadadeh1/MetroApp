package edu.gwu.android.metroidapp.Alerts


import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import edu.gwu.android.metroidapp.R

import android.widget.TextView


class IncidentsAdapter(
        private val incidents: List<Incidents>,
        private val rowClickListener: AlertsActivity
) : RecyclerView.Adapter<IncidentsAdapter.ViewHolder>() {

    /**
     * Used for the Activity to receive callbacks when a row is clicked.
     * You can also do this by having the Activity pass a lambda instead:
     *      private val rowClickListener: (Tweet) -> Unit
     */
    interface OnRowClickListener {
        fun onRowItemClicked(incident: Incidents)
    }

    // RecyclerView wants to render a new row that hasn't been created before
    // Load the XML layout and return a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflater is an object that loads & parses XML, you get it from a Context
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.incident_list_layout, parent, false)
        return ViewHolder(view)
    }

    // How many total rows to render in your list
    override fun getItemCount(): Int {
        return incidents.size
    }

    // List is ready to render a row at position and it gives you the ViewHolder
    // So you just fill it with content
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentIncident = incidents[position]
        holder.descriptionTextView.text = currentIncident.Description
        holder.linesAffectedTextView.text = currentIncident.LinesAffected

       // holder.cardView.setOnClickListener {
            rowClickListener.onRowItemClicked(currentIncident)
        //}
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //val cardView: CardView = view.findViewById(R.id.card_view_layout)
        val descriptionTextView: TextView = view.findViewById(R.id.description)
        val linesAffectedTextView: TextView = view.findViewById(R.id.linesAffected)


    }
}
