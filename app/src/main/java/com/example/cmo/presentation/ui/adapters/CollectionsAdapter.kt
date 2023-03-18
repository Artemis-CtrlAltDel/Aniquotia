package com.example.cmo.presentation.ui.adapters

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cmo.R
import com.example.cmo.data.local.pojo.Quote
import com.example.cmo.databinding.CardCollectionBinding
import com.example.cmo.other.format
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CollectionsAdapter(
    private val onCollectionClick: OnCollectionClick
) : RecyclerView.Adapter<CollectionsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardCollectionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardCollectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    private val diff =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<Pair<String, List<Quote>>>() {
            override fun areItemsTheSame(
                oldItem: Pair<String, List<Quote>>,
                newItem: Pair<String, List<Quote>>
            ): Boolean =
                oldItem.first == newItem.first

            override fun areContentsTheSame(
                oldItem: Pair<String, List<Quote>>,
                newItem: Pair<String, List<Quote>>
            ): Boolean =
                oldItem == newItem
        })

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        with(holder) {
            val ctx = binding.collectionTitle.context
            val item = diff.currentList[position]

            val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.US)
            val timeFormatter = SimpleDateFormat("hh:mm a", Locale.US)

            binding.collectionTitle.text = item.first

            binding.collectionQuotesCount.text =
                ctx.resources.getQuantityString(
                    R.plurals.other_quotes,
                    item.second.size,
                    item.second.size.toLong().format()
                )

            binding.collectionCreationDate.text =
                ctx.resources.getString(
                    R.string.card_collection_date,
                    dateFormatter.format(Date(item.second[item.second.size - 1].savedAtTimestamp))
                )

            binding.collectionUpdatedDateTime.text =
                ctx.resources.getString(
                    R.string.card_collection_time,
                    dateFormatter.format(Date(item.second[0].savedAtTimestamp)),
                    timeFormatter.format(Date(item.second[0].savedAtTimestamp))
                )

            binding.cardWrapper.setOnClickListener {
                onCollectionClick.onCollectionClick(position)
            }
        }

    override fun getItemCount() = diff.currentList.size

    fun setItems(data: ArrayList<Pair<String, List<Quote>>>) = diff.submitList(data)

    fun itemAt(position: Int): Pair<String, List<Quote>> = diff.currentList[position]
}