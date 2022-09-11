package com.nab.weatherforecast.ui.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nab.domain.models.GetWeatherResult
import com.nab.weatherforecast.databinding.ItemWeatherHistoryResultBinding

class WeatherHistoryAdapter(private val viewModel: WeatherViewModel) :
    RecyclerView.Adapter<WeatherHistoryAdapter.ViewHolder>() {

    lateinit var data : List<GetWeatherResult.Weather>
    class ViewHolder(private val binding: ItemWeatherHistoryResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(resultItem: GetWeatherResult.Weather, viewModel: WeatherViewModel) {
            binding.resultHistoryItem = resultItem
            binding.viewModel = viewModel
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemWeatherHistoryResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], viewModel)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}