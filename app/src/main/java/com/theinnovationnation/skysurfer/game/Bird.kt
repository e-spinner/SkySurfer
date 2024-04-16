package com.theinnovationnation.skysurfer.game

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import kotlin.math.min

const val BIRD_SPEED = 5
const val BIRD_COLOR = -0x5501

// It looks like a bird, but it is not a bird because it's in your phone
class Bird(var x: Int, var y: Int, initialDirectionRight: Boolean,
           private var surfaceWidth: Int, surfaceHeight: Int) {

    var rect: Rect
    private var moveDistance = 0
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        // Determine wall dimensions based on surface width and height
        val width = surfaceWidth / 10
        val height = surfaceHeight / 50

        // Make sure wall fits completely on the surface
        x = min(x, surfaceWidth - width)
        y = min(y, surfaceHeight - height)

        // Create wall's rectangle based on location and dimensions
        rect = Rect(x, y, x + width, y + height)

        // Determine how many pixels walls move each iteration
        moveDistance = if (initialDirectionRight) BIRD_SPEED else -BIRD_SPEED

        // Wall color
        paint.color = BIRD_COLOR
    }

    fun relocate(xDistance: Int) {

        // Move wall to a new x location
        val x = min(xDistance, surfaceWidth - rect.width())
        rect.offsetTo(x, rect.top)
    }

    fun move( dy: Int ) {

        y += dy

        // Move wall right or left
        rect.offset(moveDistance, dy)

        // Bounce wall off surface edges
        if (rect.right > surfaceWidth) {
            rect.offsetTo(surfaceWidth - rect.width(), rect.top)
            moveDistance *= -1
        } else if (rect.left < 0) {
            rect.offsetTo(0, rect.top)
            moveDistance *= -1
        }
    }

    fun draw(canvas: Canvas) {
        canvas.drawRect(rect, paint)
    }
}

