package com.example.cmo.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cmo.R
import com.example.cmo.data.local.pojo.AnimeQuote
import com.example.cmo.databinding.FragmentSavedQuotesBinding
import com.example.cmo.other.bookmarkQuote
import com.example.cmo.presentation.ui.adapters.MainAdapter
import com.example.cmo.presentation.ui.adapters.OnItemClick
import com.example.cmo.presentation.viewmodel.MainViewModel

class SavedQuotesFragment : Fragment() {

    val TITLE = "Favorite Quotes"

    private var _binding: FragmentSavedQuotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MainAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSavedQuotesBinding.inflate(inflater, container, false)

        adapter = MainAdapter(
            items = arrayListOf(),
            onItemClick = object: OnItemClick {
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

    private fun getData(){
        viewModel.getSavedQuotes()
        viewModel.animeSavedQuotesList.observe(requireActivity()) {
            it?.let { adapter.setData(ArrayList(it)) }
        }
    }

}