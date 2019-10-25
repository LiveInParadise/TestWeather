package maxville.testweatherapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import maxville.testweatherapp.data.common.City
import maxville.testweatherapp.data.common.Weather

@Database(entities = [City::class, Weather::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
    abstract fun weatherDao(): WeatherDao

}