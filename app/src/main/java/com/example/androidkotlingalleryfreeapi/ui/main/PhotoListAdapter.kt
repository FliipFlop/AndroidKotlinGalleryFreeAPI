package com.example.androidkotlingalleryfreeapi.ui.main

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.androidkotlingalleryfreeapi.view.PhotoListItem

class PhotoListAdapter : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var item: PhotoListItem

        if (convertView != null) item = convertView as PhotoListItem
        else item = PhotoListItem(parent!!.context)

        return item

    }


    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return 50
    }
}