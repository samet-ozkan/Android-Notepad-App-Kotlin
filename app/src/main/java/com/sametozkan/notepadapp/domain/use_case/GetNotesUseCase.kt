package com.sametozkan.notepadapp.domain.use_case

import androidx.lifecycle.LiveData
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteEntity
import com.sametozkan.notepadapp.domain.repository.NoteRepository
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(private val noteRepositoryImpl: NoteRepository) {
    operator fun invoke() : LiveData<List<NoteEntity>>{
        return noteRepositoryImpl.getAll()
    }
}