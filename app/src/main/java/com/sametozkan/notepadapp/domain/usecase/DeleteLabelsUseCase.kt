package com.sametozkan.notepadapp.domain.usecase

import com.sametozkan.notepadapp.data.datasource.local.entities.LabelEntity
import com.sametozkan.notepadapp.domain.repository.LabelRepository
import javax.inject.Inject

class DeleteLabelsUseCase @Inject constructor(private val labelRepositoryImpl: LabelRepository) {

    suspend operator fun invoke(vararg label : LabelEntity){
        labelRepositoryImpl.delete(*label)
    }
}