package com.sametozkan.notepadapp.domain.usecase

import com.sametozkan.notepadapp.data.datasource.local.entities.NoteLabelXRef
import com.sametozkan.notepadapp.domain.repository.NoteLabelXRefRepository
import javax.inject.Inject

class DeleteNoteLabelXRefUseCase @Inject constructor(private val noteLabelXRefRepositoryImpl: NoteLabelXRefRepository) {

    suspend operator fun invoke(vararg noteLabelXRef : NoteLabelXRef){
        noteLabelXRefRepositoryImpl.delete(*noteLabelXRef)
    }
}