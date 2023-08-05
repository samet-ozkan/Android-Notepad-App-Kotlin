package com.sametozkan.notepadapp.domain.usecase

import androidx.lifecycle.LiveData
import com.sametozkan.notepadapp.data.datasource.local.entities.LabelEntity
import com.sametozkan.notepadapp.domain.repository.LabelRepository
import javax.inject.Inject

class GetLabelsByIdsUseCase @Inject constructor(private val labelRepositoryImpl: LabelRepository) {
    operator fun invoke(idList: List<Long>): LiveData<List<LabelEntity>> {
        return labelRepositoryImpl.getLabelsByIds(idList)
    }
}