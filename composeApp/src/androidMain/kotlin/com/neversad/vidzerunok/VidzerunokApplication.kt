package com.neversad.vidzerunok

import android.app.Application
import com.neversad.vidzerunok.di.initKoin
import org.koin.android.ext.koin.androidContext

class VidzerunokApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@VidzerunokApplication)
        }
    }
}