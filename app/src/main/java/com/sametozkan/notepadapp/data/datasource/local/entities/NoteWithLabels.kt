package com.sametozkan.notepadapp.data.datasource.local.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import java.io.Serializable

data class NoteWithLabels(

    @Embedded
    val note: NoteEntity,

    @Relation(
        parentColumn = "note_id",
        entity = LabelEntity::class,
        entityColumn = "label_id",
        associateBy = Junction(
            value = NoteLabelXRef::class,
            parentColumn = "noteId",
            entityColumn = "labelId"
        )
    )

    val labels: List<LabelEntity>
) : Serializable