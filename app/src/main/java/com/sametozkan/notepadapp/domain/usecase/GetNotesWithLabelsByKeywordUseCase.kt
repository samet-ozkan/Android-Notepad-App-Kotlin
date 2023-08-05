package com.sametozkan.notepadapp.domain.usecase

import androidx.lifecycle.LiveData
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteWithLabels
import com.sametozkan.notepadapp.domain.repository.NoteRepository
import javax.inject.Inject

class GetNotesWithLabelsByKeywordUseCase @Inject constructor(private val noteRepositoryImpl : NoteRepository) {

    operator fun invoke(keyword : String) : LiveData<List<NoteWithLabels>>{
        return noteRepositoryImpl.getNotesWithLabelsByKeyword(keyword)
    }
}