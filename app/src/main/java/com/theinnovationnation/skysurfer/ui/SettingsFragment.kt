package com.theinnovationnation.skysurfer.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.theinnovationnation.skysurfer.R
import com.theinnovationnation.skysurfer.game.SkyGame


class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // Find the RadioGroup by its ID
        val themeRadioGroup = view.findViewById<RadioGroup>(R.id.themeRadioGroup)

        // Find the RadioButtons by their IDs
        val lightModeRadioButton = view.findViewById<RadioButton>(R.id.LightMode)
        val darkModeRadioButton = view.findViewById<RadioButton>(R.id.DarkMode)
        val easyButton = view.findViewById<RadioButton>(R.id.easyButton)
        val mediumButton = view.findViewById<RadioButton>(R.id.mediumButton)
        val hardButton = view.findViewById<RadioButton>(R.id.hardButton)


        // Find the clearData button by its ID
        val clearDataButton = view.findViewById<Button>(R.id.clearData)

        // Set an OnClickListener on each RadioButton
        val onClickListener = View.OnClickListener { v ->
            when (v.id) {
                R.id.LightMode -> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        val sharedPref = activity?.getSharedPreferences("theme", Context.MODE_PRIVATE)
                        with(sharedPref!!.edit()) {
                            putString("theme" , "lightMode")
                            apply()
                        }
                    }, 100) // Delay of 100 milliseconds
                }
                R.id.DarkMode -> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        val sharedPref = activity?.getSharedPreferences("theme", Context.MODE_PRIVATE)
                        with(sharedPref!!.edit()) {
                            putString("theme" , "darkMode")
                            apply()
                        }
                    }, 100) // Delay of 100 milliseconds
                }
                R.id.clearData -> {
                    println("clear data button")
                    Handler(Looper.getMainLooper()).postDelayed({
                        val sharedPref = activity?.getSharedPreferences("myStats", Context.MODE_PRIVATE)
                        with (sharedPref!!.edit()) {
                            println("clear working? maybe.")
                            this.putInt(getString(R.string.high_score), 0)
                            this.putInt(getString(R.string.attempts_made), 0)
                            this.putInt(getString(R.string.jumps_made), 0 )
                            this.putInt(getString(R.string.platforms_landed), 0)
                            this.putInt("slow", 0)
                            this.putInt("fast", 0)
                            this.putInt("bounce", 0)
                            this.putInt("evil", 0)
                            this.putInt("shy", 0)
                            this.apply()
                        }

                    }, 100) // Delay of 100 milliseconds
                }
                R.id.easyButton -> {
                    val sharedPref = activity?.getSharedPreferences("difficulty", Context.MODE_PRIVATE)
                    with(sharedPref!!.edit()) {
                        putString("difficulty" , "Easy")
                        apply()
                    }
                }
                R.id.mediumButton -> {
                    val sharedPref = activity?.getSharedPreferences("difficulty", Context.MODE_PRIVATE)
                    with(sharedPref!!.edit()) {
                        putString("difficulty" , "Medium")
                        apply()
                    }
                }
                R.id.hardButton -> {
                    val sharedPref = activity?.getSharedPreferences("difficulty", Context.MODE_PRIVATE)
                    with(sharedPref!!.edit()) {
                        putString("difficulty" , "Hard")
                        apply()
                    }
                }
            }
        }

        // Set the OnClickListener on each RadioButton
        lightModeRadioButton.setOnClickListener(onClickListener)
        darkModeRadioButton.setOnClickListener(onClickListener)
        clearDataButton.setOnClickListener(onClickListener)
        easyButton.setOnClickListener(onClickListener)
        mediumButton.setOnClickListener(onClickListener)
        hardButton.setOnClickListener(onClickListener)

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            SettingsFragment().apply {

            }
    }
}