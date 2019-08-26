package com.example.androidkotlingalleryfreeapi.ui.main

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.BaseAdapter
import com.example.androidkotlingalleryfreeapi.R
import com.example.androidkotlingalleryfreeapi.dao.PhotoItemCollectionDao
import com.example.androidkotlingalleryfreeapi.dao.PhotoItemDao
import com.example.androidkotlingalleryfreeapi.manager.PhotoListManager
import com.example.androidkotlingalleryfreeapi.view.PhotoListItem

class PhotoListAdapter : BaseAdapter() {

    var dao: PhotoItemCollectionDao? = null
    var lastPosition = -1

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var item: PhotoListItem

        item = if (convertView != null) convertView as PhotoListItem
        else PhotoListItem(parent!!.context)

        val dao: PhotoItemDao = getItem(position) as PhotoItemDao

        item.setNameText(dao.caption)
        item.setDescriptionText(dao.username)
        item.setImageURL(dao.imageUrl)

        val anim: Animation =
            AnimationUtils.loadAnimation(parent?.context, R.anim.anim_up_from_buttom)

        if (position > lastPosition) {
            item.startAnimation(anim)
            lastPosition = position
        }

        return item

    }


    override fun getItem(position: Int): Any? {
        return dao?.data?.get(position)
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        if (dao == null) return 0
        if (dao?.data == null) return 0
        return dao?.data?.size!!
    }

    fun increaseLastPosition(amount : Int) {
        lastPosition += amount
    }
}