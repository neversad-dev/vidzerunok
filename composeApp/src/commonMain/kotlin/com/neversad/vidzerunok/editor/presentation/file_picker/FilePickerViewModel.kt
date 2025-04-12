package com.neversad.vidzerunok.editor.presentation.file_picker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neversad.vidzerunok.core.domain.onError
import com.neversad.vidzerunok.core.domain.onSuccess
import com.neversad.vidzerunok.core.presentation.toUiText
import com.neversad.vidzerunok.editor.domain.ImageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FilePickerViewModel (
    private val imageRepository: ImageRepository
): ViewModel(){

    private val _state = MutableStateFlow(FilePickerState())
    val state = _state.asStateFlow()

    fun onAction(action: FilePickerAction) {
        when(action) {
            FilePickerAction.OnOpenFilePickerDialog -> {
               _state.update {
                   it.copy(isFilePickerDialogActive = true)
               }
            }
            FilePickerAction.OnFilePickerCanceled -> {
                _state.update { it.copy(
                        isFilePickerDialogActive = false
                    ) }
            }
            is FilePickerAction.OnFileSelected -> {
                _state.update {
                    it.copy(
                        isFilePickerDialogActive = false,
                        filePath = action.path
                    )
                }
            }
            is FilePickerAction.OnClearSelection -> {
                _state.update {
                    it.copy(
                        filePath = null
                    )
                }
            }
            is FilePickerAction.OnSaveImageToGallery -> {
                _state.update { it.copy(
                    isLoading = true
                ) }
                _state.value.filePath?.let { path ->
                    viewModelScope.launch {
                        imageRepository.saveFile(path)
                            .onSuccess {
                                _state.update { it.copy(
                                    isLoading = false,
                                    filePath = null,
                                    isFileImported = true
                                ) }
                            }
                            .onError { error ->
                                _state.update { it.copy(
                                    isLoading = false,
                                    errorMessage = error.toUiText()
                                ) }
                            }
                    }
                }
            }
            FilePickerAction.OnErrorMessageDismiss -> {
                _state.update { it.copy(
                    errorMessage = null
                ) }
            }
        }
    }
}