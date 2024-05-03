package com.theinnovationnation.skysurfer.game

import android.view.SurfaceHolder
import android.graphics.PointF


class SkyThread (private val surfaceHolder: SurfaceHolder, private var skyGame: SkyGame) : Thread() {

    private var threadRunning = false
    private val velocity = PointF()

    init {
        threadRunning = true
        println("skythread init")

        val canvas = surfaceHolder.lockCanvas()

        println("skythread: canvas: ${canvas.height}, ${canvas.width}")
        skyGame.setCanvasSize(canvas)
        skyGame.initialize()
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