package com.sametozkan.notepadapp.presentation.home

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sametozkan.notepadapp.R
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteWithLabels
import com.sametozkan.notepadapp.databinding.ItemNoteBinding
import com.sametozkan.notepadapp.presentation.note.NoteActivity
import com.sametozkan.notepadapp.util.Constants
import com.sametozkan.notepadapp.util.Utils
import java.io.Serializable

class NoteListAdapter : RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    var noteList: List<NoteWithLabels>
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    constructor(noteList: List<NoteWithLabels>) {
        this.noteList = noteList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val position = holder.adapterPosition
        val note = noteList.get(position)
        holder.bindItem(note)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    class ViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {

        private val TAG = "ViewHolder"
        fun bindItem(noteWithLabels: NoteWithLabels) {
            val noteEntity = noteWithLabels.note

            binding.apply {
                title.text = noteEntity.title
                date.text = Utils.convertTimestampToString(noteEntity.timestamp)
                root.setOnClickListener {
                    val intent = Intent(it.context, NoteActivity::class.java)
                    intent.putExtra(Constants.NOTE_ENTITY_ID, noteEntity.uid)
                    it.context.startActivity(intent)
                }
                Log.d(TAG, "bindItem: isFavorite " + noteEntity.isFavorite)
                if (noteEntity.isFavorite) {
                    favorite.setImageResource(R.drawable.baseline_favorite_24)
                } else {
                    favorite.setImageResource(R.drawable.baseline_favorite_border_24)
                }
                title.setBackgroundResource(noteEntity.color)
            }

        }
    }
}