package com.example.androidkotlingalleryfreeapi.ui.main

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.androidkotlingalleryfreeapi.dao.PhotoItemDao
import com.example.androidkotlingalleryfreeapi.manager.PhotoListManager
import com.example.androidkotlingalleryfreeapi.view.PhotoListItem

class PhotoListAdapter : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var item: PhotoListItem

        item = if (convertView != null) convertView as PhotoListItem
        else PhotoListItem(parent!!.context)

        val dao: PhotoItemDao = getItem(position) as PhotoItemDao

        item.setNameText(dao.caption)
        item.setDescriptionText(dao.username)
        item.setImageURL(dao.imageUrl)

        return item

    }


    override fun getItem(position: Int): Any? {
        return PhotoListManager.getInstance().dao?.data?.get(position)
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        if (PhotoListManager.getInstance().dao == null) return 0
        if (PhotoListManager.getInstance().dao?.data == null) return 0
        return PhotoListManager.getInstance().dao?.data?.size!!
    }
}