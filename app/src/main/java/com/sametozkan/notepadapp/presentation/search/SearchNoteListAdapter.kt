package com.sametozkan.notepadapp.presentation.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteEntity
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteWithLabels
import com.sametozkan.notepadapp.databinding.ItemNoteBinding
import com.sametozkan.notepadapp.databinding.ItemNoteSearchBinding
import com.sametozkan.notepadapp.presentation.note.NoteActivity
import com.sametozkan.notepadapp.util.Constants

class SearchNoteListAdapter : RecyclerView.Adapter<SearchNoteListAdapter.ViewHolder> {

    var noteList: List<NoteWithLabels>
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    constructor(noteList: List<NoteWithLabels>) {
        this.noteList = noteList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchNoteListAdapter.ViewHolder {
        val binding = ItemNoteSearchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchNoteListAdapter.ViewHolder, position: Int) {
        val note = noteList.get(holder.adapterPosition)
        holder.bindItem(note)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    class ViewHolder(private val binding: ItemNoteSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(noteWithLabels: NoteWithLabels) {
            val noteEntity = noteWithLabels.note
            binding.apply {
                title.text = noteEntity.title
                content.text = noteEntity.text
                root.setOnClickListener {
                    val intent = Intent(it.context, NoteActivity::class.java)
                    intent.putExtra(Constants.NOTE_ENTITY_ID, noteWithLabels.note.uid)
                    it.context.startActivity(intent)
                }
                divider.setImageResource(noteEntity.color)
            }
        }
    }


}