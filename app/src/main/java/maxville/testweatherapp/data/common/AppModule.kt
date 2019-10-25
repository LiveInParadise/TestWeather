package maxville.testweatherapp.data.common

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import maxville.testweatherapp.data.db.AppDatabase
import maxville.testweatherapp.presentation.city.CitiesActivity
import maxville.testweatherapp.presentation.city.CitiesRepository
import maxville.testweatherapp.presentation.city.CitiesViewModel
import maxville.testweatherapp.presentation.weather.WeatherActivity
import maxville.testweatherapp.presentation.weather.WeatherRepository
import maxville.testweatherapp.presentation.weather.WeatherViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.concurrent.Executors

private var INSTANCE: AppDatabase? = null

val appModule = module {
    single { getDatabaseInstance(get()) }
    single { get<AppDatabase>().cityDao() }
    single { get<AppDatabase>().weatherDao() }
    factory { CitiesRepository(get()) }
    factory { WeatherRepository(get(), get(), get()) }
}

val cityModule = module {
    scope(named<CitiesActivity>()) {
        scoped { CitiesViewModel(get(), get()) }
    }
}

val weatherModule = module {
    scope(named<WeatherActivity>()) {
        scoped { WeatherViewModel(get(), get(), get()) }
    }
}

fun getDatabaseInstance(context: Context): AppDatabase =
    INSTANCE ?: synchronized(context) {
        INSTANCE ?: prepareDatabase(context).also { INSTANCE = it }
    }

fun prepareDatabase(context: Context): AppDatabase {
    INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "weather-database")
        .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Executors.newSingleThreadExecutor().execute {
                    INSTANCE?.cityDao()?.insert(PREPOPULATE_DATA)
                }
            }
        })
        .build()
    return INSTANCE as AppDatabase
}

val PREPOPULATE_DATA =
    listOf(
        City(apiCityId = 625144L, fullName = "Minsk"),
        City(apiCityId = 511196L, fullName = "Perm")
    )