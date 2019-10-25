package maxville.testweatherapp.presentation.city

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.city_list_item.view.*
import maxville.testweatherapp.R
import maxville.testweatherapp.data.common.City

class CityAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var cityCallback: ((City) -> (Unit))? = null
    var cityList: List<City> = mutableListOf()

    class CityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val citeName: TextView = view.tvCity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CityViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.city_list_item,
                parent,
                false
            )
        )
    }

    fun updateCities(newCityList: List<City>) {
        cityList = newCityList
        notifyDataSetChanged()
    }

    override fun getItemCount() = cityList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val city = cityList[holder.adapterPosition]
        (holder as CityViewHolder).citeName.text = city.fullName
        holder.citeName.setOnClickListener { cityCallback?.let { callback -> callback(city) } }
    }

}