package com.example.drugsearchandtracker.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.drugsearchandtracker.data.local.entity.MedicationEntity
import com.tofiq.drugsearchandtracker.R
import com.tofiq.drugsearchandtracker.databinding.ItemMedicationBinding

class MedicationsAdapter(
    private val onEditClick: (MedicationEntity) -> Unit,
    private val onDeleteClick: (MedicationEntity) -> Unit
) : ListAdapter<MedicationEntity, MedicationsAdapter.MedicationViewHolder>(MedicationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicationViewHolder {
        val binding = ItemMedicationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MedicationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MedicationViewHolder(
        private val binding: ItemMedicationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(medication: MedicationEntity) {
            binding.apply {
                medicationName.text = medication.name
                medicationDetails.text = "${medication.dosage}, ${medication.frequency}"

                menuButton.setOnClickListener { view ->
                    showPopupMenu(view, medication)
                }
            }
        }

        private fun showPopupMenu(view: android.view.View, medication: MedicationEntity) {
            PopupMenu(view.context, view).apply {
                menuInflater.inflate(R.menu.menu_medication_item, menu)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.action_edit -> {
                            onEditClick(medication)
                            true
                        }
                        R.id.action_delete -> {
                            onDeleteClick(medication)
                            true
                        }
                        else -> false
                    }
                }
                show()
            }
        }
    }

    private class MedicationDiffCallback : DiffUtil.ItemCallback<MedicationEntity>() {
        override fun areItemsTheSame(oldItem: MedicationEntity, newItem: MedicationEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MedicationEntity, newItem: MedicationEntity): Boolean {
            return oldItem == newItem
        }
    }
} 