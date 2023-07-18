package com.sametozkan.notepadapp.domain.repository

import androidx.lifecycle.LiveData
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteEntity
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteWithLabels

interface NoteRepository {

    fun getAll() : LiveData<List<NoteEntity>>

    fun getAllWithLabels() : List<NoteWithLabels>

    suspend fun insert(note : NoteEntity) : Long

    suspend fun insert(vararg note : NoteEntity)

    suspend fun update(vararg note : NoteEntity)

    suspend fun delete(vararg note : NoteEntity)

}