package com.agiledeveloper.airports

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.airport_info.view.*

class AirportAdapter : RecyclerView.Adapter<AirportViewHolder>() {
    private val airports = mutableListOf<Airport>()

    // RecyclerView が何行作ればいいのか知るために。 +1 は行ヘッダ用
    override fun getItemCount() = airports.size + 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirportViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.airport_info, parent, false)
        return AirportViewHolder(view)
    }

    override fun onBindViewHolder(holder: AirportViewHolder, position: Int) {
        // position == 0 のときはデフォルトのテキストが出る (Code, Name, Temp, Delay)
        if (position > 0) holder.bind(airports[position - 1])
    }

    fun updateAirportsStatus(updatedAirports: List<Airport>) {
        airports.apply {
            clear()
            addAll(updatedAirports)
        }

        // RecyclerView のリフレッシュをトリガーする
        // そうしたら、RecyclerView は getItemCount() を呼んで得られた回数、 onCreateViewHolder() を
        // 呼んで ViewHolder を作り、 onBindViewHolder() を呼んで行とデータを bind する。
        notifyDataSetChanged()
    }
}

class AirportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(airport: Airport) {
        val (code, name, delay, weather) = airport
        val clock = if (delay) "\uD83D\uDD52" else ""

        itemView.apply {
            airportCode.text = code
            airportName.text = name
            airportTemperature.text = weather.temperature.firstOrNull()
            airportDelay.text = clock
        }
    }
}