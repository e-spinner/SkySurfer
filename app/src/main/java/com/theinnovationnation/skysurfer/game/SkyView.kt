package com.theinnovationnation.skysurfer.game

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

class SkyView (context: Context, attrs: AttributeSet) :
    SurfaceView(context, attrs), SurfaceHolder.Callback {

    private var skyThread: SkyThread? = null

    init {
        holder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        skyThread = SkyThread(holder)
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