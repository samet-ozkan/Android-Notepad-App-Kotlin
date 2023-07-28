package com.sametozkan.notepadapp.domain.use_case

import com.sametozkan.notepadapp.data.datasource.local.entities.NoteEntity
import com.sametozkan.notepadapp.domain.repository.NoteRepository
import javax.inject.Inject

class UpdateNotesUseCase @Inject constructor(private val noteRepositoryImpl: NoteRepository) {

    suspend operator fun invoke(vararg note : NoteEntity){
        noteRepositoryImpl.update(*note)
    }
}