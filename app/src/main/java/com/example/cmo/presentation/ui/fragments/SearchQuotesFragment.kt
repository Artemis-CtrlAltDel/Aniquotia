package com.example.cmo.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cmo.R
import com.example.cmo.databinding.FragmentSearchQuotesBinding
import com.example.cmo.other.Resource
import com.example.cmo.other.bookmarkQuote
import com.example.cmo.presentation.ui.adapters.MainAdapter
import com.example.cmo.presentation.ui.adapters.OnItemClick
import com.example.cmo.presentation.viewmodel.MainViewModel

class SearchQuotesFragment : Fragment() {

    private val TITLE = "Search Quotes"

    private var _binding: FragmentSearchQuotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchQuotesBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        // get data

        adapter = MainAdapter(
            items = arrayListOf(),
            onItemClick = object : OnItemClick {
                override fun onBookmarkClick(position: Int) {
                    bookmarkQuote(adapter.itemAt(position), viewModel)
                }
            }
        )

        binding.swipe.setOnRefreshListener {
            // get data
            binding.swipe.isRefreshing = false
        }

        binding.recycler.adapter = adapter
        binding.recycler.setHasFixedSize(true)
        binding.recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getData(pairTypeValue: Pair<String, String>) {
        when (pairTypeValue.first){
            "ANIME:" -> viewModel.getQuotesByAnime(pairTypeValue.second)
            "CHARACTER:" -> viewModel.getRandomQuoteByCharacter(pairTypeValue.second)
        }

        viewModel.animeQuotesList.observe(requireActivity()){
            binding.error.isVisible = false
            with(binding){
                when (it) {
                    is Resource.Loading -> {
                        progressErrorContainer.isVisible = true
                        progress.isVisible = true

                        recyclerContainer.isVisible = false
                    }
                    is Resource.Success -> {
                        progressErrorContainer.isVisible = false
                        progress.isVisible = false

                        recyclerContainer.isVisible = true
                        it.data?.let { adapter.setData(it) }
                    }
                    else -> {
                        progressErrorContainer.isVisible = true
                        progress.isVisible = false
                        error.isVisible = true

                        recyclerContainer.isVisible = false
                    }
                }
            }
        }
    }
}