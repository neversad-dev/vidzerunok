package com.neversad.vidzerunok.editor.presentation.editor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.neversad.vidzerunok.app.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class EditorViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val filePath = savedStateHandle.toRoute<Route.Editor>().file

    private val _state = MutableStateFlow(EditorState(
        file = filePath
    ))
    val state = _state.asStateFlow()


}