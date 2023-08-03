package com.sametozkan.notepadapp.data.datasource.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteEntity
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteWithLabels

@Dao
interface NoteDao {

    @Query("SELECT * FROM Note ORDER BY note_timestamp DESC")
    fun getAll(): LiveData<List<NoteEntity>>

    @Transaction
    @Query("SELECT * FROM Note ORDER BY note_timestamp DESC")
    fun getAllWithLabels(): LiveData<List<NoteWithLabels>>

    @Transaction
    @Query("SELECT * FROM Note WHERE note_id = :id")
    fun getNoteWithLabelsById(id: Long): LiveData<NoteWithLabels>

    @Transaction
    @Query("SELECT * FROM Note WHERE note_title LIKE '%' || :keyword || '%' OR note_content LIKE '%' || :keyword || '%' ")
    fun getNotesWithLabelsByKeyword(keyword: String): LiveData<List<NoteWithLabels>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg note: NoteEntity): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg note: NoteEntity)

    @Delete
    fun delete(vararg note: NoteEntity)
}