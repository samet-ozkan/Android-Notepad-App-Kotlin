package com.sametozkan.notepadapp.data.repository

import androidx.lifecycle.LiveData
import androidx.room.RoomDatabase
import com.sametozkan.notepadapp.data.datasource.local.AppDatabase
import com.sametozkan.notepadapp.data.datasource.local.dao.LabelDao
import com.sametozkan.notepadapp.data.datasource.local.entities.LabelEntity
import com.sametozkan.notepadapp.domain.repository.LabelRepository
import javax.inject.Inject

class LabelRepositoryImpl @Inject constructor(private val labelDao : LabelDao) : LabelRepository {
    override fun getAll(): LiveData<List<LabelEntity>> {
        return labelDao.getAll()
    }

    override fun getLabelsByIds(idList : List<Long>): LiveData<List<LabelEntity>> {
        return labelDao.getLabelsByIds(idList)
    }

    override suspend fun insert(vararg label: LabelEntity) : List<Long> {
        return labelDao.insert(*label)
    }

    override suspend fun update(vararg label: LabelEntity) {
        labelDao.update(*label)
    }

    override suspend fun delete(vararg label: LabelEntity) {
        labelDao.delete(*label)
    }
}