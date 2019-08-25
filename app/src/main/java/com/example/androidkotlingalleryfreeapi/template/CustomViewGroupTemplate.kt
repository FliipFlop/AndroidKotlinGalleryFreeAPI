package com.example.androidkotlingalleryfreeapi.template

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.androidkotlingalleryfreeapi.R

class CustomViewGroupTemplate @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    // var item in view group

    init {
        initInflater()
        initInstance()
    }

    private fun initInflater() {
        inflate(context , R.layout.fragment_template , this)
    }

    private fun initInstance() {
        // init var to view

    }

}