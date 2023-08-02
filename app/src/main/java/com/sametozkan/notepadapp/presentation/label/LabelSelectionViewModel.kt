package com.sametozkan.notepadapp.presentation.label

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sametozkan.notepadapp.data.datasource.local.entities.LabelEntity
import com.sametozkan.notepadapp.domain.use_case.GetLabelsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LabelSelectionViewModel @Inject constructor(private val getLabelsUseCase : GetLabelsUseCase) : ViewModel() {

    var selectedLabelList = ArrayList<Long>()
    fun fetchLabelsUseCase() = getLabelsUseCase()
}