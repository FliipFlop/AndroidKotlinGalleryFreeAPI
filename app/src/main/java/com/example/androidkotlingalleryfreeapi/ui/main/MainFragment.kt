package com.example.androidkotlingalleryfreeapi.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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

    private var photoListAdapter: PhotoListAdapter? = null

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Do anythings
        initListView()
        initApi()
    }


    private fun initListView() {
        photoListAdapter = PhotoListAdapter()
        lvPhotoItemList.adapter = photoListAdapter
    }

    private fun initApi() {
        val applicationContext: Context? = Contextor.getInstance().getContext()

        var call = HttpManager.getInstance().getService()?.loadPhotoList()
        call?.enqueue(object : Callback<PhotoItemCollectionDao> {
            override fun onFailure(call: Call<PhotoItemCollectionDao>, t: Throwable) {
                Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<PhotoItemCollectionDao>,
                response: Response<PhotoItemCollectionDao>
            ) {
                if (response.isSuccessful) {
                    var dao: PhotoItemCollectionDao? = response.body()
                    PhotoListManager.getInstance().dao = dao
                    photoListAdapter?.notifyDataSetChanged()
                    Toast.makeText(applicationContext, dao?.data?.size.toString() , Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, response.errorBody()?.string(), Toast.LENGTH_LONG)
                        .show()
                }
            }

        })
    }


}