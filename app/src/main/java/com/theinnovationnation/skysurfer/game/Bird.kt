package com.theinnovationnation.skysurfer.game

import android.app.Application
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.content.res.ColorStateList
import kotlin.math.min
import android.graphics.drawable.VectorDrawable
import androidx.core.graphics.drawable.DrawableCompat
import kotlin.random.Random


enum class BirdType(val lightTheme: Long, val darkTheme: Long) {
    EVIL_BIRD(0xFF48AADB, 0xFFA200ff),
    SHY_BIRD(0x48AADB, 0x686871),
    BOUNCE_BIRD(0xFFFFD555, 0xFFDAD30B),
    FAST_BIRD(0xFFf92227, 0xFFE3326D),
    SLOW_BIRD(0xFFA634D6, 0xFF000000)
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

// allows mutable copies of drawables to be made by extending Drawable
fun Drawable.copy(): Drawable {
    return this.constantState?.newDrawable()?.mutate()?: this
}


// It looks like a bird, but it is not a bird because it's in your phone
class Bird(var x: Int, var y: Int, initialDirectionRight: Boolean,
           private var surfaceWidth: Int, surfaceHeight: Int, var birdType: BirdType, private var svgDrawable: Drawable) {

    private val birdSpeed = 5
    val svgBird = svgDrawable.copy()

    var rect: Rect
    private var moveDistance = 0
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var isVisible = true

    init {
        val width = surfaceWidth / 10
        val height = surfaceHeight / 20
        x = min(x, surfaceWidth - width)
        y = min(y, surfaceHeight - height)
        rect = Rect(x, y, x + width, y + height)
        moveDistance = if (initialDirectionRight) birdSpeed else -birdSpeed

        // Change SVG color based on birdType
        DrawableCompat.setTintList(svgBird, ColorStateList.valueOf(birdType.lightTheme.toInt()))
    }

    fun relocate(xDistance: Int) {

        // Move wall to a new x location
        val x = min(xDistance, surfaceWidth - rect.width())
        rect.offsetTo(x, rect.top)
    }

    fun move(dy: Int) {
        y += dy
        rect.offset(moveDistance * speedMod(birdType), dy)
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
        //if (!isVisible) paint.color = 0x0
        //canvas.drawRect(rect, paint)
        // Draw SVG drawable
        svgBird.bounds = rect
        if ( isVisible) {
            svgBird.draw(canvas)
        }
    }
}

