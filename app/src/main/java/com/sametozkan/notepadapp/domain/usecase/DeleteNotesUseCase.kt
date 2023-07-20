package com.sametozkan.notepadapp.domain.usecase

import com.sametozkan.notepadapp.data.datasource.local.entities.NoteEntity
import com.sametozkan.notepadapp.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNotesUseCase @Inject constructor(private val noteRepositoryImpl: NoteRepository) {

    suspend operator fun invoke(vararg note : NoteEntity){
        noteRepositoryImpl.delete(*note)
    }
}