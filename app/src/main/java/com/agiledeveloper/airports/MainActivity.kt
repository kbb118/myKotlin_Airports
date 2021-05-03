package com.agiledeveloper.airports

import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val airportCodes = mutableListOf<String>()

    private suspend fun updateAirportStatus() {
        val airports = getAirportStatus(airportCodes)
        val airportAdapter = airportStatus.adapter as AirportAdapter
        airportAdapter.updateAirportsStatus(airports)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // FIXME なぜか動かないので、最初から押せるようにしておく
        // 空港コードに何か記入されたら押せるようにする
        // addAirportCode.isEnabled = false
        addAirportCode.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                addAirportCode.isEnabled = airportCode.text.isNotBlank()
            }

            // TextWatcher の残りのコールバックは使わないので空にする
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                /* no-op */
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                /* no-op */
            }
        })


        addAirportCode.setOnClickListener {
            airportCodes.add(airportCode.text.toString())
            airportCode.setText("")

            launch { updateAirportStatus() }
        }

        airportStatus.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = AirportAdapter()
        }
    }
}