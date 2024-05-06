package com.theinnovationnation.skysurfer.game

import android.app.Application
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import kotlin.math.min
import kotlin.random.Random

fun isLightThemeSelected(themeValue: Long): Boolean {
    return BirdType.values().any { it.lightTheme == themeValue }
}

// true = Day
// false = Night
enum class BirdType(val lightTheme: Long, val darkTheme: Long) {
    EVIL_BIRD(0xFF402B4C, 0xFFB2A4D4),
    SHY_BIRD(0x48CCFF, 0x3A3A3E),
    BOUNCE_BIRD(0xFFffd555, 0xFFFFFFFF),
    FAST_BIRD(0xFFf92227, 0xFFFFFFFF),
    SLOW_BIRD(0xFF18206b, 0xFFFFFFFF)
}

fun randomBird(): BirdType {

    return when(Random.nextInt(0, 10)) {
        1, 2, 3 -> BirdType.SLOW_BIRD
        4, 5, 6 -> BirdType.FAST_BIRD
        7, 8 -> BirdType.SHY_BIRD
        9 -> BirdType.BOUNCE_BIRD
        else -> BirdType.EVIL_BIRD
    }

}

// It looks like a bird, but it is not a bird because it's in your phone
class Bird(var x: Int, var y: Int, initialDirectionRight: Boolean,
           private var surfaceWidth: Int, surfaceHeight: Int, var birdType: BirdType) {

    private val birdSpeed = 5

    var rect: Rect
    private var moveDistance = 0
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    public var isVisible = true

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
        moveDistance = if (initialDirectionRight) birdSpeed else -birdSpeed

        // Wall color

        paint.color = birdType.lightTheme.toInt()
    }

    fun relocate(xDistance: Int) {

        // Move wall to a new x location
        val x = min(xDistance, surfaceWidth - rect.width())
        rect.offsetTo(x, rect.top)
    }

    fun move( dy: Int ) {

        y += dy

        // Move wall right or left
        rect.offset(moveDistance * speedMod(birdType), dy)

        // Bounce wall off surface edges
        if (rect.right > surfaceWidth) {
            rect.offsetTo(surfaceWidth - rect.width(), rect.top)
            moveDistance *= -1
        } else if (rect.left < 0) {
            rect.offsetTo(0, rect.top)
            moveDistance *= -1
        }
    }

    private fun speedMod(birdType: BirdType) : Int {
        return when (birdType) {
            BirdType.FAST_BIRD -> 2
            BirdType.SLOW_BIRD -> 1
            else -> 0
        }
    }

    fun draw(canvas: Canvas) {
        if (!isVisible) paint.color = 0x0
        canvas.drawRect(rect, paint)
    }
}

