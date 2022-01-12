package com.example.weathernow

import android.animation.Animator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.weathernow.databinding.FragmentErrorBinding
import com.example.weathernow.repository.Response
import com.example.weathernow.utils.LocationCheckUtils
import com.example.weathernow.viewmodel.MainViewModel

class ErrorFragment : Fragment() {

    lateinit var binding: FragmentErrorBinding
    lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentErrorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lottieForError.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {

            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationRepeat(animation: Animator?) {

            }
        })

        binding.retryBtn.setOnClickListener() {
            if (LocationCheckUtils.isLocationEnabled(requireActivity())) {
                findNavController().navigateUp()
            }
        }
    }


}