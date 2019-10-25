package maxville.testweatherapp.presentation.weather

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.content_weather.*
import maxville.testweatherapp.R
import maxville.testweatherapp.data.common.City
import org.koin.androidx.scope.currentScope

class WeatherActivity : AppCompatActivity() {

    val viewModel: WeatherViewModel by currentScope.inject()
    var mProgressDialog: MaterialDialog? = null
    var city: City? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        city = intent?.extras?.getSerializable("city") as City?
        city?.let { city ->
            viewModel.getWeather(city.apiCityId, city.id, false)
            supportActionBar?.title = city.fullName
        }

        viewModel.weather.observe(this, Observer { weather ->
            tvCurrentTemp.text = String.format(getString(R.string.current_temp), weather.temp.toString())
            tvMinTemp.text = String.format(getString(R.string.min_temp), weather.tempMin.toString())
            tvMaxTemp.text = String.format(getString(R.string.max_temp), weather.tempMax.toString())
            tvWindSpeed.text = String.format(getString(R.string.wind_speed), weather.speed.toString())
            tvWindDeg.text = String.format(getString(R.string.wind_deg), weather.deg.toString())
            tvDate.text = String.format(getString(R.string.date), weather.date)
            tvCityId.text = String.format(getString(R.string.city_id), weather.cityIdLocal)
        })

        viewModel.isProgressShown.observe(this, Observer {
            if (it) showLoading()
            else hideLoading()
        })

        fab.setOnClickListener {
            // Смена ИД города, должно автоматически поменятся ид города и у погоды
            viewModel.changeCityId(city!!)
        }
    }

    fun showLoading() {
        mProgressDialog = MaterialDialog(this@WeatherActivity)
            .noAutoDismiss()
            .customView(R.layout.progress_dialog_layout)
        mProgressDialog?.show()
    }

    fun hideLoading() {
        mProgressDialog?.dismiss()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
            R.id.action_refresh -> city?.let { city -> viewModel.getWeather(city.apiCityId, city.id, true) }
        }
        return super.onOptionsItemSelected(item)
    }
}
