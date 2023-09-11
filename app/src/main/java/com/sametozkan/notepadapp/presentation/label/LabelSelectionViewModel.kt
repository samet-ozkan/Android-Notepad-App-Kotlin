package com.sametozkan.notepadapp.presentation.label

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sametozkan.notepadapp.data.datasource.local.entities.LabelEntity
import com.sametozkan.notepadapp.domain.usecase.AddLabelsUseCase
import com.sametozkan.notepadapp.domain.usecase.GetLabelsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LabelSelectionViewModel @Inject constructor(
    private val getLabelsUseCase: GetLabelsUseCase,
    private val addLabelsUseCase: AddLabelsUseCase
) : ViewModel() {

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage

    var selectedLabelList = ArrayList<Long>()
    fun fetchLabels() = getLabelsUseCase()
    fun addLabels(vararg labelEntity: LabelEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            addLabelsUseCase(*labelEntity)
        }.invokeOnCompletion { cause ->
            cause?.let {
                _toastMessage.postValue("Something went wrong!")
            } ?: run {
                _toastMessage.postValue("Label added successfully!")
            }
        }
    }
}