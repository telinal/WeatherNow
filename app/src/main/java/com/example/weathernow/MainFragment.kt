package com.example.weathernow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.weathernow.api.RetrofitHelper
import com.example.weathernow.api.WeatherAPI
import com.example.weathernow.databinding.FragmentMainBinding
import com.example.weathernow.models.Current
import com.example.weathernow.models.WeatherResponse
import com.example.weathernow.repository.Response
import com.example.weathernow.repository.WeatherRepository
import com.example.weathernow.viewmodel.MainViewModel
import com.example.weathernow.viewmodel.MainViewModelFactory

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        mainViewModel.currentLive.observe(viewLifecycleOwner){
            when(it){
                is Response.Error -> {
                    binding.progressBar.visibility = GONE
                    Toast.makeText(activity, it.errorMessage.toString(), Toast.LENGTH_SHORT).show()
                }
                is Response.Loading -> {
                    binding.progressBar.visibility = VISIBLE
                }
                is Response.Success -> {
                    binding.progressBar.visibility = GONE
                    bindData(it.data)
                }
            }
        }

        mainViewModel.getCurrentWeather("guwahati")
    }

    private fun bindData(data: WeatherResponse?) {
        with(binding){

        }

    }


}