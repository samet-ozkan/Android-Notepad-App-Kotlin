package com.sametozkan.notepadapp.domain.usecase

import androidx.lifecycle.LiveData
import com.sametozkan.notepadapp.data.datasource.local.entities.LabelEntity
import com.sametozkan.notepadapp.data.repository.LabelRepositoryImpl
import com.sametozkan.notepadapp.domain.repository.LabelRepository
import javax.inject.Inject

class GetLabelsUseCase @Inject constructor(private val labelRepositoryImpl: LabelRepository) {

    operator fun invoke() : LiveData<List<LabelEntity>>{
        return labelRepositoryImpl.getAll()
    }
}