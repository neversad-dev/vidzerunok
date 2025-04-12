package com.neversad.vidzerunok.editor.presentation.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
): ViewModel() {

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

    private fun observeGallery(){
        observeGalleryJob?.cancel()
        observeGalleryJob = viewModelScope.launch {
            val files = imageRepository.getAllFiles()
            _state.update { it.copy(
                files = files
            ) }
        }

    }

}

