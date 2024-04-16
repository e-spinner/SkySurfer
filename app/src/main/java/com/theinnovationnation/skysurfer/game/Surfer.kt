package com.theinnovationnation.skysurfer.game

import android.graphics.*
import java.security.KeyStore.TrustedCertificateEntry

const val SURFER_RADIUS = 20
const val SURFER_COLOR = 0xffaaaaff
const val HEIGHT_THRESHOLD = 0.45

class Surfer (private val surfaceWidth: Int, private val surfaceHeight: Int) {

    // flag to track it the surfer is at threshold value
    public var isAtThreshold = false

    public var surferSpeed = 0

    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var center = Point(SURFER_RADIUS, SURFER_RADIUS)



    private val jumpPath = generateJumpPath(surfaceHeight)
    private var jumpIndex = 0 // Index to track the current position in the jump path

    val bottom
        get() = center.y + SURFER_RADIUS

    init {
        paint.color = SURFER_COLOR.toInt()
    }

    fun setCenter(x: Int, y: Int) {

        // Move circle center
        center.x = x
        center.y = y
    }

    private fun generateJumpPath(surfaceHeight: Int): List<Int> {
        val maxHeight = surfaceHeight / 2
        val jumpPath = mutableListOf<Int>()



        val steps = mutableListOf<Int>()
        for (i in 10..160 step 3) {
            val stepHeight = maxHeight / i
            steps.add(stepHeight)
        }
        jumpPath.add(0)
        jumpPath.addAll(steps.map { it * -1 })
        jumpPath.add(0)
        jumpPath.add(0)
        jumpPath.add(0)
        jumpPath.addAll(steps.reversed())

        println(jumpPath)

        return jumpPath
    }

    private var isFalling: Boolean = false

    fun move(velocity: PointF, birdList: MutableList<Bird>) {
        // Move surfer's center by velocity in the X direction
        center.offset(-velocity.x.toInt() * 6 , 0)

        // Check for collision with birds or ground
        for (bird in birdList) {
            if (this.intersects(bird, isFalling) || this.bottom >= surfaceHeight) {
                jumpIndex = 1
            }
        }

        // Calculate the jumping effect in the Y direction
        if (jumpIndex > jumpPath.size-1 ) {
            jumpIndex = jumpPath.size-1
        }
        var jumpY = jumpPath[jumpIndex]

        isFalling = jumpY > 0
        surferSpeed = jumpY


        // Check if surfer has reached threshold height
        if ( center.y <= HEIGHT_THRESHOLD * surfaceHeight && !isFalling ) {
            isAtThreshold = true
            jumpY = 0
        }
        else { isAtThreshold = false }

        // Apply the Y movement
        center.offset(0, jumpY)

        jumpIndex++



        // Don't go too far down or up
        if (center.y > surfaceHeight - SURFER_RADIUS) {
            center.y = surfaceHeight - SURFER_RADIUS
        } else if (center.y < SURFER_RADIUS) {
            center.y = SURFER_RADIUS
        }

        // Don't go too far right or left
        if (center.x > surfaceWidth - SURFER_RADIUS) {
            center.x = surfaceWidth - SURFER_RADIUS
        } else if (center.x < SURFER_RADIUS) {
            center.x = SURFER_RADIUS
        }
    }


    fun draw(canvas: Canvas) {
        canvas.drawCircle(center.x.toFloat(), center.y.toFloat(), SURFER_RADIUS.toFloat(), paint)
    }

    private fun intersects(bird: Bird, isFalling: Boolean): Boolean {

        // Check if the bottom of the surfer's bounding box intersects with the top of the bird's bounding box
        // and if the surfer's bounding box intersects with the bird's bounding box on the x-axis
        if (    center.y + SURFER_RADIUS >= bird.rect.top &&
                center.y - SURFER_RADIUS <= bird.rect.bottom &&
                center.x + SURFER_RADIUS >= bird.rect.left &&
                center.x - SURFER_RADIUS <= bird.rect.right ) {
            if (isFalling) {
                return true
            }
        }
        return false

    }

}

