package maxville.testweatherapp.data.common

import android.content.Context
import android.net.ConnectivityManager

class Utils {

    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo?.isConnected ?: false
        }
    }
}