package com.example.androidkotlingalleryfreeapi.manager

import com.example.androidkotlingalleryfreeapi.dao.PhotoItemCollectionDao
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("list")
    fun loadPhotoList(): Call<PhotoItemCollectionDao>

    @GET("list/after/{id}")
    fun loadPhotoListAfterID(@Path("id") id: Int): Call<PhotoItemCollectionDao>

    @GET("list/before/{id}")
    fun loadPhotoListBeforeID(@Path("id") id: Int): Call<PhotoItemCollectionDao>

}