package com.example.cmo.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cmo.databinding.FragmentSavedQuotesBinding
import com.example.cmo.other.bookmarkQuote
import com.example.cmo.presentation.ui.adapters.QuotesAdapter
import com.example.cmo.presentation.ui.adapters.OnQuoteClick
import com.example.cmo.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedQuotesFragment : Fragment() {

    private var _binding: FragmentSavedQuotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: QuotesAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSavedQuotesBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        getData()
        handleActions()
        setupRecycler()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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

        binding.savedRecycler.adapter = adapter
        binding.savedRecycler.setHasFixedSize(true)
        binding.savedRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
    }

    private fun handleActions() {
        binding.savedSwipe.setOnRefreshListener {
            binding.savedSwipe.isRefreshing = true
            getData()
            binding.savedSwipe.isRefreshing = false
        }
    }

    private fun getData(){
        viewModel.getSavedQuotes()
        viewModel.animeSavedQuotesList.observe(requireActivity()) {
            it?.let { adapter.setItems(ArrayList(it)) }
        }
    }

}