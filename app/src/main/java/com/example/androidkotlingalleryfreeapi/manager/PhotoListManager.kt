package com.example.androidkotlingalleryfreeapi.manager

import android.content.Context
import com.example.androidkotlingalleryfreeapi.application.Contextor
import com.example.androidkotlingalleryfreeapi.dao.PhotoItemCollectionDao

object PhotoListManager {

    private var instance : PhotoListManager? = null

    fun getInstance() : PhotoListManager {
        if (instance == null) instance = PhotoListManager
        return instance as PhotoListManager
    }

    private var mContext = Contextor.getInstance().getContext()

    var dao: PhotoItemCollectionDao? = null

}