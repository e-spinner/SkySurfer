package com.theinnovationnation.skysurfer.game

import android.view.SurfaceHolder
import android.graphics.PointF

class SkyThread (private val surfaceHolder: SurfaceHolder) : Thread() {

    private var skyGame: SkyGame
    private var threadRunning = false
    private val velocity = PointF()

    init {
        threadRunning = true

        val canvas = surfaceHolder.lockCanvas()
        skyGame = SkyGame(canvas.width, canvas.height)
        surfaceHolder.unlockCanvasAndPost(canvas)
    }

    override fun run() {
        while (threadRunning) {
            val canvas = surfaceHolder.lockCanvas()
            canvas?.let {
                skyGame.update(velocity)
                skyGame.draw(canvas)
                surfaceHolder.unlockCanvasAndPost(it)
            }
        }
    }

    fun changeAcceleration(xForce: Float, yForce: Float) {
        velocity.x = xForce
        velocity.y = yForce
    }

    fun stopThread() {
        threadRunning = false
    }

    fun shake() {
        skyGame.newGame()
    }
}