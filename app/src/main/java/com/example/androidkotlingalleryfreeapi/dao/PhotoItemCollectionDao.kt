package com.example.androidkotlingalleryfreeapi.dao

import com.google.gson.annotations.SerializedName

class PhotoItemCollectionDao {

    @SerializedName("success")
    var success : Boolean = false

    @SerializedName("data")
    var data : List<PhotoItemDao>? = null
}