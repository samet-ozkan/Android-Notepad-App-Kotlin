package com.sametozkan.notepadapp.data.datasource.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sametozkan.notepadapp.data.datasource.local.entities.LabelEntity

@Dao
interface LabelDao {

    @Query("SELECT * FROM Label ORDER BY label_name ASC")
    fun getAll() : LiveData<List<LabelEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(label : LabelEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg label : LabelEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg label : LabelEntity)

    @Delete
    suspend fun delete(vararg label : LabelEntity)
}