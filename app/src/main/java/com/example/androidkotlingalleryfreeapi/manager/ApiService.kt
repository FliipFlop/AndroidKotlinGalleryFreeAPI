package com.example.androidkotlingalleryfreeapi.manager

import com.example.androidkotlingalleryfreeapi.dao.PhotoItemCollectionDao
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("list")
    fun loadPhotoList() : Call<PhotoItemCollectionDao>

}