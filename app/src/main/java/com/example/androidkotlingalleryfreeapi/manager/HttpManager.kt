package com.example.androidkotlingalleryfreeapi.manager

import android.content.Context
import com.example.androidkotlingalleryfreeapi.R
import com.example.androidkotlingalleryfreeapi.application.Contextor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HttpManager {

    private var instance: HttpManager? = null

    fun getInstance(): HttpManager {
        if (instance == null) instance = HttpManager
        return instance as HttpManager
    }

    private var mContext = Contextor.getInstance().getContext()
    private var service: ApiService? = null

    init {

        val gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(mContext?.getString(R.string.api_base_url))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        service = retrofit.create(ApiService::class.java)
    }

    fun getService(): ApiService? {
        return service
    }
}