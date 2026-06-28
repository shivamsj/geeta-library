package com.example.librarymanager

import android.app.Application
import com.example.librarymanager.data.network.ApiClient

class GeetaLibraryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ApiClient.initialize(this)
    }
}
