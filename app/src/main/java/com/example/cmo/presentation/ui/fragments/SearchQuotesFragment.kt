package com.example.cmo.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.content.res.AppCompatResources
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
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class SearchQuotesFragment : Fragment() {

    private val TITLE = "Search Quotes"

    private val TAG = "SearchQuotesFragment"

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

        bindViews()
        setupRecycler()
        handleActions()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        if (viewModel.animeSearchedQuotesList.value?.data.isNullOrEmpty()){
            getData()
        }
        super.onResume()
    }

    private fun bindViews() {
        binding.searchFetchingProgressWrapper.hide()
        binding.searchEmptyQueryMessage.hide()
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

        binding.searchRecycler.adapter = adapter
        binding.searchRecycler.setHasFixedSize(true)
        binding.searchRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun handleActions() {
        binding.searchSwipe.setOnRefreshListener {
            binding.searchSwipe.isRefreshing = true
            getData()
            binding.searchSwipe.isRefreshing = false
        }
        binding.searchIconWrapper.setOnClickListener {
            if (!validateQuery()) {
                binding.searchEmptyQueryMessage.show()
            } else {
                binding.searchEmptyQueryMessage.hide()
                getData()
            }
        }
    }

    private fun View.hide() {
        this.isVisible = false
    }

    private fun View.show() {
        this.isVisible = true
    }

    private fun validateQuery(): Boolean {
        val queryPattern = "(anime|Anime|ANIME|char|Char|CHAR)( )*:( )*(.)+".toRegex()
        return binding.searchBox.text.isNotEmpty() &&
                binding.searchBox.text.matches(queryPattern)
    }

    private fun getData() {

        val searchQuery = binding.searchBox.text.split(":")
        when (searchQuery[0].lowercase().trim()) {
            "anime" -> viewModel.getQuotesByAnime(searchQuery[1].lowercase().trim())
            "char" -> viewModel.getQuotesByCharacter(searchQuery[1].lowercase().trim())
        }

        viewModel.animeSearchedQuotesList.observe(requireActivity()) {

            when (it) {
                is Resource.Loading -> {
                    binding.searchFetchingProgressWrapper.show()
                    binding.searchViewsWrapper.hide()
                    binding.searchFetchingResultsWrapper.hide()
                }
                is Resource.Success -> {
                    if (it.data.isNullOrEmpty()) {
                        binding.searchFetchingResultsWrapper.show()

                        binding.searchFetchingProgressWrapper.show()
                        binding.searchProgress.hide()
                        binding.searchViewsWrapper.show()
                        binding.searchErrorView.hide()

                    } else {
                        binding.searchFetchingResultsWrapper.show()
                        binding.searchFetchingProgressWrapper.hide()
                        adapter.setItems(it.data!!)
                    }
                }
                else -> {
                    binding.searchFetchingProgressWrapper.show()
                    binding.searchViewsWrapper.show()
                    binding.searchEmptyView.hide()
                    binding.searchProgress.hide()
                    binding.searchFetchingResultsWrapper.hide()
                }
            }
        }
    }
}