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
import com.example.cmo.R
import com.example.cmo.data.local.pojo.Quote
import com.example.cmo.databinding.FragmentSavedQuotesBinding
import com.example.cmo.other.format
import com.example.cmo.other.replaceFragment
import com.example.cmo.presentation.ui.activities.MainActivity
import com.example.cmo.presentation.ui.adapters.QuotesAdapter
import com.example.cmo.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedQuotesFragment : Fragment() {

    private var _binding: FragmentSavedQuotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var parentActivity: MainActivity

    private lateinit var adapter: QuotesAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSavedQuotesBinding.inflate(inflater, container, false)

        parentActivity = requireActivity() as MainActivity

        viewModel = ViewModelProvider(parentActivity)[MainViewModel::class.java]

        adapter = QuotesAdapter { position -> viewModel.bookmarkQuote(adapter.itemAt(position)) }

        bindViews()
        setupRecycler()
        handleActions()
        getData()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindViews() {
        binding.includeEmpty.emptyWrapper.isVisible = false

        parentActivity.binding.includeToolbar.toolbar.title =
            getString(R.string.toolbar_saved_title)
        parentActivity.binding.includeToolbar.goBack.isVisible = false
        parentActivity.binding.includeToolbar.search.isVisible = false
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

        binding.gotoCollections.setOnClickListener {
            replaceFragment(
                activity = parentActivity,
                fragment = CollectionsFragment(),
                subtitle = getString(R.string.toolbar_collection_subtitle)
            )
        }
    }

    private fun getData() {
        viewModel.animeSavedQuotesList.observe(viewLifecycleOwner) {
            it?.let {
                adapter.setItems(ArrayList(it))
                successViewsSetup(
                    it.size,
                    it.groupBy { quote -> quote.anime }.size
                )
            } ?: run { emptyViewsSetup() }
        }
    }

    private fun emptyViewsSetup() {
        binding.includeEmpty.emptyWrapper.isVisible = true

        binding.dataWrapper.isVisible = false
        binding.collectionsWrapper.isVisible = false
    }

    private fun successViewsSetup(quotesCount: Int, collectionsCount: Int) {
        binding.includeEmpty.emptyWrapper.isVisible = false

        binding.dataWrapper.isVisible = true
        binding.collectionsWrapper.isVisible = true

        binding.quotesCount.text =
            resources.getQuantityString(
                R.plurals.other_quotes,
                quotesCount,
                quotesCount.toLong().format()
            )
        binding.collectionsCount.text =
            resources.getQuantityString(
                R.plurals.other_collections,
                collectionsCount,
                collectionsCount.toLong().format()
            )
    }

}