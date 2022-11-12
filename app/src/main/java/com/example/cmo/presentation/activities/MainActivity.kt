package com.example.cmo.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cmo.databinding.ActivityMainBinding
import com.example.cmo.presentation.adapters.MainAdapter
import com.example.cmo.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    private val adapter = MainAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide()

        viewModel = ViewModelProvider(this@MainActivity)[MainViewModel::class.java]

        viewModel.getQuotes()
        viewModel.animeQuotesList.observeForever {
            it?.let { adapter.setData(it) }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.apply {

            recycler.adapter = adapter
            recycler.setHasFixedSize(true)
            recycler.layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

            setContentView(root)
        }
    }
}