package com.example.cmo.presentation.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cmo.databinding.ActivityMainBinding
import com.example.cmo.other.bookmarkQuote
import com.example.cmo.presentation.ui.adapters.MainAdapter
import com.example.cmo.presentation.ui.adapters.OnItemClick
import com.example.cmo.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide()

        viewModel = ViewModelProvider(this@MainActivity)[MainViewModel::class.java]

        getData()

        adapter = MainAdapter(
            items = arrayListOf(),
            onItemClick = object: OnItemClick {
                override fun onBookmarkClick(position: Int) {
                    bookmarkQuote(adapter.itemAt(position), viewModel)
                }

            }
        )

        binding = ActivityMainBinding.inflate(layoutInflater)
        with(binding) {

            swipe.setOnRefreshListener {
                getData()
                swipe.isRefreshing = false
            }

            recycler.adapter = adapter
            recycler.setHasFixedSize(true)
            recycler.layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

            setContentView(root)
        }
    }

    private fun getData(){
        viewModel.getQuotes()
        viewModel.animeQuotesList.observe(this) {
            it?.let { adapter.setData(it) }
        }
    }
}