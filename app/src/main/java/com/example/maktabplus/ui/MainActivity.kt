package com.example.maktabplus.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.maktabplus.R
import com.example.maktabplus.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}