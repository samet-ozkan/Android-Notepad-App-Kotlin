package com.sametozkan.notepadapp.data.datasource.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Label", indices = [Index(value = arrayOf("label_id"))])
data class LabelEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "label_id")
    val uid: Long,
    @ColumnInfo(name = "label_name") val name: String
)