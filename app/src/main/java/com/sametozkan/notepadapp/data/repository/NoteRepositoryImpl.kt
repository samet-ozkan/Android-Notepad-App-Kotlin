package com.sametozkan.notepadapp.data.repository

import androidx.lifecycle.LiveData
import com.sametozkan.notepadapp.data.datasource.local.AppDatabase
import com.sametozkan.notepadapp.data.datasource.local.dao.NoteDao
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteEntity
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteWithLabels
import com.sametozkan.notepadapp.domain.repository.NoteRepository
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val noteDao: NoteDao) : NoteRepository {
    override fun getAll(): LiveData<List<NoteEntity>> {
        return noteDao.getAll()
    }

    override fun getNoteWithLabelsById(id: Long): LiveData<NoteWithLabels> {
        return noteDao.getNoteWithLabelsById(id)
    }

    override fun getAllWithLabels(): LiveData<List<NoteWithLabels>> {
        return noteDao.getAllWithLabels()
    }

    override fun getNotesWithLabelsByKeyword(keyword: String): LiveData<List<NoteWithLabels>> {
        return noteDao.getNotesWithLabelsByKeyword(keyword)
    }

    override suspend fun insert(vararg note: NoteEntity) : List<Long> {
        return noteDao.insert(*note)
    }

    override suspend fun update(vararg note: NoteEntity) {
        noteDao.update(*note)
    }

    override suspend fun delete(vararg note: NoteEntity) {
        noteDao.delete(*note)
    }
}