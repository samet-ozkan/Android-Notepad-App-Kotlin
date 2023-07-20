package com.sametozkan.notepadapp.domain.usecase

import com.sametozkan.notepadapp.data.datasource.local.entities.NoteLabelXRef
import com.sametozkan.notepadapp.data.repository.NoteLabelXRefRepositoryImpl
import com.sametozkan.notepadapp.domain.repository.NoteLabelXRefRepository
import javax.inject.Inject

class UpdateNoteLabelXRefUseCase @Inject constructor(private val noteLabelXRefRepositoryImpl: NoteLabelXRefRepository) {

    suspend operator fun invoke(vararg noteLabelXRef : NoteLabelXRef){
        noteLabelXRefRepositoryImpl.update(*noteLabelXRef)
    }
}