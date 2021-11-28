package com.johnmarsel.testtask

import android.app.Application
import com.johnmarsel.testtask.api.ItunesApi

class TestTask : Application() {
    override fun onCreate() {
        super.onCreate()
        ItunesApi.create()
        ItunesRepository.initialize(this)
    }
}