package com.example.androidkotlingalleryfreeapi.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.androidkotlingalleryfreeapi.R
import com.example.androidkotlingalleryfreeapi.application.Contextor
import com.example.androidkotlingalleryfreeapi.dao.PhotoItemCollectionDao
import com.example.androidkotlingalleryfreeapi.manager.HttpManager
import com.example.androidkotlingalleryfreeapi.manager.PhotoListManager
import kotlinx.android.synthetic.main.fragment_template.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment() {

    private lateinit var photoListAdapter: PhotoListAdapter
    private lateinit var photoListManager: PhotoListManager
    val applicationContext: Context? = Contextor.getInstance().getContext()

    companion object {
        fun newInstance(): MainFragment {
            val fragment = MainFragment()
            val args = Bundle()
            // bundle.put...
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        // init argument from newInstance to someVar
        // someVar = arguments.get...

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_template, container, false)
    }

    override fun onViewCreated(rootView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(rootView, savedInstanceState)
        // Do anythings
        initPhotoListManager()
        initListView()
        initSwipeRefresh()
        initApi()
    }

    private fun initPhotoListManager() {
        photoListManager = PhotoListManager()
    }

    private fun initListView() {
        photoListAdapter = PhotoListAdapter()

        lvPhotoItemList.adapter = photoListAdapter
        lvPhotoItemList.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScroll(
                view: AbsListView?,
                firstVisibleTeam: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
                swSwipeRefresh.isEnabled = firstVisibleTeam == 0
            }

            override fun onScrollStateChanged(p0: AbsListView?, p1: Int) {

            }

        })
    }


    private fun initSwipeRefresh() {
        swSwipeRefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                refreshData()
            }
        })
    }

    private fun initApi() {
        refreshData()
    }

    private fun refreshData() {
        if (photoListManager.getCount() == 0) {
            reloadData()
        } else {
            reloadDataNewer()
        }
    }

    inner class PhotoListLoadCallback(
        var mode: Int
    ) :
        Callback<PhotoItemCollectionDao> {

        var MODE_RELOAD: Int = 1
        var MODE_RELOAD_NEWER: Int = 2

        override fun onFailure(call: Call<PhotoItemCollectionDao>, t: Throwable) {
            swSwipeRefresh.isRefreshing = false
            Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_LONG).show()
        }

        override fun onResponse(
            call: Call<PhotoItemCollectionDao>, response: Response<PhotoItemCollectionDao>
        ) {
            swSwipeRefresh.isRefreshing = false
            val toastText: String
            if (response.isSuccessful) {

                var dao: PhotoItemCollectionDao? = response.body()

                var firstVisiblePosition: Int = 0
                var c: View
                var top: Int = 0

                if (photoListManager.getCount() > 0) {
                    firstVisiblePosition = lvPhotoItemList.firstVisiblePosition
                    c = lvPhotoItemList.getChildAt(0)
                    top = if (c == null) 0
                    else c.top
                }


                if (mode == MODE_RELOAD_NEWER) {
                    photoListManager.insertDaoAtTopPosition(dao!!)
                    toastText = "RELOAD NEWER SUCCESS"
                } else {
                    photoListManager.dao = dao
                    toastText = "LOAD SUCCESS"
                }

                photoListAdapter?.dao = photoListManager.dao
                photoListAdapter?.notifyDataSetChanged()

                if (mode == MODE_RELOAD_NEWER) {
                    if (photoListManager.getCount() > 0) {
                        var insertDaoSize: Int
                        insertDaoSize =
                            if (dao?.data != null && dao?.data!!.size != null) dao.data!!.size
                            else 0

                        photoListAdapter.increaseLastPosition(insertDaoSize)
                        lvPhotoItemList.setSelectionFromTop(firstVisiblePosition + insertDaoSize, top)
                    }
                } else {

                }

                Toast.makeText(applicationContext, toastText, Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(
                    applicationContext,
                    response.errorBody()?.string(),
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
    }

    private fun reloadDataNewer() {
        var maxId: Int = photoListManager.getMaximumID()!!

        var call: Call<PhotoItemCollectionDao>? =
            HttpManager.getInstance().getService()?.loadPhotoListAfterID(maxId)

        call?.enqueue(
            PhotoListLoadCallback(2)
        )
    }

    private fun reloadData() {

        var call = HttpManager.getInstance().getService()?.loadPhotoList()
        call?.enqueue(PhotoListLoadCallback(1))
    }


}