package com.example.androidkotlingalleryfreeapi.dao

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class PhotoItemCollectionDao() : Parcelable {

    @SerializedName("success")
    var success : Boolean = false

    @SerializedName("data")
    lateinit var data : ArrayList<PhotoItemDao>

    constructor(parcel: Parcel) : this() {
        success = parcel.readByte() != 0.toByte()
        data = parcel.createTypedArrayList(PhotoItemDao.CREATOR)!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (success) 1 else 0)
        parcel.writeTypedList(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PhotoItemCollectionDao> {
        override fun createFromParcel(parcel: Parcel): PhotoItemCollectionDao {
            return PhotoItemCollectionDao(parcel)
        }

        override fun newArray(size: Int): Array<PhotoItemCollectionDao?> {
            return arrayOfNulls(size)
        }
    }
}