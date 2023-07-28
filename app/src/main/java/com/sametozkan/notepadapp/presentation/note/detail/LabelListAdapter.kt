package com.sametozkan.notepadapp.presentation.note.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sametozkan.notepadapp.data.datasource.local.entities.LabelEntity
import com.sametozkan.notepadapp.databinding.ItemLabelBinding

class LabelListAdapter : RecyclerView.Adapter<LabelListAdapter.ViewHolder> {

    var labelList: List<LabelEntity>
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    constructor(labelList: List<LabelEntity>) {
        this.labelList = labelList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLabelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return labelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val label = labelList.get(holder.adapterPosition)
        holder.bindItem(label)
    }

    class ViewHolder(private val binding: ItemLabelBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(label: LabelEntity) {
            binding.apply {
                this.label.text = label.name
            }
        }
    }
}