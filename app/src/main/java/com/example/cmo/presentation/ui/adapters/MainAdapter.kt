package com.example.cmo.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cmo.data.network.AnimeQuotesApiDto
import com.example.cmo.databinding.CardQuoteDetailsBinding

class MainAdapter(private var items: ArrayList<AnimeQuotesApiDto>) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardQuoteDetailsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardQuoteDetailsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            binding.anime.text = items[position].anime
            binding.quote.text = items[position].quote
            binding.character.text = "- ${items[position].character}"
        }
    }

    override fun getItemCount() = items.size

    fun setData(data: ArrayList<AnimeQuotesApiDto>){
        items = data
        notifyDataSetChanged()
    }
}