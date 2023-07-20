package com.sametozkan.notepadapp.domain.repository

import androidx.lifecycle.LiveData
import com.sametozkan.notepadapp.data.datasource.local.entities.LabelEntity

interface LabelRepository {

    fun getAll() : LiveData<List<LabelEntity>>

    suspend fun insert(vararg label : LabelEntity) : List<Long>

    suspend fun update(vararg label : LabelEntity)

    suspend fun delete(vararg label : LabelEntity)

}