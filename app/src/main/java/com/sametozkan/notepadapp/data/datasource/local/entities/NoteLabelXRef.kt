package com.sametozkan.notepadapp.data.datasource.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "NoteLabel",
    primaryKeys = ["noteId", "labelId"],
    foreignKeys = [
        ForeignKey(
            entity = NoteEntity::class,
            parentColumns = arrayOf("note_id"),
            childColumns = arrayOf("noteId"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LabelEntity::class,
            parentColumns = arrayOf("label_id"),
            childColumns = arrayOf("labelId"), onDelete = ForeignKey.CASCADE
        )],
    indices = [Index(value = arrayOf("noteId")), Index(value = arrayOf("labelId"))]
)
class NoteLabelXRef(
    @ColumnInfo(name = "noteId") val note_id: Long,
    @ColumnInfo(name = "labelId") val label_id: Long
)