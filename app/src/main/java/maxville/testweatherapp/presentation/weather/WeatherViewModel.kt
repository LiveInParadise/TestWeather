package maxville.testweatherapp.presentation.weather

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import maxville.testweatherapp.data.common.BaseViewModel
import maxville.testweatherapp.data.common.City
import maxville.testweatherapp.data.common.Weather
import maxville.testweatherapp.presentation.city.CitiesRepository

class WeatherViewModel(
    val context: Context,
    val weatherRepository: WeatherRepository,
    val citiesRepository: CitiesRepository
) : BaseViewModel() {

    var weather = MutableLiveData<Weather>()

    fun getWeather(cityId: Long, cityIdLocal: Long, forceNetwork: Boolean) {
        addDisposable(
            weatherRepository.getWeather(cityId, cityIdLocal, forceNetwork)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showLoading() }
                .subscribe({
                    hideLoading()
                    weather.value = it
                }, {
                    hideLoading()
                    Log.e("error", it.message)
                })
        )
    }

    fun changeCityId(city: City) {
        addDisposable(
            citiesRepository.changeCityId(city.apiCityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ }, {
                    Log.e("error", it.message)
                })
        )
    }
}