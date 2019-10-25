package maxville.testweatherapp

import android.app.Application
import maxville.testweatherapp.data.common.appModule
import maxville.testweatherapp.data.common.cityModule
import maxville.testweatherapp.data.common.networkModule
import maxville.testweatherapp.data.common.weatherModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(listOf(appModule, networkModule, cityModule, weatherModule))
        }
    }
}