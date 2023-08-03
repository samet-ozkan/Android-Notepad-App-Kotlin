package com.sametozkan.notepadapp.presentation.note.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.sametozkan.notepadapp.data.datasource.local.entities.LabelEntity
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteEntity
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteLabelXRef
import com.sametozkan.notepadapp.domain.use_case.AddLabelsUseCase
import com.sametozkan.notepadapp.domain.use_case.AddNoteLabelXRefUseCase
import com.sametozkan.notepadapp.domain.use_case.AddNotesUseCase
import com.sametozkan.notepadapp.domain.use_case.GetLabelsByIdsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val getLabelsByIdsUseCase: GetLabelsByIdsUseCase,
    private val addNotesUseCase: AddNotesUseCase,
    private val addNoteXrefLabelsUseCase: AddNoteLabelXRefUseCase,
) : ViewModel() {

    val message = MutableLiveData<String>()
    val idList = MutableLiveData<List<Long>>(ArrayList())
    var title: String? = null
    var content: String? = null
    var isFavorite = false


    fun fetchLabelsByIds() = idList.switchMap {
        getLabelsByIdsUseCase(it)
    }

    private fun checkEmpty(): Boolean {
        title?.let {
            content?.let {
                return true
            } ?: run {
                message.value = "Content is required."
                return false
            }
        }
            ?: run {
                content?.let {
                    message.value = "Title is required."
                    return false
                }
                    ?: run {
                        message.value = "Title and content are required."
                        return false
                    }
            }

    }

    fun save() {
        if (checkEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                val addNote = async {
                    addNotesUseCase(
                        NoteEntity(
                            title = title!!,
                            text = content!!,
                            timestamp = Calendar.getInstance().timeInMillis,
                            isFavorite = isFavorite
                        )
                    )
                }
                val insertedNotesIds = addNote.await()
                idList.value?.let { idList ->
                    insertedNotesIds.map { insertedNoteId ->
                        idList.map { labelId ->
                            addNoteXrefLabelsUseCase(NoteLabelXRef(insertedNoteId, labelId))
                        }
                    }

                }

            }
        }

    }

}