package com.sametozkan.notepadapp.data.datasource.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Note", indices = [Index(value = arrayOf("note_id"))])
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    val uid: Long,
    @ColumnInfo(name = "note_title") val title: String,
    @ColumnInfo(name = "note_content") val text: String,
    @ColumnInfo(name = "note_timestamp") val timestamp: Long,
    @ColumnInfo(name = "note_isFavorite") val isFavorite: Boolean,
)