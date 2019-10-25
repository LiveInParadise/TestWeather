package maxville.testweatherapp.data.common

import androidx.room.*
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    tableName = "cities",
    indices = arrayOf(Index(value = ["api_city_id"], unique = true))
)
data class City(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "api_city_id") val apiCityId: Long,
    @ColumnInfo(name = "fullName") val fullName: String
) : Serializable

@Entity(
    tableName = "weather",
    foreignKeys = [ForeignKey(
        entity = City::class,
        parentColumns = ["id"],
        childColumns = ["city_id_local"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class Weather(
    @PrimaryKey(autoGenerate = false) var cityId: Long,
    @ColumnInfo(name = "city_id_local") val cityIdLocal: Long,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "temp") val temp: Double,
    @ColumnInfo(name = "temp_min") var tempMin: Double,
    @ColumnInfo(name = "temp_max") var tempMax: Double,
    @ColumnInfo(name = "speed") var speed: Double,
    @ColumnInfo(name = "deg") var deg: Long
)

data class BaseWeatherResponse(
    @SerializedName("cod") var cod: String,
    @SerializedName("message") var message: Int,
    @SerializedName("cnt") var cnt: Int,
    @SerializedName("list") var weatherResponse: List<WeatherResponse>
) : Serializable

data class WeatherResponse(
    @SerializedName("dt_txt") var date: String,
    @SerializedName("main") var main: MainWeatherData,
    @SerializedName("wind") var windData: Wind
) : Serializable

data class MainWeatherData(
    @SerializedName("temp") var temp: Double,
    @SerializedName("temp_min") var tempMin: Double,
    @SerializedName("temp_max") var tempMax: Double
) : Serializable

data class Wind(
    @SerializedName("speed") var speed: Double,
    @SerializedName("deg") var deg: Long
) : Serializable