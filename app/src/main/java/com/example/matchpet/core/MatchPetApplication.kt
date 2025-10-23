package com.example.matchpet.core

import android.app.Application
import com.example.matchpet.core.di.AppContainer

class MatchPetApplication : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(applicationContext)
    }
}
