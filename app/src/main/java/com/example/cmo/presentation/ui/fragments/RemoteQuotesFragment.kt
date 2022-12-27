package com.example.cmo.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cmo.databinding.FragmentRemoteQuotesBinding
import com.example.cmo.other.Resource
import com.example.cmo.other.bookmarkQuote
import com.example.cmo.presentation.ui.adapters.MainAdapter
import com.example.cmo.presentation.ui.adapters.OnItemClick
import com.example.cmo.presentation.viewmodel.MainViewModel

class RemoteQuotesFragment : Fragment() {

    val TITLE = "Anime Quotes"

    private var _binding: FragmentRemoteQuotesBinding? = null
    private val binding get() = _binding!!


    private lateinit var adapter: MainAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRemoteQuotesBinding.inflate(inflater, container, false)

        adapter = MainAdapter(
            items = arrayListOf(),
            onItemClick = object : OnItemClick {
                override fun onBookmarkClick(position: Int) {
                    bookmarkQuote(adapter.itemAt(position), viewModel)
                }
            }
        )

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        getData()

        binding.swipe.setOnRefreshListener {
            getData()
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

    private fun getData() {
        viewModel.getQuotes()
        viewModel.animeQuotesList.observe(requireActivity()) {
            binding.error.isVisible = false
            with(binding){
                when (it) {
                    is Resource.Loading -> {
                        progress.isVisible = true
                        recycler.isVisible = false
                    }
                    is Resource.Success -> {
                        progress.isVisible = false
                        recycler.isVisible = true
                        it.data?.let { adapter.setData(it) }
                    }
                    else -> {
                        progress.isVisible = false
                        recycler.isVisible = false
                        error.isVisible = true
                    }
                }
            }
        }
    }

}