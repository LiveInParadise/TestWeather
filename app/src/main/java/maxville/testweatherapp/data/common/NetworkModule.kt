package maxville.testweatherapp.data.common

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import maxville.testweatherapp.data.network.ApiService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val BASE_URL = "http://api.openweathermap.org/data/2.5/"

val networkModule = module {
    single { provideRetrofit() }
    single { provideApiService(get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}

fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)