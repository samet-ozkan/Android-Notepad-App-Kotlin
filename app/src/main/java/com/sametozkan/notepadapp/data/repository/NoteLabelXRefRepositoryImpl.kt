package com.sametozkan.notepadapp.data.repository

import com.sametozkan.notepadapp.data.datasource.local.AppDatabase
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteLabelXRef
import com.sametozkan.notepadapp.domain.repository.NoteLabelXRefRepository

class NoteLabelXRefRepositoryImpl(private val database : AppDatabase) : NoteLabelXRefRepository {
    override suspend fun insert(noteLabel: NoteLabelXRef): Long {
        return database.NoteLabelXRefDao().insert(noteLabel)
    }

    override suspend fun insert(vararg noteLabel: NoteLabelXRef) {
        database.NoteLabelXRefDao().insert(*noteLabel)
    }

    override suspend fun update(vararg noteLabel: NoteLabelXRef) {
        database.NoteLabelXRefDao().update(*noteLabel)
    }

    override suspend fun delete(vararg noteLabel: NoteLabelXRef) {
        database.NoteLabelXRefDao().delete(*noteLabel)
    }
}