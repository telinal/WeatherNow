package com.example.weathernow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.weathernow.api.RetrofitHelper
import com.example.weathernow.api.WeatherAPI
import com.example.weathernow.databinding.FragmentMainBinding
import com.example.weathernow.repository.WeatherRepository
import com.example.weathernow.viewmodel.MainViewModel
import com.example.weathernow.viewmodel.MainViewModelFactory

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    private fun initialize() {
        val repository = (requireActivity().application as WeatherApplication).currentWeatherRepository
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository = repository))[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

}