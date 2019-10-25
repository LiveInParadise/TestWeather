package maxville.testweatherapp.data.common

import androidx.core.util.Preconditions
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()
    val isProgressShown = SingleLiveEvent<Boolean>()

    override fun onCleared() {
        dispose()
        super.onCleared()
    }

    private fun dispose() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    protected fun addDisposable(disposable: Disposable) {
        Preconditions.checkNotNull(disposable)
        Preconditions.checkNotNull(disposables)
        disposables.add(disposable)
    }

    fun showLoading() {
        isProgressShown.value = true
    }

    fun hideLoading() {
        isProgressShown.value = false
    }

}