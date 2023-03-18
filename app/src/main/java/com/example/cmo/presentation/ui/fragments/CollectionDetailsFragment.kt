package com.example.cmo.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cmo.data.local.pojo.Quote
import com.example.cmo.databinding.FragmentCollectionDetailsBinding
import com.example.cmo.other.replaceFragment
import com.example.cmo.presentation.ui.activities.MainActivity
import com.example.cmo.presentation.ui.adapters.QuotesAdapter
import com.example.cmo.presentation.viewmodel.MainViewModel

class CollectionDetailsFragment : Fragment() {

    private var _binding: FragmentCollectionDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var parentActivity: MainActivity

    private lateinit var adapter: QuotesAdapter

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCollectionDetailsBinding.inflate(layoutInflater, container, false)

        parentActivity = requireActivity() as MainActivity

        viewModel = ViewModelProvider(parentActivity)[MainViewModel::class.java]

        adapter = QuotesAdapter { position -> viewModel.bookmarkQuote(adapter.itemAt(position)) }

        bindViews()
        setupRecycler()
        handleActions()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindViews() {
        binding.includeEmpty.emptyWrapper.isVisible = false

        parentActivity.binding.includeToolbar.goBack.isVisible = true
        parentActivity.binding.includeToolbar.search.isVisible = false
    }

    private fun setupRecycler() {
        binding.recycler.adapter = adapter
        binding.recycler.setHasFixedSize(true)
        binding.recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
    }

    private fun handleActions() {
        binding.swipe.setOnRefreshListener {
            binding.swipe.isRefreshing = true
            getData()
            binding.swipe.isRefreshing = false
        }
    }

    private fun getData() {
        viewModel.sharedQuotesList.observe(viewLifecycleOwner) {
            it?.let {
                adapter.setItems(it.second as ArrayList<Quote>)
                successViewsSetup(it.first)
            } ?: run { emptyViewsSetup() }
        }

        parentActivity.binding.includeToolbar.goBack.setOnClickListener {
            replaceFragment(
                activity = parentActivity,
                fragment = CollectionsFragment(),
                subtitle = "Collections"
            )
        }
    }

    private fun emptyViewsSetup() {
        binding.includeEmpty.emptyWrapper.isVisible = true

        binding.dataWrapper.isVisible = false
    }

    private fun successViewsSetup(fragmentTitle: String) {
        binding.includeEmpty.emptyWrapper.isVisible = false

        binding.dataWrapper.isVisible = true
    }
}