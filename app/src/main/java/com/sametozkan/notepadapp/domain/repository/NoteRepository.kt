package com.sametozkan.notepadapp.domain.repository

import androidx.lifecycle.LiveData
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteEntity
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteWithLabels

interface NoteRepository {

    fun getAll() : LiveData<List<NoteEntity>>

    fun getNoteWithLabelsById(id : Long) : LiveData<NoteWithLabels>

    fun getAllWithLabels() : LiveData<List<NoteWithLabels>>

    fun getNotesWithLabelsByKeyword(keyword : String) : LiveData<List<NoteWithLabels>>

    suspend fun insert(vararg note : NoteEntity) : List<Long>

    suspend fun update(vararg note : NoteEntity)

    suspend fun delete(vararg note : NoteEntity)

}