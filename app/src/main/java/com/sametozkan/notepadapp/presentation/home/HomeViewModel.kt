package com.sametozkan.notepadapp.presentation.home

import androidx.lifecycle.ViewModel
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteWithLabels
import com.sametozkan.notepadapp.domain.use_case.GetNotesWithLabelsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNotesWithLabelsUseCase: GetNotesWithLabelsUseCase
) : ViewModel() {

    var notesWithLabels : List<NoteWithLabels> = ArrayList()

    fun fetchNotesWithLabels() = getNotesWithLabelsUseCase()

}