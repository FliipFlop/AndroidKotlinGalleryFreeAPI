package com.example.androidkotlingalleryfreeapi.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.view.WindowManager
import com.example.androidkotlingalleryfreeapi.application.Contextor

@SuppressLint("StaticFieldLeak")
object ScreenUtils {

    private var instance : ScreenUtils? = null

    fun getInstance() : ScreenUtils {
        if (instance == null) instance = ScreenUtils
        return instance as ScreenUtils
    }

    private var mContext = Contextor.getInstance().getContext()

    fun getScreenWidth() : Int {
        var wm : WindowManager = mContext?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        var display = wm.defaultDisplay
        var size = Point()

        display.getSize(size)

        return size.x
    }

    fun getScreenHeight() : Int {
        var wm : WindowManager = mContext?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        var display = wm.defaultDisplay
        var size = Point()

        display.getSize(size)

        return size.y
    }

}