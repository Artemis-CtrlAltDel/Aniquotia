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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

    override fun onResume() {
        if (viewModel.animeQuotesList.value?.data?.isNotEmpty() == true){
            getData()
        }
        super.onResume()
    }

    private fun bindViews(){
        binding.remoteViewsWrapper.hide()
    }

    private fun setupRecycler() {
        adapter = MainAdapter(
            items = arrayListOf(),
            onItemClick = object : OnItemClick {
                override fun onBookmarkClick(position: Int) {
                    bookmarkQuote(adapter.itemAt(position), viewModel)
                }
            }
        )

        binding.remoteRecycler.adapter = adapter
        binding.remoteRecycler.setHasFixedSize(true)
        binding.remoteRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun handleActions() {
        binding.remoteSwipe.setOnRefreshListener {
            refresh()
        }
        binding.fab.setOnClickListener {
            refresh()
        }
    }

    private fun refresh() {
        binding.remoteSwipe.isRefreshing = true
        getData()
        binding.remoteSwipe.isRefreshing = false
    }

    private fun View.hide() {
        this.isVisible = false
    }

    private fun View.show() {
        this.isVisible = true
    }

    private fun getData() {

        viewModel.getQuotes()

        viewModel.animeQuotesList.observe(requireActivity()) {
            binding.remoteFetchingResultsWrapper.hide()
            binding.remoteFetchingProgressWrapper.hide()

            when (it) {
                is Resource.Loading -> {
                    binding.remoteFetchingProgressWrapper.show()
                    binding.remoteViewsWrapper.hide()
                    binding.remoteFetchingResultsWrapper.hide()
                    binding.fab.hide()
                }
                is Resource.Success -> {
                    if (it.data.isNullOrEmpty()) {
                        binding.remoteFetchingResultsWrapper.show()

                        binding.remoteFetchingProgressWrapper.show()
                        binding.remoteProgress.hide()
                        binding.remoteViewsWrapper.show()
                        binding.remoteErrorView.hide()

                        binding.fab.hide()

                    } else {
                        binding.remoteFetchingResultsWrapper.show()
                        binding.remoteFetchingProgressWrapper.hide()
                        adapter.setItems(it.data!!)
                        binding.fab.show()
                    }
                }
                else -> {
                    binding.remoteFetchingProgressWrapper.show()
                    binding.remoteViewsWrapper.show()
                    binding.remoteEmptyView.hide()
                    binding.remoteProgress.hide()
                    binding.remoteFetchingResultsWrapper.hide()
                    binding.fab.show()
                }
            }
        }
    }

}