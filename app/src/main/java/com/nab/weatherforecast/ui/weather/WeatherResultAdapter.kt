package com.nab.weatherforecast.ui.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nab.domain.models.GetWeatherResult
import com.nab.weatherforecast.databinding.ItemWeatherResultBinding

class WeatherResultAdapter(private val viewModel: WeatherViewModel) :
    RecyclerView.Adapter<WeatherResultAdapter.ViewHolder>() {

    lateinit var data: List<GetWeatherResult.Weather>

    class ViewHolder(private val binding: ItemWeatherResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(resultItem: GetWeatherResult.Weather, viewModel: WeatherViewModel) {
            binding.resultItem = resultItem
            binding.viewModel = viewModel
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemWeatherResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position],viewModel)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}