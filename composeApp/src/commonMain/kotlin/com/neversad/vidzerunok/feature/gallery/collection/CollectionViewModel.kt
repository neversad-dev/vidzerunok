package com.neversad.vidzerunok.feature.gallery.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neversad.vidzerunok.core.common.onError
import com.neversad.vidzerunok.core.common.onSuccess
import com.neversad.vidzerunok.core.domain.ImageRepository
import com.neversad.vidzerunok.core.presentation.toUiText
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CollectionViewModel(
    val imageRepository: ImageRepository
) : ViewModel() {

    private var observeGalleryJob: Job? = null

    private val _state = MutableStateFlow(CollectionState())
    val state = _state
        .onStart {
            observeGallery()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: CollectionAction) {
        when (action) {

            is CollectionAction.OnImageDelete -> {
                viewModelScope.launch {
                    imageRepository.deleteImage(action.file)
                        .onSuccess { observeGallery() }
                        .onError { error ->
                            _state.update {
                                it.copy(
                                    errorMessage = error.toUiText()
                                )
                            }
                        }
                }
            }
            is CollectionAction.OnOpenFilePickerDialog -> {
                _state.update { it.copy(
                    isFilePickerDialogActive = true
                ) }
            }
            is CollectionAction.OnFilePickerCanceled -> {
                _state.update { it.copy(
                    isFilePickerDialogActive = false
                ) }
            }
            is CollectionAction.OnFileSelected -> {
                _state.update { it.copy(
                    isFilePickerDialogActive = false
                ) }
            }
            is CollectionAction.OnEditModeClick -> {
                _state.update { it.copy(
                    isEditMode = true
                ) }
            }
            is CollectionAction.OnEditModeDismiss -> {
                _state.update { it.copy(
                    isEditMode = false
                ) }
            }

            else -> Unit
        }
    }

    private fun observeGallery() {
        observeGalleryJob?.cancel()
        observeGalleryJob = viewModelScope.launch {
            val files = imageRepository.getAllFiles()
            _state.update {
                it.copy(
                    files = files
                )
            }
        }

    }

}

