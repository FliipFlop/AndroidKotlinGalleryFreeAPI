package com.example.androidkotlingalleryfreeapi.ui.main

import android.os.Bundle

class MutableInteger(var value: Int) {
    fun onSaveInstanceState(): Bundle? {
        var bundle: Bundle = Bundle()
        bundle.putInt("value", value)
        return bundle
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            value = savedInstanceState.getInt("value")
        }
    }
}