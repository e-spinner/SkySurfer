package com.theinnovationnation.skysurfer

import android.os.Bundle
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.theinnovationnation.skysurfer.ui.GameFragment
import com.theinnovationnation.skysurfer.ui.SettingsFragment
import com.theinnovationnation.skysurfer.ui.StatsFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.nav_view)

        // Set up the BottomNavigationView to listen for item selections
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            val selectedFragment: Fragment = when (item.itemId) {
                R.id.navigation_game -> GameFragment.newInstance()
                R.id.navigation_stats -> StatsFragment.newInstance()
                R.id.navigation_settings -> SettingsFragment.newInstance()
                // Add more cases for other menu items if needed
                else -> throw IllegalArgumentException("Unknown navigation item")
            }

            // Replace the current fragment with the selected fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, selectedFragment)
                .commit()

            true
        }
    }
}


