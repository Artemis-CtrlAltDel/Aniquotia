package com.example.cmo.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cmo.R
import com.example.cmo.data.network.pojo.AnimeQuoteApiResponse

class MainAdapter(private var items: ArrayList<AnimeQuoteApiResponse>) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val anime: TextView = itemView.findViewById(R.id.anime)
        val quote: TextView = itemView.findViewById(R.id.quote)
        val character: TextView = itemView.findViewById(R.id.character)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.card_quote_details,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            anime.text = items[position].anime
            quote.text = items[position].quote
            character.text = "- ${items[position].character}"
        }
    }

    override fun getItemCount() = items.size

    fun setData(data: ArrayList<AnimeQuoteApiResponse>){
        items = data
        notifyDataSetChanged()
    }
}