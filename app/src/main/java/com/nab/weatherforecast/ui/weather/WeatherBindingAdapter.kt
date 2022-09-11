package com.nab.weatherforecast.ui.weather

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nab.domain.models.GetWeatherResult

@BindingAdapter("searchResultItems", "viewModel")
fun searchResultItems(
    recyclerView: RecyclerView, searchResultItems: List<GetWeatherResult.Weather>?,
    viewModel: WeatherViewModel
) {
    searchResultItems?.let {
        recyclerView.adapter =
            (recyclerView as? WeatherResultAdapter ?: WeatherResultAdapter(viewModel).apply {
                data = searchResultItems
            })
    }

}

@BindingAdapter("searchHistoryResultItems", "viewModel")
fun searchHistoryResultItems(
    recyclerView: RecyclerView, searchHistoryResultItems: List<GetWeatherResult.Weather>?,
    viewModel: WeatherViewModel
) {
    searchHistoryResultItems?.let {
        recyclerView.adapter =
            (recyclerView as? WeatherHistoryAdapter ?: WeatherHistoryAdapter(viewModel).apply {
                data = searchHistoryResultItems
            })
    }
}
