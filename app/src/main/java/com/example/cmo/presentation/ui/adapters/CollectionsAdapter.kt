package com.example.cmo.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cmo.data.local.pojo.Details
import com.example.cmo.databinding.FragmentSavedQuotesBinding

class CollectionsAdapter(
    private var items: ArrayList<Details>,
    private val onCollectionClick: OnCollectionClick
) : RecyclerView.Adapter<CollectionsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: FragmentSavedQuotesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentSavedQuotesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder) {
        val ctx = binding.savedSwipe
    }

    override fun getItemCount() = items.size

    fun setItems(data: ArrayList<Details>) {
        items = data
        notifyDataSetChanged()
    }

    fun itemAt(position: Int) = items[position]
}