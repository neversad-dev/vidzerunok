package com.neversad.vidzerunok.editor.presentation.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neversad.vidzerunok.core.domain.onError
import com.neversad.vidzerunok.core.domain.onSuccess
import com.neversad.vidzerunok.core.presentation.toUiText
import com.neversad.vidzerunok.editor.domain.ImageRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GalleryViewModel(
    val imageRepository: ImageRepository
) : ViewModel() {

    private var observeGalleryJob: Job? = null

    private val _state = MutableStateFlow(GalleryState())
    val state = _state
        .onStart {
            observeGallery()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: GalleryAction) {
        when (action) {

            is GalleryAction.OnImageDelete -> {
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

