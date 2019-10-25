package maxville.testweatherapp.presentation.weather

import android.content.Context
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import maxville.testweatherapp.data.common.BaseWeatherResponse
import maxville.testweatherapp.data.common.Utils
import maxville.testweatherapp.data.common.Weather
import maxville.testweatherapp.data.db.WeatherDao
import maxville.testweatherapp.data.network.ApiService

class WeatherRepository(
    val context: Context,
    val apiService: ApiService,
    val weatherDatabase: WeatherDao
) {

    fun getWeather(cityId: Long, cityIdLocal: Long, forceNetwork: Boolean): Flowable<Weather> {
        return if (Utils.isNetworkAvailable(context) && forceNetwork)
            getWeatherForce(cityId, cityIdLocal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        else
            weatherDatabase.getWeather(cityId)
                .switchIfEmpty(getWeatherForce(cityId, cityIdLocal))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getWeatherForce(cityId: Long, cityIdLocal: Long): Flowable<Weather> {
        return apiService.getWeather(cityId = cityId, appId = "61c16f164ad2bf08cd0eb25a109cc96b", units = "metric")
            .map {
                convertWeather(cityId, cityIdLocal, it).apply {
                    weatherDatabase.insert(this)
                }
            }.toFlowable()
    }

    private fun convertWeather(cityId: Long, cityIdLocal: Long, baseWeatherResponse: BaseWeatherResponse): Weather {
        return Weather(
            cityId = cityId,
            cityIdLocal = cityIdLocal,
            date = baseWeatherResponse.weatherResponse[0].date,
            temp = baseWeatherResponse.weatherResponse[0].main.temp,
            tempMin = baseWeatherResponse.weatherResponse[0].main.tempMin,
            tempMax = baseWeatherResponse.weatherResponse[0].main.tempMax,
            speed = baseWeatherResponse.weatherResponse[0].windData.speed,
            deg = baseWeatherResponse.weatherResponse[0].windData.deg
        )
    }
}