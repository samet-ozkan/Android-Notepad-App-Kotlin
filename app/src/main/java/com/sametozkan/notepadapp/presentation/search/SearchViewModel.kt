package com.sametozkan.notepadapp.presentation.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteEntity
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteWithLabels
import com.sametozkan.notepadapp.domain.use_case.GetNotesWithLabelsByKeywordUseCase
import com.sametozkan.notepadapp.domain.use_case.GetNotesWithLabelsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor
    (private val getNotesWithLabelsByKeywordUseCase: GetNotesWithLabelsByKeywordUseCase) : ViewModel() {

    private val TAG = "SearchViewModel"

    val keyword = MutableLiveData<String>()

    var notesByKeyword: List<NoteWithLabels> = ArrayList()

    fun fetchNotesByKeyword() = keyword.switchMap {
        Log.d(TAG, "fetchNotesByKeyword: " + it)
        getNotesWithLabelsByKeywordUseCase(it) }
}