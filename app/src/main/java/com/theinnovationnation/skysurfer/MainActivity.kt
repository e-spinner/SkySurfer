package com.theinnovationnation.skysurfer

import android.graphics.drawable.Drawable
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.theinnovationnation.skysurfer.game.SkyGame
import com.theinnovationnation.skysurfer.ui.GameFragment
import com.theinnovationnation.skysurfer.ui.SettingsFragment
import com.theinnovationnation.skysurfer.ui.StatsFragment

class MainActivity : AppCompatActivity() {
    private  var skyGame = SkyGame()

    private lateinit var gameFragment: GameFragment
    private lateinit var statsFragment: StatsFragment
    private lateinit var settingsFragment: SettingsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        println("Main Activity onCreate skygame: $skyGame")
        val birdDrawable = AppCompatResources.getDrawable(this, R.drawable.pretty_bird_svg) as Drawable
        val surferDrawable = AppCompatResources.getDrawable(this, R.drawable.noun_surfer_384446) as Drawable
        skyGame.setDrawable(birdDrawable, surferDrawable)
        // Initialize fragments after skyGame is initialized
        initializeFragments()

        // Find the BottomNavigationView
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.nav_view)

        // Set up the BottomNavigationView to listen for item selections
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            val selectedFragment: Fragment = when (item.itemId) {
                R.id.navigation_game -> gameFragment
                R.id.navigation_stats -> statsFragment
                R.id.navigation_settings -> settingsFragment
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

    private fun initializeFragments() {
        println("MainActivity fragments initialized, skygame: $skyGame")
        gameFragment = GameFragment.newInstance(skyGame)
        statsFragment = StatsFragment()
        settingsFragment = SettingsFragment()


        skyGame.setOnGameOverListener(gameFragment)
    }
}


