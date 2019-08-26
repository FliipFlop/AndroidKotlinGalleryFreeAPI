package com.example.androidkotlingalleryfreeapi.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.androidkotlingalleryfreeapi.R
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoListItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        initInflater()
        initInstance()
    }

    private fun initInflater() {
        inflate(context, R.layout.item_photo, this)
    }

    private fun initInstance() {
        // init view item

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

    fun setNameText(text : String) {
        tvName.text = text
    }

    fun setDescriptionText(text : String) {
        tvDescription.text = text
    }

    fun setImageURL(url : String) {
        Glide.with(context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(ivPhotoItem)
    }
}