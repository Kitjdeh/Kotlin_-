package com.example.coinmonitoring.view.intro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.coinmonitoring.R
import com.example.coinmonitoring.databinding.FragmentIntro1Binding
import timber.log.Timber


class IntroFragment1 : Fragment() {
    private var _binding : FragmentIntro1Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIntro1Binding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("현재 intro11111화면")
        binding.nextBtn.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_introFragment1_to_introFragment2)
        }
    }

    //fragment가 없어 졌을 때의 처리
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}