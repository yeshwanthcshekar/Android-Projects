package com.example.amphibians

import android.app.Application
import com.example.amphibians.repository.AppContainer
import com.example.amphibians.repository.DefaultAppContainer

class AmphibianApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}