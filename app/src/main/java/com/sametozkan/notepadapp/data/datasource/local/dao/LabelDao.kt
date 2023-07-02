package com.sametozkan.notepadapp.data.datasource.local.dao

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
    fun getAll() : List<LabelEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg label : LabelEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg label : LabelEntity)

    @Delete
    fun delete(vararg label : LabelEntity)
}