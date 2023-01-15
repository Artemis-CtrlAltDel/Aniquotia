package com.example.cmo.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cmo.databinding.FragmentSearchQuotesBinding
import com.example.cmo.other.Resource
import com.example.cmo.other.bookmarkQuote
import com.example.cmo.presentation.ui.adapters.QuotesAdapter
import com.example.cmo.presentation.ui.adapters.OnQuoteClick
import com.example.cmo.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchQuotesFragment : Fragment() {

    private var _binding: FragmentSearchQuotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: QuotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchQuotesBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        bindViews()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
    }

    private fun bindViews() {
    }

    private fun validateQuery(): Boolean {
//        val queryPattern = "(anime|Anime|ANIME|char|Char|CHAR)( )*:( )*(.)+".toRegex()
//        return binding.searchBox.text.isNotEmpty() &&
//                binding.searchBox.text.matches(queryPattern)

        return true
    }

    private fun getData() {

//        val searchQuery = binding.searchBox.text.split(":")
//        when (searchQuery[0].lowercase().trim()) {
//            "anime" -> viewModel.getQuotesByAnime(searchQuery[1].lowercase().trim())
//            "char" -> viewModel.getQuotesByCharacter(searchQuery[1].lowercase().trim())
//        }
//
//        viewModel.animeSearchedQuotesList.observe(requireActivity()) {
//
//
//        }
    }
}