package com.example.androidkotlingalleryfreeapi.ui.main

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.BaseAdapter
import android.widget.ProgressBar
import com.example.androidkotlingalleryfreeapi.R
import com.example.androidkotlingalleryfreeapi.dao.PhotoItemCollectionDao
import com.example.androidkotlingalleryfreeapi.dao.PhotoItemDao
import com.example.androidkotlingalleryfreeapi.manager.PhotoListManager
import com.example.androidkotlingalleryfreeapi.view.PhotoListItem

class PhotoListAdapter(lastPositionInt: MutableInteger) : BaseAdapter() {

    var dao: PhotoItemCollectionDao? = null
    var lastPositionInt: MutableInteger = lastPositionInt

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        if (position == count - 1) {
            val item: ProgressBar

            item = if (convertView != null) convertView as ProgressBar
            else ProgressBar(parent?.context)
            return item
        }

        var item: PhotoListItem

        item = if (convertView != null) convertView as PhotoListItem
        else PhotoListItem(parent!!.context)

        val dao: PhotoItemDao = getItem(position) as PhotoItemDao

        item.setNameText(dao.caption)
        item.setDescriptionText(dao.username)
        item.setImageURL(dao.imageUrl)

        val anim: Animation =
            AnimationUtils.loadAnimation(parent?.context, R.anim.anim_up_from_buttom)

        if (position > lastPositionInt.value) {
            item.startAnimation(anim)
            lastPositionInt.value = position
        }

        return item

    }


    override fun getItem(position: Int): Any? {
        return dao?.data?.get(position)
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getViewTypeCount(): Int {
        return 2
    }

    override fun getItemViewType(position: Int): Int {
        if (position == count - 1) return 1
        else return 0
    }

    override fun getCount(): Int {
        if (dao == null) return 1
        if (dao?.data == null) return 1
        return dao?.data?.size!! + 1
    }

    fun increaseLastPosition(amount: Int) {
        lastPositionInt.value = lastPositionInt.value + amount
    }
}