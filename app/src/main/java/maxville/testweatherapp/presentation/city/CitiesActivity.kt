package maxville.testweatherapp.presentation.city

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import maxville.testweatherapp.R
import maxville.testweatherapp.presentation.weather.WeatherActivity
import org.koin.androidx.scope.currentScope

class CitiesActivity : AppCompatActivity() {

    val viewModel: CitiesViewModel by currentScope.inject()
    lateinit var cityAdapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        cityAdapter = CityAdapter(this@CitiesActivity)
        cityAdapter.cityCallback = { city ->
            val intent = Intent(this, WeatherActivity::class.java)
            intent.putExtra("city", city)
            startActivity(intent)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@CitiesActivity)
            adapter = cityAdapter
            setHasFixedSize(true)
        }

        viewModel.cities.observe(this, Observer {
            cityAdapter.updateCities(it)
        })
        viewModel.getCities()

    }

}