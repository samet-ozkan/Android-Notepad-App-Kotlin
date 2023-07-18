package com.sametozkan.notepadapp.data.repository

import androidx.lifecycle.LiveData
import androidx.room.RoomDatabase
import com.sametozkan.notepadapp.data.datasource.local.AppDatabase
import com.sametozkan.notepadapp.data.datasource.local.entities.LabelEntity
import com.sametozkan.notepadapp.domain.repository.LabelRepository

class LabelRepositoryImpl(private val database : AppDatabase) : LabelRepository {
    override fun getAll(): LiveData<List<LabelEntity>> {
        return database.LabelDao().getAll()
    }

    override suspend fun insert(label: LabelEntity) : Long {
        return database.LabelDao().insert(label)
    }

    override suspend fun insert(vararg label: LabelEntity) {
        database.LabelDao().insert(*label)
    }

    override suspend fun update(vararg label: LabelEntity) {
        database.LabelDao().update(*label)
    }

    override suspend fun delete(vararg label: LabelEntity) {
        database.LabelDao().delete(*label)
    }
}