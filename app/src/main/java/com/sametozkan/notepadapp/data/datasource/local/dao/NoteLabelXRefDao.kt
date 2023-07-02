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
    fun insert(vararg noteLabel : NoteLabelXRef)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg noteLabel : NoteLabelXRef)

    @Delete
    fun delete(vararg noteLabel : NoteLabelXRef)
}