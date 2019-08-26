package com.example.androidkotlingalleryfreeapi.dao

import com.google.gson.annotations.SerializedName
import java.util.*

class PhotoItemDao {

    @SerializedName("id")
    var id: Int = 0

    @SerializedName("link")
    var link: String = ""

    @SerializedName("image_url")
    var imageUrl: String = ""

    @SerializedName("caption")
    var caption: String = ""

    @SerializedName("user_id")
    var userId: String = ""

    @SerializedName("username")
    var username: String = ""

    @SerializedName("profile_picture")
    var profilePicture: String = ""

    @SerializedName("tags")
    var tags: List<String>? = null

    @SerializedName("created_time")
    var createdTime: Date? = null

    @SerializedName("camera")
    var camera: String = ""

    @SerializedName("lens")
    var lens: String = ""

    @SerializedName("focal_length")
    var focalLength: String = ""

    @SerializedName("iso")
    var iso: String = ""

    @SerializedName("shutter_speed")
    var shutterSpeed: String = ""

    @SerializedName("aperture")
    var aperture: String = ""

}