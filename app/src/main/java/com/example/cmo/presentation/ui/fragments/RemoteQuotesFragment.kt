package com.example.cmo.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cmo.R
import com.example.cmo.data.local.pojo.Quote
import com.example.cmo.databinding.FragmentRemoteQuotesBinding
import com.example.cmo.other.Resource
import com.example.cmo.other.isConnected
import com.example.cmo.presentation.ui.activities.MainActivity
import com.example.cmo.presentation.ui.adapters.QuotesAdapter
import com.example.cmo.presentation.viewmodel.MainViewModel

class RemoteQuotesFragment : Fragment() {

    private lateinit var parentActivity: MainActivity

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

        parentActivity = requireActivity() as MainActivity

        adapter = QuotesAdapter { position -> viewModel.bookmarkQuote(adapter.itemAt(position)) }

        bindViews()
        setupRecycler()
        handleActions()

        // TODO : please do consider cleaning this shi, respectfully c:
        if (viewModel.animeQuotesList.value != null) {
            if (viewModel.animeSearchedQuotesList.value == null) {
                getData()
            } else {
                getDataFromSearch()
            }
        } else {
            if (viewModel.animeSearchedQuotesList.value == null) {
                getDataForFlag(isRandom = true)
            } else {
                getDataForFlag(isRandom = false)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindViews() {
        binding.fab.hide()

        binding.dataWrapper.isVisible = false
        binding.includeEmpty.emptyWrapper.isVisible = false
        binding.includeError.errorWrapper.isVisible = false
        binding.includeProgress.progressWrapper.isVisible = true

        parentActivity.binding.includeToolbar.search.isVisible = true
        parentActivity.binding.includeToolbar.goBack.isVisible = false

        if (!isConnected(requireContext())) {
            binding.includeError.errorWrapper.isVisible = true
            binding.includeProgress.progressWrapper.isVisible = false
        } else if (adapter.itemCount == 0) {
            binding.includeEmpty.emptyWrapper.isVisible = true
            binding.includeProgress.progressWrapper.isVisible = false
        } else {
            binding.dataWrapper.isVisible = true
            binding.includeProgress.progressWrapper.isVisible = false
        }
    }

    private fun setupRecycler() {
        binding.recycler.adapter = adapter
        binding.recycler.setHasFixedSize(true)
        binding.recycler.layoutManager =
            LinearLayoutManager(parentActivity, LinearLayoutManager.VERTICAL, false)
    }

    private fun refresh(isRandom: Boolean = true) {
        binding.swipe.isRefreshing = true
        getDataForFlag(isRandom)
        binding.swipe.isRefreshing = false
    }

    private fun handleActions() {
        binding.nestedScroll.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY) {
                binding.fab.show()
            } else {
                binding.fab.hide()
            }
        }
        binding.swipe.setOnRefreshListener {
            refresh()
        }
        binding.fab.setOnClickListener {
            refresh()
        }
        binding.includeError.tryAgain.setOnClickListener {
            onResume()
            bindViews()
        }
        parentActivity.binding.includeToolbar.search.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                getDataForFlag(isRandom = false)
                parentActivity.binding.includeToolbar.search.onActionViewCollapsed()
                return false
            }

            override fun onQueryTextChange(newText: String?) = false
        })
    }

    private fun validateQuery(query: String): Boolean {
        val queryPattern = "(anime|Anime|ANIME|char|Char|CHAR)( )*:( )*(.)+".toRegex()

        return query.isNotEmpty() &&
                query.matches(queryPattern)
    }

    private fun getDataForFlag(isRandom: Boolean = true) {

        if (isRandom) {
            parentActivity.binding.includeToolbar.toolbar.title =
                getString(R.string.toolbar_remote_title)
            parentActivity.binding.includeToolbar.toolbar.subtitle = ""

            binding.fragmentTitle.text = getString(R.string.fragment_remote_title)

            viewModel.getQuotes()
            getData()
        } else {
            val query = parentActivity.binding.includeToolbar.search.query

            if (validateQuery(query.toString())) {
                val queryList = query.split(":").toList().map { it.trim() }

                parentActivity.binding.includeToolbar.toolbar.title =
                    getString(R.string.toolbar_search_title)
                parentActivity.binding.includeToolbar.toolbar.subtitle =
                    queryList[1].replaceFirstChar { it.uppercase() }

                binding.fragmentTitle.text = getString(
                    R.string.fragment_search_title,
                    queryList[1].replaceFirstChar { it.uppercase() },
                    getString(R.string.other_quotes)
                )

                if (queryList[0] == "anime") {
                    viewModel.getQuotesByAnime(queryList[1])
                } else {
                    viewModel.getQuotesByCharacter(queryList[1])
                }
                getDataFromSearch()
            } else {
                Toast.makeText(requireContext(), "Invalid query format", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getDataFromSearch() {
        viewModel.animeSearchedQuotesList.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success<ArrayList<Quote>> -> {
                    it.data?.let { data ->
                        adapter.setItems(data)
                        successViewsSetup()
                    } ?: run { bindViews() }
                }
                is Resource.Loading<ArrayList<Quote>> -> {
                    loadingViewsSetup()
                }
                else -> {
                    bindViews()
                }
            }
        }
    }

    private fun getData() {
        viewModel.animeQuotesList.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success<ArrayList<Quote>> -> {
                    it.data?.let { data ->
                        adapter.setItems(data)
                        successViewsSetup()
                    } ?: run { bindViews() }
                }
                is Resource.Loading<ArrayList<Quote>> -> {
                    loadingViewsSetup()
                }
                else -> {
                    bindViews()
                }
            }
        }
    }

    private fun successViewsSetup() {
        binding.dataWrapper.isVisible = true
        binding.includeEmpty.emptyWrapper.isVisible = false
        binding.includeError.errorWrapper.isVisible = false
        binding.includeProgress.progressWrapper.isVisible = false
    }

    private fun loadingViewsSetup() {
        binding.dataWrapper.isVisible = false
        binding.includeEmpty.emptyWrapper.isVisible = false
        binding.includeError.errorWrapper.isVisible = false
        binding.includeProgress.progressWrapper.isVisible = true
    }

    // TODO : Probably gonna customize that later
//    private fun errorViewsSetup(){
//        bindViews()
//    }
}