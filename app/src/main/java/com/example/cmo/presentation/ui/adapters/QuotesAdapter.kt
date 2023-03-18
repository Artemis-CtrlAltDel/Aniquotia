package com.example.cmo.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cmo.R
import com.example.cmo.data.local.pojo.Quote
import com.example.cmo.databinding.CardQuoteBinding

class QuotesAdapter(
    private val onQuoteClick: OnQuoteClick
) :
    RecyclerView.Adapter<QuotesAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardQuoteBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diff = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Quote>() {
        override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean =
            oldItem == newItem
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            CardQuoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        with(holder) {
            val ctx = binding.anime.context
            val item = diff.currentList[position]

            binding.anime.text = item.anime
            binding.quote.text = item.quote
            binding.character.text =
                ctx.resources.getString(R.string.other_quote_credits, item.character)

            binding.bookmarkIcon.setImageDrawable(
                AppCompatResources.getDrawable(
                    ctx,
                    when (item.isBookmarked) {
                        true -> R.drawable.ic_bookmark_active
                        else -> R.drawable.ic_bookmark_inactive
                    }
                )
            )

            binding.bookmark.setOnClickListener {
                onQuoteClick.onBookmarkClick(position)
                notifyItemChanged(position)
            }
        }

    override fun getItemCount() = diff.currentList.size

    fun setItems(data: ArrayList<Quote>) = diff.submitList(data)

    fun itemAt(position: Int): Quote = diff.currentList[position]
}