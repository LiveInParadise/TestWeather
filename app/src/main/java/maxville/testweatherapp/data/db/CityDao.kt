package maxville.testweatherapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import maxville.testweatherapp.data.common.City

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cityList: List<City>)

    @Query("SELECT * FROM cities")
    fun getCities(): Flowable<List<City>>

    @Query("UPDATE cities SET id= :newCityId WHERE api_city_id = :apiCityId")
    fun updateCityId(newCityId: Long, apiCityId: Long)

}