package com.sametozkan.notepadapp.domain.usecase

import androidx.lifecycle.LiveData
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteEntity
import com.sametozkan.notepadapp.domain.repository.NoteRepository
import javax.inject.Inject

class GetNotesWithLabelsUseCase @Inject constructor(private val noteRepositoryImpl: NoteRepository) {
    operator fun invoke() : LiveData<List<NoteEntity>>{
        return noteRepositoryImpl.getAll()
    }
}