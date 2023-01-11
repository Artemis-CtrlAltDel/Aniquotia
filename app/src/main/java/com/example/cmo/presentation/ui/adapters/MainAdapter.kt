package com.example.cmo.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.cmo.R
import com.example.cmo.data.local.pojo.AnimeQuote
import com.example.cmo.data.network.dto.AnimeQuoteApiDto
import com.example.cmo.databinding.CardQuoteDetailsBinding

class MainAdapter(
    private var items: ArrayList<AnimeQuote>,
    private val onItemClick: OnItemClick
) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardQuoteDetailsBinding) :
        RecyclerView.ViewHolder(binding.root)

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
            val ctx = binding.anime.context

            binding.anime.text = items[position].anime
            binding.quote.text = items[position].quote
            binding.character.text = "- ${items[position].character}"

            binding.bookmarkIcon.setImageDrawable(
                AppCompatResources.getDrawable(
                    ctx,
                    when (items[position].isBookmarked) {
                        true -> R.drawable.ic_bookmark_active
                        else -> R.drawable.ic_bookmark_inactive
                    }
                )
            )

            binding.bookmark.setOnClickListener {
                onItemClick.onBookmarkClick(position)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount() = items.size

    fun setItems(data: ArrayList<AnimeQuote>) {
        items = data
        notifyDataSetChanged()
    }

    fun itemAt(position: Int) = items[position]
}