package com.shulga.cftshift.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shulga.cftshift.databinding.SearchHistoryItemBinding
import com.shulga.domain.models.searchHistory.SearchHistoryModel

interface OnInteractionListener {
    fun onDelete(cardId: Int)
}

class SearchHistoryAdapter(private val onInteractionListener: OnInteractionListener) :
    ListAdapter<SearchHistoryModel, CardViewHolder>(CardDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding =
            SearchHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = getItem(position)
        holder.bind(card)
    }
}

class CardViewHolder(
    private val binding: SearchHistoryItemBinding,
    private val onInteractionListener: OnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(searchHistoryModel: SearchHistoryModel) {
        binding.apply {
            cardNumber.text = searchHistoryModel.number
            deleteButton.setOnClickListener { onInteractionListener.onDelete(searchHistoryModel.id) }
        }
    }
}

class CardDiffCallback : DiffUtil.ItemCallback<SearchHistoryModel>() {
    override fun areItemsTheSame(
        oldItem: SearchHistoryModel,
        newItem: SearchHistoryModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: SearchHistoryModel,
        newItem: SearchHistoryModel
    ): Boolean {
        return oldItem == newItem
    }
}