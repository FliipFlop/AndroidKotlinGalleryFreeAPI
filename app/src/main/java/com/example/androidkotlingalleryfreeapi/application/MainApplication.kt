package com.example.androidkotlingalleryfreeapi.application

import android.app.Application

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initContextor()
    }

    private fun initContextor() {
        Contextor.getInstance().init(applicationContext)
    }

}