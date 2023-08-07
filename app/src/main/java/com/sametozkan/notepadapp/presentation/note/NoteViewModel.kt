package com.sametozkan.notepadapp.presentation.note

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteLabelXRef
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteWithLabels
import com.sametozkan.notepadapp.domain.usecase.AddNoteLabelXRefUseCase
import com.sametozkan.notepadapp.domain.usecase.DeleteNoteLabelXRefUseCase
import com.sametozkan.notepadapp.domain.usecase.DeleteNotesUseCase
import com.sametozkan.notepadapp.domain.usecase.GetLabelsByIdsUseCase
import com.sametozkan.notepadapp.domain.usecase.GetNoteWithLabelsByIdUseCase
import com.sametozkan.notepadapp.domain.usecase.UpdateNotesUseCase
import com.sametozkan.notepadapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val updateNotesUseCase: UpdateNotesUseCase,
    private val addNoteLabelXRefUseCase: AddNoteLabelXRefUseCase,
    private val deleteNoteLabelXRefUseCase: DeleteNoteLabelXRefUseCase,
    private val getNoteWithLabelsByIdUseCase: GetNoteWithLabelsByIdUseCase,
    private val deleteNotesUseCase: DeleteNotesUseCase
) :
    ViewModel() {

    private val TAG = "NoteViewModel"

    val changeFragment = MutableLiveData<String>()
    val noteWithLabels = MutableLiveData<NoteWithLabels>()
    var resultLabels = ArrayList<Long>()
    val message = MutableLiveData<String>()

    fun updateNote() {
        noteWithLabels.value?.let { noteWithLabels ->
            viewModelScope.launch(Dispatchers.IO) {
                updateNotesUseCase(noteWithLabels.note)
                message.postValue("Note updated successfully!")
            }
        }
    }

    fun deleteNote() {
        noteWithLabels.value?.let { noteWithLabels ->
            viewModelScope.launch(Dispatchers.IO) {
                deleteNotesUseCase(noteWithLabels.note)
                message.postValue("Note deleted successfully!")
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
