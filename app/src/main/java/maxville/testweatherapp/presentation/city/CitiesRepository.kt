package maxville.testweatherapp.presentation.city

import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import maxville.testweatherapp.data.db.CityDao
import kotlin.random.Random

class CitiesRepository(val cityDatabase: CityDao) {

    fun getCities() = cityDatabase.getCities()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun changeCityId(apiCityId: Long): Maybe<Long> {
        return Maybe.fromCallable {
            val newId = Random.nextLong(40, 500)
            cityDatabase.updateCityId(newId, apiCityId)
            newId
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}