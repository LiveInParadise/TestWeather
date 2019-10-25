package maxville.testweatherapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import maxville.testweatherapp.data.common.Weather

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weather: Weather): Long

    @Query("SELECT * FROM weather WHERE cityId = :cityId")
    fun getWeather(cityId: Long): Flowable<Weather>

}