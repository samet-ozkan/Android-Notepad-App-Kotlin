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
    private val labelItemClickListener: LabelClickListener
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
        holder.bindItem(label, labelItemClickListener)
        holder.binding.label.isChecked = selectedLabelIdList.contains(label.uid)
    }

    class ViewHolder(val binding: ItemLabelSelectionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val TAG = "LabelSelectionAdapter"

        fun bindItem(label: LabelEntity, labelItemClickListener: LabelClickListener) {
            binding.label.apply {
                text = label.name
                setOnCheckedChangeListener(null)
                setOnClickListener {
                    labelItemClickListener.onLabelClicked(label.uid, this.isChecked)
                    Log.d(TAG, "bindItem: isChecked " + this.isChecked)
                }
            }
        }
    }
}