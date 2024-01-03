package com.weather.forecast.clearsky.mainscreen.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weather.forecast.clearsky.R

class ForecastRecyclerViewAdapter(private var forecastPrediction: Array<Array<String>>,private var calender: List<String>) :
    RecyclerView.Adapter<ForecastRecyclerViewAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
         val calender : TextView
         val max_temp : TextView
         val min_temp : TextView
         val avg_humidity : TextView
         val forecast_condition : TextView


        init {
            // Define click listener for the ViewHolder's View
            calender = view.findViewById(R.id.calender)
            max_temp = view.findViewById(R.id.max_temp)
            min_temp = view.findViewById(R.id.min_temp)
            avg_humidity = view.findViewById(R.id.avg_humidity)
            forecast_condition = view.findViewById(R.id.forecast_condition)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.forecast_recycler_view, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.calender.text = calender[position]
        viewHolder.max_temp.text = "Max Temp : ${forecastPrediction[position][0]}"
        viewHolder.min_temp.text = "Min Temp : ${forecastPrediction[position][1]}"
        viewHolder.avg_humidity.text = "Avg Humidity : ${forecastPrediction[position][2]}"
        viewHolder.forecast_condition.text = forecastPrediction[position][3]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = forecastPrediction.size

    fun setItems(forecastPrediction: Array<Array<String>>, calender: List<String>) {
        this.forecastPrediction = forecastPrediction
        this.calender = calender
        notifyDataSetChanged()
    }

}