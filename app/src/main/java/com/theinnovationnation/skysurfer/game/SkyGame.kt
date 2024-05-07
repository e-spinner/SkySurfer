package com.theinnovationnation.skysurfer.game

import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import kotlin.random.Random


class SkyGame() {
    var theme: String = ""
    var difficulty: String = ""
    private var onGameOverListener: OnGameOverListener? = null
    private lateinit var hDisp: TextView
    private var surfaceHeight: Int = 0
    private var surfaceWidth: Int = 0
    private lateinit var birdDrawable: Drawable
    private lateinit var surferDrawable: Drawable

    fun setCanvasSize( canvas: Canvas) {
        this.surfaceHeight = canvas.height
        this.surfaceWidth = canvas.width
    }

    fun setTextView( textView: TextView) {
        hDisp = textView
    }

    fun setDrawable ( drawable: Drawable, drawable2: Drawable ) {
        birdDrawable = drawable
        surferDrawable = drawable2
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

        surfer = Surfer(surfaceWidth, surfaceHeight, surferDrawable)
        numBirds = when (difficulty) {
            "Easy" -> 16
            "Medium" -> 12
            else -> 8
        }

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
                    surfaceWidth, surfaceHeight, randomBird(), birdDrawable
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


        val birdY = surfaceHeight / (numBirds + 1)
        for (c in 1..numBirds) {
            birdList[c-1].y = birdY * c

        }
        for (bird in birdList) {
            val newBirdType = randomBird()
            println(theme)
            val color = if ( theme == "lightMode" ) newBirdType.lightTheme.toInt() else newBirdType.darkTheme.toInt()
            bird.paint.color = color// Update the bird's color
            DrawableCompat.setTintList(bird.svgBird, ColorStateList.valueOf(color))
            bird.birdType = newBirdType
            bird.isVisible = true
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
            onGameOverListener?.onGameOver(arrayOf( surferHeight, 1, surfer.platformsLandedOn+1, surfer.platformsLandedOn, surfer.totalSlow, surfer.totalFast, surfer.totalBounce, surfer.totalEvil, surfer.totalShy ))
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
