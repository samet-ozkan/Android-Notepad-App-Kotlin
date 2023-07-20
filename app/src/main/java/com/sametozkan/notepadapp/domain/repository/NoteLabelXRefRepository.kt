package com.sametozkan.notepadapp.domain.repository

import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteLabelXRef

interface NoteLabelXRefRepository {

    suspend fun insert(vararg noteLabel : NoteLabelXRef) : List<Long>

    suspend fun update(vararg noteLabel : NoteLabelXRef)

    suspend fun delete(vararg noteLabel : NoteLabelXRef)

}