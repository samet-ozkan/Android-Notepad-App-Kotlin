package com.sametozkan.notepadapp.data.repository

import com.sametozkan.notepadapp.data.datasource.local.AppDatabase
import com.sametozkan.notepadapp.data.datasource.local.dao.NoteLabelXRefDao
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteLabelXRef
import com.sametozkan.notepadapp.domain.repository.NoteLabelXRefRepository
import javax.inject.Inject

class NoteLabelXRefRepositoryImpl @Inject constructor(
    private val noteLabelXRefDao: NoteLabelXRefDao
) : NoteLabelXRefRepository {

    override suspend fun insert(vararg noteLabel: NoteLabelXRef) : List<Long> {
        return noteLabelXRefDao.insert(*noteLabel)
    }

    override suspend fun update(vararg noteLabel: NoteLabelXRef) {
        noteLabelXRefDao.update(*noteLabel)
    }

    override suspend fun delete(vararg noteLabel: NoteLabelXRef) {
        noteLabelXRefDao.delete(*noteLabel)
    }
}