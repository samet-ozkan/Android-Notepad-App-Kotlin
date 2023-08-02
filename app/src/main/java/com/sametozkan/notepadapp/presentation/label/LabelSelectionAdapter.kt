package com.sametozkan.notepadapp.presentation.label

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sametozkan.notepadapp.data.datasource.local.entities.LabelEntity
import com.sametozkan.notepadapp.databinding.ItemLabelSelectionBinding

class LabelSelectionAdapter(
    labelList: List<LabelEntity>,
    selectedLabelIdList: List<Long>,
    private val labelItemClickListener: LabelItemClickListener
) :
    RecyclerView.Adapter<LabelSelectionAdapter.ViewHolder>() {

    private val TAG = "LabelSelectionAdapter"

    var labelList: List<LabelEntity> = labelList
        set(value) {
            field = value
            Log.d(TAG, "labelList: " + value)
            notifyDataSetChanged()
        }

    var selectedLabelIdList: List<Long> = selectedLabelIdList
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ItemLabelSelectionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return labelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var label = labelList.get(holder.adapterPosition)
        if (selectedLabelIdList.contains(label.uid)) {
            holder.binding.label.isChecked = true
        }
        holder.bindItem(label, labelItemClickListener)
    }

    class ViewHolder(val binding: ItemLabelSelectionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val TAG = "LabelSelectionAdapter"

        fun bindItem(label: LabelEntity, labelItemClickListener: LabelItemClickListener) {
            binding.label.apply {
                text = label.name
                setOnClickListener {
                    labelItemClickListener.onClick(label.uid, this.isChecked)
                    Log.d(TAG, "bindItem: isChecked " + this.isChecked)
                }
            }
        }
    }
}