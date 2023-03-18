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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cmo.R
import com.example.cmo.data.local.pojo.Quote
import com.example.cmo.databinding.FragmentCollectionsBinding
import com.example.cmo.other.replaceFragment
import com.example.cmo.presentation.ui.activities.MainActivity
import com.example.cmo.presentation.ui.adapters.CollectionsAdapter
import com.example.cmo.presentation.ui.adapters.OnCollectionClick
import com.example.cmo.presentation.viewmodel.MainViewModel

class CollectionsFragment : Fragment() {

    private var _binding: FragmentCollectionsBinding? = null
    private val binding get() = _binding!!

    private lateinit var parentActivity: MainActivity

    private lateinit var adapter: CollectionsAdapter

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCollectionsBinding.inflate(layoutInflater, container, false)

        parentActivity = requireActivity() as MainActivity

        viewModel = ViewModelProvider(parentActivity)[MainViewModel::class.java]

        adapter = CollectionsAdapter { position ->
            viewModel.setCollectionQuotes(adapter.itemAt(position))
            replaceFragment(
                activity = parentActivity,
                fragment = CollectionDetailsFragment(),
                subtitle = getString(
                    R.string.toolbar_collection_details_subtitle,
                    adapter.itemAt(position).first
                )
            )
        }

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
        parentActivity.binding.includeToolbar.goBack.isVisible = true
        parentActivity.binding.includeToolbar.search.isVisible = false

        binding.includeEmpty.emptyWrapper.isVisible = true
        binding.dataWrapper.isVisible = false
    }

    private fun setupRecycler() {
        binding.recycler.adapter = adapter
        binding.recycler.setHasFixedSize(true)
        binding.recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun handleActions() {
        binding.swipe.setOnRefreshListener {
            binding.swipe.isRefreshing = true
            getData()
            binding.swipe.isRefreshing = false
        }

        parentActivity.binding.includeToolbar.goBack.setOnClickListener {
            replaceFragment(
                activity = parentActivity,
                fragment = SavedQuotesFragment(),
                subtitle = ""
            )
        }
    }

    private fun getData() {
        viewModel.animeSavedQuotesList.observe(viewLifecycleOwner) {
            it?.let {
                val actualData = arrayListOf<Pair<String, List<Quote>>>()
                (it.groupBy { quote -> quote.anime } as HashMap).forEach { pair ->
                    actualData.add(Pair(pair.key, pair.value))
                }
                adapter.setItems(actualData)
                successViewsSetup()
            } ?: run { emptyViewsSetup() }
        }
    }

    private fun emptyViewsSetup() {
        binding.includeEmpty.emptyWrapper.isVisible = true

        binding.dataWrapper.isVisible = false
    }

    private fun successViewsSetup() {
        binding.includeEmpty.emptyWrapper.isVisible = false

        binding.dataWrapper.isVisible = true
    }
}