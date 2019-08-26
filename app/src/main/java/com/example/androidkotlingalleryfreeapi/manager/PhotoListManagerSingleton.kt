package com.example.androidkotlingalleryfreeapi.manager

import android.content.Context
import com.example.androidkotlingalleryfreeapi.application.Contextor
import com.example.androidkotlingalleryfreeapi.dao.PhotoItemCollectionDao

object PhotoListManagerSingleton {

    private var instance : PhotoListManagerSingleton? = null

    fun getInstance() : PhotoListManagerSingleton {
        if (instance == null) instance = PhotoListManagerSingleton
        return instance as PhotoListManagerSingleton
    }

    private var mContext = Contextor.getInstance().getContext()

    var dao: PhotoItemCollectionDao? = null

}