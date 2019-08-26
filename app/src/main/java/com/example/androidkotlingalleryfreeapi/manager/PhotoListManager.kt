package com.example.androidkotlingalleryfreeapi.manager

import android.os.Bundle
import com.example.androidkotlingalleryfreeapi.application.Contextor
import com.example.androidkotlingalleryfreeapi.dao.PhotoItemCollectionDao
import com.example.androidkotlingalleryfreeapi.dao.PhotoItemDao
import kotlin.math.max
import kotlin.math.min

class PhotoListManager {

    private var mContext = Contextor.getInstance().getContext()

    var dao: PhotoItemCollectionDao? = null

    fun insertDaoAtTopPosition(newDao: PhotoItemCollectionDao) {
        if (dao == null) dao = PhotoItemCollectionDao()
        if (dao?.data == null) dao?.data = ArrayList<PhotoItemDao>()

        dao?.data?.addAll(0, newDao.data!!)
    }

    fun insertDaoAtBottomPosition(newDao: PhotoItemCollectionDao) {
        if (dao == null) dao = PhotoItemCollectionDao()
        if (dao?.data == null) dao?.data = ArrayList<PhotoItemDao>()

        dao?.data?.addAll(newDao.data!!.size, newDao.data!!)
    }

    fun getMaximumID(): Int? {
        if (dao == null) return 0
        if (dao!!.data == null) return 0
        if (dao!!.data!!.isEmpty()) return 0

        var maxId: Int? = dao!!.data?.get(0)?.id

        for (i in 1 until dao!!.data!!.size) {
            maxId = max(maxId!!, dao!!.data!!.get(i).id)
        }
        return maxId
    }

    fun getMinimumID(): Int? {
        if (dao == null) return 0
        if (dao!!.data == null) return 0
        if (dao!!.data!!.isEmpty()) return 0

        var minId: Int? = dao!!.data?.get(0)?.id

        for (i in 1 until dao!!.data!!.size) {
            minId = min(minId!!, dao!!.data!!.get(i).id)
        }
        return minId
    }

    fun getCount(): Int {
        if (dao == null) return 0
        if (dao!!.data == null) return 0

        return dao!!.data!!.size
    }

    fun onSaveInstanceState(): Bundle? {
        var bundle: Bundle = Bundle()
        bundle.putParcelable("dao", dao)
        return bundle
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            dao = savedInstanceState.getParcelable("dao")
        }
    }

}