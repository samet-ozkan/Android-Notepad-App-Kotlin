package com.sametozkan.notepadapp.data.repository

import androidx.lifecycle.LiveData
import com.sametozkan.notepadapp.data.datasource.local.AppDatabase
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteEntity
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteWithLabels
import com.sametozkan.notepadapp.domain.repository.NoteRepository

class NoteRepositoryImpl(private val database : AppDatabase) : NoteRepository {
    override fun getAll(): LiveData<List<NoteEntity>> {
        return database.NoteDao().getAll()
    }

    override fun getAllWithLabels(): List<NoteWithLabels> {
        return database.NoteDao().getAllWithLabels()
    }

    override suspend fun insert(note: NoteEntity): Long {
        return database.NoteDao().insert(note)
    }

    override suspend fun insert(vararg note: NoteEntity) {
        database.NoteDao().insert(*note)
    }

    override suspend fun update(vararg note: NoteEntity) {
        database.NoteDao().update(*note)
    }

    override suspend fun delete(vararg note: NoteEntity) {
        database.NoteDao().delete(*note)
    }
}