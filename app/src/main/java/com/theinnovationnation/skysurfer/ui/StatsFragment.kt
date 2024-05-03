package com.theinnovationnation.skysurfer.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.theinnovationnation.skysurfer.R
import android.widget.TextView
import android.content.SharedPreferences
import com.theinnovationnation.skysurfer.game.SkyView


/**
 * A simple [Fragment] subclass.
 * Use the [StatsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StatsFragment : Fragment(){

    private lateinit var highScore: TextView
    private lateinit var attemptsMade: TextView
    private lateinit var jumpsMade: TextView
    private lateinit var platformsLanded: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("statsFragment onCreateView")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_stats, container, false)

        // Now that the view is inflated, you can find views within it
        highScore = view.findViewById(R.id.highScore)
        attemptsMade = view.findViewById(R.id.attemptsMade)
        jumpsMade = view.findViewById(R.id.jumpsMade)
        platformsLanded = view.findViewById(R.id.platformsLanded)

        // retrieving values from shared preferences XML file
        val sharedPref = activity?.getSharedPreferences("myStats", Context.MODE_PRIVATE)
        val highScoreVal = sharedPref?.getInt(getString(R.string.high_score), 0)
        val attemptsMadeVal = sharedPref?.getInt(getString(R.string.attempts_made), 0)
        val platformsLandedVal = sharedPref?.getInt(getString(R.string.platforms_landed), 0)
        val jumpsMadeVal = sharedPref?.getInt(getString(R.string.jumps_made), 0)

        // Making String Variables to update textview since it was yelling at me to
        val highScoreText = "High Score: " + highScoreVal
        val attemptsMadeText = "Attempts Made: " + attemptsMadeVal
        val jumpsMadeText = "Jumps Made: " + jumpsMadeVal
        val platformsLandedText  = "Platforms Landed On: " + platformsLandedVal

        // Updates TextViews to hold correct Stats
        highScore.setText(highScoreText)
        attemptsMade.setText(attemptsMadeText)
        platformsLanded.setText(platformsLandedText)
        jumpsMade.setText(jumpsMadeText)

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StatsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            StatsFragment().apply {

            }
    }
}