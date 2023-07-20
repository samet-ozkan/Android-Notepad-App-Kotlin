package com.sametozkan.notepadapp.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteLabelXRef

@Dao
interface NoteLabelXRefDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg noteLabel : NoteLabelXRef) : List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg noteLabel : NoteLabelXRef)

    @Delete
    suspend fun delete(vararg noteLabel : NoteLabelXRef)
}