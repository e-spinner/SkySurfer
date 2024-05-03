package com.theinnovationnation.skysurfer.game

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.TextView

class SkyView (context: Context, attrs: AttributeSet) :
    SurfaceView(context, attrs), SurfaceHolder.Callback {

    private var skyThread: SkyThread? = null
    private lateinit var skyGame: SkyGame
    private lateinit var surfaceHolder: SurfaceHolder

    init {
        holder.addCallback(this)
    }

    fun setSkyGame(skyGame: SkyGame) {
        println("SkyView setSkyGame, skyGame: $skyGame")
        this.skyGame = skyGame
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        this.surfaceHolder = holder
        skyThread = SkyThread(holder, skyGame)
        skyThread?.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        // Nothing to do
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        skyThread?.stopThread()
    }

    fun changeAcceleration(x: Float, y: Float) {
        skyThread?.changeAcceleration(x, y)
    }

    fun shake() {
        skyThread?.shake()
    }
}