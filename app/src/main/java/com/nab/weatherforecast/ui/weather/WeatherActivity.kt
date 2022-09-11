package com.nab.weatherforecast.ui.weather

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nab.weatherforecast.Application
import com.nab.weatherforecast.DefaultServerLocator
import com.nab.weatherforecast.databinding.ActivityMainBinding
import com.nab.weatherforecast.ui.common.ViewModelFactory
import com.nab.weatherforecast.ui.common.errorTextCase

class WeatherActivity : AppCompatActivity() {

    private val viewModel: WeatherViewModel by viewModels {
        ViewModelFactory(DefaultServerLocator.getInstance(application as Application))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).apply {
            viewModel = this@WeatherActivity.viewModel
            lifecycleOwner = this@WeatherActivity
        }.root)

        viewModel.showErrorToastEvent.observe(this, { event ->
            event.getNeedHandle()?.let { error ->

                Toast.makeText(
                    this@WeatherActivity,
                    errorTextCase(error),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        viewModel.toHandleEcryptData()
    }

}