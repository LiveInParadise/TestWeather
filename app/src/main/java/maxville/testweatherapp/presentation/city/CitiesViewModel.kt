package maxville.testweatherapp.presentation.city

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import maxville.testweatherapp.data.common.BaseViewModel
import maxville.testweatherapp.data.common.City

class CitiesViewModel(val context: Context, val citiesRepository: CitiesRepository) :
    BaseViewModel() {

    var cities = MutableLiveData<List<City>>()

    fun getCities() {
        addDisposable(citiesRepository.getCities()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                cities.value = it
            }, {
                Log.e("error", it.message)
            })
        )
    }

}