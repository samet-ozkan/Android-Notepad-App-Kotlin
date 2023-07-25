package com.sametozkan.notepadapp.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteWithLabels
import com.sametozkan.notepadapp.databinding.ItemNoteBinding

class NoteListAdapter : RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {

    var noteList: List<NoteWithLabels> = ArrayList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListAdapter.ViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteListAdapter.ViewHolder, position: Int) {
        val position = holder.adapterPosition
        val note = noteList.get(position)
        holder.bindItem(note)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    class ViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(noteWithLabels: NoteWithLabels) {
            val noteEntity = noteWithLabels.note
            binding.apply {
                title.text = noteEntity.title
                //content.text = noteEntity.text
                date.text = noteEntity.timestamp.toString()
            }
        }
    }
}