package com.example.androidkotlingalleryfreeapi.template

import android.content.Context
import com.example.androidkotlingalleryfreeapi.application.Contextor

object SingletonTemplate {

    private var instance : SingletonTemplate? = null

    fun getInstance() : SingletonTemplate {
        if (instance == null) instance = SingletonTemplate
        return instance as SingletonTemplate
    }

    private var mContext = Contextor.getInstance().getContext()

}