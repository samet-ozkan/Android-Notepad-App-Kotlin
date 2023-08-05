package com.sametozkan.notepadapp.presentation.label

import androidx.lifecycle.ViewModel
import com.sametozkan.notepadapp.domain.usecase.GetLabelsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LabelSelectionViewModel @Inject constructor(private val getLabelsUseCase : GetLabelsUseCase) : ViewModel() {

    var selectedLabelList = ArrayList<Long>()
    fun fetchLabelsUseCase() = getLabelsUseCase()
}