package com.example.drugsearchandtracker.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.drugsearchandtracker.data.remote.model.ConceptProperty
import com.tofiq.drugsearchandtracker.databinding.ItemSearchResultBinding

class SearchResultsAdapter(
    private val onItemClick: (ConceptProperty) -> Unit
) : ListAdapter<ConceptProperty, SearchResultsAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchResultBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemSearchResultBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }
        }

        fun bind(medication: ConceptProperty) {
            binding.apply {
                medicationNameText.text = medication.name
                medicationSynonymText.text = medication.synonym
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<ConceptProperty>() {
        override fun areItemsTheSame(oldItem: ConceptProperty, newItem: ConceptProperty): Boolean {
            return oldItem.rxcui == newItem.rxcui
        }

        override fun areContentsTheSame(oldItem: ConceptProperty, newItem: ConceptProperty): Boolean {
            return oldItem == newItem
        }
    }
} 