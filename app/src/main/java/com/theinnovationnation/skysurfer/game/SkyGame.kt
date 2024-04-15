package com.theinnovationnation.skysurfer.game

import android.graphics.*
import kotlin.random.Random

// NUMBER OF WALLS ON SCREEN
const val NUM_WALLS = 20

class SkyGame(private val surfaceWidth: Int, private val surfaceHeight: Int) {

    private val surfer = Surfer(surfaceWidth, surfaceHeight)
    private val birdList = mutableListOf<Bird>()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var gameOver = false

    init {
        paint.textSize = 90f
        paint.color = Color.RED

        val wallY = surfaceHeight / (NUM_WALLS + 1)

        // Add walls at random locations, and alternate initial direction
        for (c in 1..NUM_WALLS) {
            val initialRight = c % 2 == 0
            birdList.add(
                Bird(
                    Random.nextInt(surfaceWidth), wallY * c, initialRight,
                    surfaceWidth, surfaceHeight
                )
            )
        }

        newGame()
    }

    fun newGame() {
        gameOver = false

        // Reset surfer at the top of the screen
        surfer.setCenter(surfaceWidth / 2, surfaceHeight - SURFER_RADIUS + 100)

        // Reset walls at random spots
        for (wall in birdList) {
            wall.relocate(Random.nextInt(surfaceWidth))
        }
    }

    fun update(velocity: PointF) {
        if (gameOver) return

        // Move surfer and walls
        surfer.move(velocity, birdList)
        for (bird in birdList) {
            bird.move()
        }



        // Check for win
        if (surfer.bottom >= surfaceHeight) {
            gameOver = false
        }
    }

    fun draw(canvas: Canvas) {

        // Wipe canvas clean
        canvas.drawColor(Color.WHITE)

        // Draw surfer and walls
        surfer.draw(canvas)
        for (wall in birdList) {
            wall.draw(canvas)
        }

    }
}
