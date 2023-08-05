package com.sametozkan.notepadapp.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteWithLabels
import com.sametozkan.notepadapp.domain.usecase.GetLabelsByIdsUseCase
import com.sametozkan.notepadapp.domain.usecase.GetLabelsUseCase
import com.sametozkan.notepadapp.domain.usecase.GetNotesWithLabelsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNotesWithLabelsUseCase: GetNotesWithLabelsUseCase,
    private val getLabelsUseCase: GetLabelsUseCase,
    private val getLabelsByIdsUseCase: GetLabelsByIdsUseCase
) : ViewModel() {

    var notesWithLabels: List<NoteWithLabels> = ArrayList()
    val selectedIdList = MutableLiveData<List<Long>>(ArrayList())

    fun fetchNotesWithLabels() = getNotesWithLabelsUseCase()

    fun fetchLabels() = getLabelsUseCase()

    fun getSelectedLabels() = selectedIdList.switchMap { getLabelsByIdsUseCase(it) }

}