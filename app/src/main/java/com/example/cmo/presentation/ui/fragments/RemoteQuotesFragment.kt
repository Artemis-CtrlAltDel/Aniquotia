package com.example.cmo.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
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
import com.example.cmo.other.isConnected
import com.example.cmo.presentation.ui.adapters.QuotesAdapter
import com.example.cmo.presentation.ui.adapters.OnQuoteClick
import com.example.cmo.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RemoteQuotesFragment : Fragment() {

    private var _binding: FragmentRemoteQuotesBinding? = null
    private val binding get() = _binding!!


    private lateinit var adapter: QuotesAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRemoteQuotesBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        bindViews()
        setupRecycler()
        getData()
        handleActions()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun bindViews() {
//        binding.dataWrapper.isVisible = false
//        binding.includeProgress.progressWrapper.isVisible = false
//        binding.includeEmpty.emptyWrapper.isVisible = false
//        binding.includeError.errorWrapper.isVisible = false
//
//        println("Testttttttttttttttttttt")
//        if (!isConnected(requireContext())) {
//            println("It's not connected")
//            binding.includeError.errorWrapper.isVisible = true
//        } else {
//            println("It's connected")
//            binding.includeEmpty.emptyWrapper.isVisible = true
//        }
    }

    private fun setupRecycler() {
        adapter = QuotesAdapter(
            items = arrayListOf(),
            onQuoteClick = object : OnQuoteClick {
                override fun onBookmarkClick(position: Int) {
                    bookmarkQuote(adapter.itemAt(position), viewModel)
                }
            }
        )

        binding.recycler.adapter = adapter
        binding.recycler.setHasFixedSize(true)
        binding.recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun handleActions() {
        binding.swipe.setOnRefreshListener {
            refresh()
        }
        binding.fab.setOnClickListener {
            refresh()
        }
        binding.includeError.tryAgain.setOnClickListener {

        }
    }

    private fun refresh() {
//        binding.swipe.isRefreshing = true
//        getData()
//        binding.swipe.isRefreshing = false
    }

    private fun getData() {

//        viewModel.getQuotes()
//
//        viewModel.animeQuotesList.observe(requireActivity()) {
//
//        }
    }

}