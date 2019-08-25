package com.example.androidkotlingalleryfreeapi.template

object SingletonTemplate {

    private var instance : SingletonTemplate? = null

    fun getInstance() : SingletonTemplate {
        if (instance == null) instance = SingletonTemplate
        return instance as SingletonTemplate
    }

}