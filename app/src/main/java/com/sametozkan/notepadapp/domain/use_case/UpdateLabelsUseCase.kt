package com.sametozkan.notepadapp.domain.use_case

import com.sametozkan.notepadapp.data.datasource.local.entities.LabelEntity
import com.sametozkan.notepadapp.domain.repository.LabelRepository
import javax.inject.Inject

class UpdateLabelsUseCase @Inject constructor(private val labelRepositoryImpl : LabelRepository) {

    suspend operator fun invoke(vararg label : LabelEntity){
        labelRepositoryImpl.update(*label)
    }
}