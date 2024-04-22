package com.theinnovationnation.skysurfer.game

import android.graphics.*
import android.widget.TextView
import kotlin.random.Random





class SkyGame(private val surfaceWidth: Int, private val surfaceHeight: Int, val hDisp: TextView) {

    // NUMBER OF BIRDS ON SCREEN
    private val numBirds = 12

    private val surfer = Surfer(surfaceWidth, surfaceHeight)
    private val birdList = mutableListOf<Bird>()

    private var surferHeight = 0

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var gameOver = false

    init {
        paint.textSize = 90f
        paint.color = Color.RED

        val birdY = surfaceHeight / (numBirds + 1)

        // Add birds at random locations, and alternate initial direction
        for (c in 1..numBirds) {
            val initialRight = c % 2 == 0
            birdList.add(
                Bird(
                    Random.nextInt(surfaceWidth), birdY * c, initialRight,
                    surfaceWidth, surfaceHeight, randomBird()
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
        hDisp.text = "$surferHeight m"

        val birdY = if (surfer.isAtThreshold) -surfer.surferSpeed else 0

        for (bird in birdList) {
            bird.move(birdY)

            // Check if bird is below screen
            if (bird.y > surfaceHeight) {
                val newBirdType = randomBird()
                bird.paint.color = newBirdType.color.toInt() // Update the bird's color
                bird.birdType = newBirdType
                bird.isVisible = true
                bird.relocate(Random.nextInt(surfaceWidth))
                bird.y = 0
                bird.rect.offsetTo(bird.rect.left, 0)

            }
        }



        // Check for win
        if (surfer.bottom >= surfaceHeight) {
            gameOver = true
        }
    }

    fun draw(canvas: Canvas) {

        // Wipe canvas clean
        canvas.drawColor(Color.WHITE)

        // Draw surfer and walls
        surfer.draw(canvas)
        for (bird in birdList) {
            bird.draw(canvas)
        }

    }
}
