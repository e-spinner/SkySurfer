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
import com.theinnovationnation.skysurfer.game.OnGameOverListener
import com.theinnovationnation.skysurfer.game.SkyGame
import com.theinnovationnation.skysurfer.game.SkyView

class GameFragment(private var skyGame: SkyGame? = null) : Fragment(), SensorEventListener, OnGameOverListener {

    private fun saveStats(highScore: Int, attemptsMade: Int, jumpsMade: Int, platformsLanded: Int){
        println("gameFragment saveStats called")
        println("Activity: $activity")
        val sharedPref = activity?.getSharedPreferences("myStats", Context.MODE_PRIVATE) ?: return
        println("SharedPreferences: $sharedPref")
        with (sharedPref.edit()) {
            // Saving data to XML file using parameters passed to function
            val highScoreVal = sharedPref.getInt(getString(R.string.high_score), 0)
            val attemptsMadeVal = sharedPref.getInt(getString(R.string.attempts_made), 0)
            val platformsLandedVal = sharedPref.getInt(getString(R.string.platforms_landed), 0)
            val jumpsMadeVal = sharedPref.getInt(getString(R.string.jumps_made), 0)

            println("platforms: $platformsLanded, old platforms: $platformsLandedVal")

            if (highScore > highScoreVal) {
                putInt(getString(R.string.high_score), highScore)
            }
            putInt(getString(R.string.attempts_made), attemptsMade + attemptsMadeVal)
            putInt(getString(R.string.jumps_made), jumpsMade + jumpsMadeVal)
            putInt(getString(R.string.platforms_landed), platformsLanded + platformsLandedVal)
            apply()
        }
    }

    private var stats: Array<Int> = arrayOf()


    private fun updateStats(newStats: Array<Int>) {
        stats = newStats
        println("inside updateStats")
        saveStats(stats[0], stats[1] , stats[2] ,stats[3])
    }

    override fun onGameOver(stats: Array<Int>) {
        println("GameFragment game over")
        updateStats(stats)
    }

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private lateinit var surfaceView: SkyView
    private lateinit var heightDisplay: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("GameFragment onCreate skygame: $skyGame")

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
        println("GameFragment onCreateView skygame: $skyGame")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        // Now that the view is inflated, you can find views within it
        surfaceView = view.findViewById(R.id.sky_surface)
        heightDisplay = view.findViewById(R.id.height_display)
        skyGame?.setTextView(heightDisplay)
        skyGame?.let { surfaceView.setSkyGame(it) }


        // For testing in the emulator
        surfaceView.setOnClickListener {
            println("shake")
            surfaceView.shake()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("GameFragment onViewCreated skygame: $skyGame")
        // Now that the view is created, it's safe to use skyGame
        skyGame?.let { surfaceView.setSkyGame(it) }
    }

    companion object {
        @JvmStatic
        fun newInstance(skyGame: SkyGame) =
            GameFragment(skyGame).apply {
            }
    }

}