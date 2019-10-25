package maxville.testweatherapp.data.network

import io.reactivex.Single
import maxville.testweatherapp.data.common.BaseWeatherResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    /**
     * Погода по ID города
     */
    @POST("forecast")
    fun getWeather(
        @Query("id") cityId: Long,
        @Query("APPID") appId: String,
        @Query("units") units: String
    ): Single<BaseWeatherResponse>

}