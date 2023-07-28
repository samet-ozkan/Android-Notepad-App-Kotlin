package com.sametozkan.notepadapp.presentation.note

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteEntity
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteWithLabels
import com.sametozkan.notepadapp.domain.use_case.UpdateNotesUseCase
import com.sametozkan.notepadapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val updateNotesUseCase: UpdateNotesUseCase) :
    ViewModel() {

    val changeFragment = MutableLiveData<String>()
    val noteWithLabels = MutableLiveData<NoteWithLabels>()

    fun updateNote(title: String, content: String) {
        noteWithLabels.value?.let { value ->
            val noteEntity = value.note.copy(title = title, text = content)
            val job = viewModelScope.launch(Dispatchers.IO) {
                updateNotesUseCase(noteEntity)
            }
            job.invokeOnCompletion { cause: Throwable? ->
                if (cause == null){
                    noteWithLabels.postValue(value.copy(note = noteEntity))
                    changeFragment.postValue(Constants.NOTE_DETAIL)
                }
            }
        }
    }

}
