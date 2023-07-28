package com.sametozkan.notepadapp.domain.use_case

import androidx.lifecycle.LiveData
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteWithLabels
import com.sametozkan.notepadapp.domain.repository.NoteRepository
import javax.inject.Inject

class GetNotesWithLabelsUseCase @Inject constructor(private val noteRepositoryImpl: NoteRepository) {
    operator fun invoke() : LiveData<List<NoteWithLabels>>{
        return noteRepositoryImpl.getAllWithLabels()
    }
}