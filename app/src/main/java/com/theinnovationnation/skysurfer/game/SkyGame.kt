package com.theinnovationnation.skysurfer.game

import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import com.theinnovationnation.skysurfer.R
import kotlin.random.Random


class SkyGame() {
    var theme: String = ""
    var difficulty: String = ""
    private var onGameOverListener: OnGameOverListener? = null
    private lateinit var hDisp: TextView
    private var surfaceHeight: Int = 0
    private var surfaceWidth: Int = 0
    private lateinit var svgDrawable: Drawable

    fun setCanvasSize( canvas: Canvas) {
        this.surfaceHeight = canvas.height
        this.surfaceWidth = canvas.width
    }

    fun setTextView( textView: TextView) {
        hDisp = textView
    }

    fun setDrawable ( drawable: Drawable ) {
        svgDrawable = drawable
    }

    fun setOnGameOverListener(listener: OnGameOverListener) {
        this.onGameOverListener = listener
    }


    // NUMBER OF BIRDS ON SCREEN
    private var numBirds = 12


    private lateinit var surfer: Surfer
    private var birdList = mutableListOf<Bird>()

    private var surferHeight = 0

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var gameOver = false

    fun initialize() {

        numBirds = when (difficulty) {
            "Easy" -> 16
            "Medium" -> 12
            else -> 8
        }

        surfer = Surfer(surfaceWidth, surfaceHeight)

        paint.textSize = 90f
        paint.color = Color.RED

        val birdY = surfaceHeight / (numBirds + 1)

        birdList = mutableListOf<Bird>()
        // Add birds at random locations, and alternate initial direction
        for (c in 1..numBirds) {
            val initialRight = c % 2 == 0
            birdList.add(
                Bird(
                    Random.nextInt(surfaceWidth), birdY * c, initialRight,
                    surfaceWidth, surfaceHeight, randomBird(), svgDrawable
                )
            )
        }

        newGame()
    }


    fun newGame() {
        gameOver = false

        // Reset surfer at the top of the screen
        surfer.setCenter(surfaceWidth / 2, 600)

        // Reset Score
        surferHeight = 0

        surfer.jumpIndex = 0

        // Reset walls at random spots
        for (bird in birdList) {
            bird.relocate(Random.nextInt(surfaceWidth))
        }
    }

    fun update(velocity: PointF) {
        if (gameOver) return

        // Move surfer and walls
        surfer.move(velocity, birdList)
        surferHeight -= surfer.surferSpeed
        val text = "$surferHeight m"
        hDisp.text = text

        val birdY = if (surfer.isAtThreshold) -surfer.surferSpeed else 0

        for (bird in birdList) {
            bird.move(birdY)

            // Check if bird is below screen
            if (bird.y > surfaceHeight) {
                val newBirdType = randomBird()
                println(theme)
                val color = if ( theme == "lightMode" ) newBirdType.lightTheme.toInt() else newBirdType.darkTheme.toInt()
                bird.paint.color = color// Update the bird's color
                DrawableCompat.setTintList(bird.svgBird, ColorStateList.valueOf(color))
                bird.birdType = newBirdType
                bird.isVisible = true
                bird.relocate(Random.nextInt(surfaceWidth))
                bird.y = 0
                bird.rect.offsetTo(bird.rect.left, 0)

            }
        }



        // Check for lose
        if (surfer.bottom >= surfaceHeight) {
            gameOver = true
            // save stats
            println("game over")
            onGameOverListener?.onGameOver(arrayOf( surferHeight, 1, surfer.platformsLandedOn+1, surfer.platformsLandedOn ))
        }
    }

    fun draw(canvas: Canvas) {

        // Wipe canvas clean
        val color = if ( theme == "lightMode" ) Color.parseColor("#FF48CCDB") else Color.parseColor("#FF36393E")
        canvas.drawColor( color )

        // Draw surfer and walls
        surfer.draw(canvas)
        for (bird in birdList) {
            bird.draw(canvas)
        }

    }
}
