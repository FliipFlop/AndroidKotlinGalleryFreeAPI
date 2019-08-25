package com.example.androidkotlingalleryfreeapi.ui.main

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.androidkotlingalleryfreeapi.R

class PhotoListItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    // var item in view group

    init {
        initInflater()
        initInstance()
    }

    private fun initInflater() {
        inflate(context, R.layout.item_photo, this)
    }

    private fun initInstance() {
        // init var to view

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = width * 2 / 3
        var newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
            height
            , MeasureSpec.EXACTLY
        )

        // size of child in view
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)

        // size of self --> all of view
        setMeasuredDimension(width , height)
    }
}