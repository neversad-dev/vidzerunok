package com.neversad.vidzerunok.feature.gallery.file_picker

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.neversad.vidzerunok.core.common.onError
import com.neversad.vidzerunok.core.common.onSuccess
import com.neversad.vidzerunok.core.domain.ImageRepository
import com.neversad.vidzerunok.core.presentation.toUiText
import com.neversad.vidzerunok.feature.gallery.navigation.FilePickerScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FilePickerViewModel (
    private val imageRepository: ImageRepository,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    private val filePath = savedStateHandle.toRoute<FilePickerScreen>().path

    private val _state = MutableStateFlow(FilePickerState(
        filePath = filePath,
    ))
    val state = _state.asStateFlow()

    fun onAction(action: FilePickerAction) {
        when(action) {
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