package com.theinnovationnation.skysurfer.game

import android.graphics.*


class Surfer (private val surfaceWidth: Int, private val surfaceHeight: Int) {

    private val surferRadius = 20
    private val surferColor = 0xffaaaaff
    private val heightThreshold = 0.45

    var platformsLandedOn = 0

    // flag to track it the surfer is at threshold value
    public var isAtThreshold = false

    public var surferSpeed = 0

    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var center = Point(surferRadius, surferRadius)

    private val jumpPath = generateJumpPath(surfaceHeight)
    var jumpIndex = 0 // Index to track the current position in the jump path

    val bottom
        get() = center.y + surferRadius

    init {
        paint.color = surferColor.toInt()
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
        for (i in 10..120 step 2) {
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
    private var boost: Float = 1.0F

    fun move(velocity: PointF, birdList: MutableList<Bird>) {
        // Move surfer's center by velocity in the X direction
        center.offset(-velocity.x.toInt() * 6 , 0)


        // Check for collision with birds or ground
        for (bird in birdList) {
            if (this.intersects(bird, isFalling) || this.bottom >= surfaceHeight) {
                jumpIndex = 1
                platformsLandedOn++
                println("platforms: $platformsLandedOn")


                when (bird.birdType) {
                    BirdType.SHY_BIRD -> bird.isVisible = false
                    BirdType.EVIL_BIRD -> jumpIndex = jumpPath.size-1
                    BirdType.BOUNCE_BIRD -> boost = 5.0F
                    else -> {}
                }
            }
        }

        // Calculate the jumping effect in the Y direction
        if (jumpIndex > jumpPath.size-1 ) {
            jumpIndex = jumpPath.size-1
        }
        var jumpY = jumpPath[jumpIndex]

        isFalling = jumpY > 0
        if(isFalling) boost = 1.0F
        surferSpeed = (jumpY * boost).toInt()


        // Check if surfer has reached threshold height
        if ( center.y <= heightThreshold * surfaceHeight && !isFalling ) {
            isAtThreshold = true
            jumpY = 0
        }
        else { isAtThreshold = false }

        // Apply the Y movement
        center.offset(0, jumpY)

        jumpIndex++



        // Don't go too far down or up
        if (center.y > surfaceHeight - surferRadius) {
            center.y = surfaceHeight - surferRadius
        } else if (center.y < surferRadius) {
            center.y = surferRadius
        }

        // Don't go too far right or left
        if (center.x > surfaceWidth - surferRadius) {
            center.x = surfaceWidth - surferRadius
        } else if (center.x < surferRadius) {
            center.x = surferRadius
        }
    }


    fun draw(canvas: Canvas) {
        canvas.drawCircle(center.x.toFloat(), center.y.toFloat(), surferRadius.toFloat(), paint)
    }

    private fun intersects(bird: Bird, isFalling: Boolean): Boolean {

        // Check if the bottom of the surfer's bounding box intersects with the top of the bird's bounding box
        // and if the surfer's bounding box intersects with the bird's bounding box on the x-axis
        if (    center.y + surferRadius >= bird.rect.top &&
                center.y - surferRadius <= bird.rect.bottom &&
                center.x + surferRadius >= bird.rect.left &&
                center.x - surferRadius <= bird.rect.right ) {
            if (isFalling) {
                return true
            }
        }
        return false

    }

}

