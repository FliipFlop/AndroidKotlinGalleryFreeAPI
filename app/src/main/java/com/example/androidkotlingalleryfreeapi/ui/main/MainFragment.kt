package com.example.androidkotlingalleryfreeapi.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.androidkotlingalleryfreeapi.R
import com.example.androidkotlingalleryfreeapi.application.Contextor
import com.example.androidkotlingalleryfreeapi.dao.PhotoItemCollectionDao
import com.example.androidkotlingalleryfreeapi.manager.HttpManager
import com.example.androidkotlingalleryfreeapi.manager.PhotoListManager
import kotlinx.android.synthetic.main.fragment_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment() {

    private lateinit var photoListAdapter: PhotoListAdapter
    private lateinit var photoListManager: PhotoListManager
    val applicationContext: Context? = Contextor.getInstance().getContext()

    private lateinit var lastPositionInt: MutableInteger

    var isLoadingMore: Boolean = false

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
        initPhotoListManager()
        initLastPosition()

        if (savedInstanceState != null) {
            // Restore Instance state
            onRestoreInstanceState(savedInstanceState)
        }

        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Save Instance state
        outState.putBundle("photoListManager", photoListManager.onSaveInstanceState())
        outState.putBundle("lastPositionInt", lastPositionInt.onSaveInstanceState())
    }

    private fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // Restore Instance state
        photoListManager.onRestoreInstanceState(savedInstanceState.getBundle("photoListManager"))
        lastPositionInt.onRestoreInstanceState(savedInstanceState.getBundle("lastPositionInt"))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(rootView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(rootView, savedInstanceState)
        // Do anythings

        initNewPhotosButton()
        initListView()
        initSwipeRefresh()
        initApi(savedInstanceState)
    }

    private fun initPhotoListManager() {
        photoListManager = PhotoListManager()
    }


    private fun initLastPosition() {
        lastPositionInt = MutableInteger(-1)
    }

    private fun initNewPhotosButton() {
        btnNewPhotos.setOnClickListener {
            lvPhotoItemList.smoothScrollToPosition(0)
            hideButtonNewPhotos()
        }
    }

    private fun initListView() {
        photoListAdapter = PhotoListAdapter(lastPositionInt)
        photoListAdapter.dao = photoListManager.dao

        lvPhotoItemList.adapter = photoListAdapter

        lvPhotoItemList.setOnScrollListener(listViewScrollListener)
    }


    private fun initSwipeRefresh() {
        swSwipeRefresh.setOnRefreshListener {
            refreshData()
        }
    }

    private fun initApi(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            refreshData()
        }
    }

    private fun refreshData() {
        if (photoListManager.getCount() == 0) {
            reloadData()
        } else {
            reloadDataNewer()
        }
    }

    private fun reloadData() {

        var call = HttpManager.getInstance().getService()?.loadPhotoList()
        call?.enqueue(PhotoListLoadCallback(1))
    }

    private fun reloadDataNewer() {
        var maxId: Int = photoListManager.getMaximumID()!!

        var call: Call<PhotoItemCollectionDao>? =
            HttpManager.getInstance().getService()?.loadPhotoListAfterID(maxId)

        call?.enqueue(
            PhotoListLoadCallback(2)
        )
    }

    private fun loadMoreData() {
        if (isLoadingMore) return
        isLoadingMore = true

        var minId: Int = photoListManager.getMinimumID()!!

        var call: Call<PhotoItemCollectionDao>? =
            HttpManager.getInstance().getService()?.loadPhotoListBeforeID(minId)

        call?.enqueue(
            PhotoListLoadCallback(3)
        )
    }

    private fun showButtonNewPhotos() {
        btnNewPhotos.visibility = View.VISIBLE

        val anim = AnimationUtils.loadAnimation(
            Contextor.getInstance().getContext(),
            R.anim.anim_zoom_fade_in
        )
        btnNewPhotos.startAnimation(anim)
    }

    private fun hideButtonNewPhotos() {
        btnNewPhotos.visibility = View.GONE

        val anim = AnimationUtils.loadAnimation(
            Contextor.getInstance().getContext(),
            R.anim.anim_zoom_fade_out
        )
        btnNewPhotos.startAnimation(anim)
    }

    private val listViewScrollListener = object : AbsListView.OnScrollListener {
        override fun onScroll(
            view: AbsListView?,
            firstVisibleTeam: Int,
            visibleItemCount: Int,
            totalItemCount: Int
        ) {
            swSwipeRefresh.isEnabled = firstVisibleTeam == 0

            if (firstVisibleTeam + visibleItemCount >= totalItemCount) {
                if (photoListManager.getCount() > 0) {
                    // Load more photos
                    loadMoreData()
                }
            }
        }

        override fun onScrollStateChanged(p0: AbsListView?, p1: Int) {

        }

    }

    fun toastMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    inner class PhotoListLoadCallback(
        var mode: Int
    ) :
        Callback<PhotoItemCollectionDao> {

        var MODE_RELOAD: Int = 1
        var MODE_RELOAD_NEWER: Int = 2
        var MODE_LOAD_MORE: Int = 3

        override fun onFailure(call: Call<PhotoItemCollectionDao>, t: Throwable) {
            if (mode == MODE_LOAD_MORE) isLoadingMore = false

            swSwipeRefresh.isRefreshing = false
            toastMessage(t.toString())
        }

        override fun onResponse(
            call: Call<PhotoItemCollectionDao>, response: Response<PhotoItemCollectionDao>
        ) {
            swSwipeRefresh.isRefreshing = false
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
                    toastMessage("RELOAD NEWER SUCCESS")
                } else if (mode == MODE_LOAD_MORE) {
                    photoListManager.insertDaoAtBottomPosition(dao!!)
                    isLoadingMore = false
                    toastMessage("LODE MORE SUCCESS")
                } else {
                    photoListManager.dao = dao
                    toastMessage("LOAD SUCCESS")
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
                        lvPhotoItemList.setSelectionFromTop(
                            firstVisiblePosition + insertDaoSize,
                            top
                        )

                        if (insertDaoSize > 0) showButtonNewPhotos()
                    }
                } else {

                }

            } else {
                if (mode == MODE_LOAD_MORE) isLoadingMore = false

                toastMessage(response.errorBody()?.string().toString())
            }
        }
    }

}