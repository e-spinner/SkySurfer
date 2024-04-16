package com.theinnovationnation.skysurfer.ui

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.theinnovationnation.skysurfer.R
import com.theinnovationnation.skysurfer.game.SkyView

class GameFragment : Fragment(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private lateinit var surfaceView: SkyView
    private lateinit var heightDisplay: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }


    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this, accelerometer)
    }

    override fun onSensorChanged(event: SensorEvent) {

        // Get accelerometer values
        val x: Float = event.values[0]
        val y: Float = event.values[1]

        // Move the ball
        surfaceView.changeAcceleration(x, y)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Nothing to do
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        // Now that the view is inflated, you can find views within it
        surfaceView = view.findViewById(R.id.sky_surface)
        heightDisplay = view.findViewById(R.id.height_display)
        surfaceView.setTextView(heightDisplay)

        // For testing in the emulator
        surfaceView.setOnClickListener { surfaceView.shake() }

        return view
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            GameFragment().apply {

            }
    }
}