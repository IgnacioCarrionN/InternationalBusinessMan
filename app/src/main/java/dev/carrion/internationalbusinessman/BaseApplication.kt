package dev.carrion.internationalbusinessman

import android.app.Application
import dev.carrion.internationalbusinessman.di.appModule
import org.koin.android.ext.android.startKoin
import org.koin.android.logger.AndroidLogger


class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule), logger = AndroidLogger())
    }
}