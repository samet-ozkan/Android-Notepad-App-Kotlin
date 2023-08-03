package com.sametozkan.notepadapp.presentation.note

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.sametozkan.notepadapp.data.datasource.local.entities.LabelEntity
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteEntity
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteLabelXRef
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteWithLabels
import com.sametozkan.notepadapp.domain.use_case.AddNoteLabelXRefUseCase
import com.sametozkan.notepadapp.domain.use_case.DeleteNoteLabelXRefUseCase
import com.sametozkan.notepadapp.domain.use_case.GetLabelsByIdsUseCase
import com.sametozkan.notepadapp.domain.use_case.GetNoteWithLabelsByIdUseCase
import com.sametozkan.notepadapp.domain.use_case.GetNotesWithLabelsUseCase
import com.sametozkan.notepadapp.domain.use_case.UpdateNotesUseCase
import com.sametozkan.notepadapp.presentation.note.detail.NoteDetailFragment
import com.sametozkan.notepadapp.presentation.note.edit.NoteEditFragment
import com.sametozkan.notepadapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val updateNotesUseCase: UpdateNotesUseCase,
    private val addNoteLabelXRefUseCase: AddNoteLabelXRefUseCase,
    private val deleteNoteLabelXRefUseCase: DeleteNoteLabelXRefUseCase,
    private val getLabelsByIdsUseCase: GetLabelsByIdsUseCase,
    private val getNoteWithLabelsByIdUseCase: GetNoteWithLabelsByIdUseCase
) :
    ViewModel() {

    private val TAG = "NoteViewModel"

    val changeFragment = MutableLiveData<String>()
    val noteWithLabels = MutableLiveData<NoteWithLabels>()
    var resultLabels = ArrayList<Long>()
    fun updateNote() {
        noteWithLabels.value?.let { noteWithLabels ->
            viewModelScope.launch(Dispatchers.IO) {
                Log.d(TAG, "updateNote: " + noteWithLabels.note.isFavorite)
                updateNotesUseCase(noteWithLabels.note)
                changeFragment.postValue(Constants.NOTE_DETAIL)
            }
        }
    }

    fun fetchNoteWithLabelsById(id: Long) = getNoteWithLabelsByIdUseCase(id)


    private suspend fun deleteLabels() {
        noteWithLabels.value?.labels?.forEach { labelEntity ->
            deleteNoteLabelXRefUseCase(
                NoteLabelXRef(
                    noteWithLabels.value!!.note.uid,
                    labelEntity.uid
                )
            )
        }
    }

    private suspend fun addLabels() {
        if (resultLabels.isNotEmpty()) {
            resultLabels.forEach { labelId ->
                addNoteLabelXRefUseCase(
                    NoteLabelXRef(
                        noteWithLabels.value!!.note.uid,
                        labelId
                    )
                )
            }
        }
    }

    fun updateLabels() {
        val noteWithLabelsValue = noteWithLabels.value
        if (noteWithLabelsValue != null) {
            viewModelScope.launch(Dispatchers.IO) {
                if (!noteWithLabelsValue.labels.isNullOrEmpty()) {
                    deleteLabels()
                }
                addLabels()
            }
        }
    }

}
