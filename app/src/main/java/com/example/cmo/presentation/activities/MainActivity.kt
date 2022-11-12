package com.example.cmo.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.cmo.databinding.ActivityMainBinding
import com.example.cmo.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this@MainActivity)[MainViewModel::class.java]

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.apply {

            setContentView(root)

            generateQuote.setOnClickListener {
                viewModel.getQuotes()
                viewModel.animeQuotesList.observeForever {
                    
                }
            }

        }
    }
}