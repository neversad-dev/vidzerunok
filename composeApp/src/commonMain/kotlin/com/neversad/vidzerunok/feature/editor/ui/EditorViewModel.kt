package com.neversad.vidzerunok.feature.editor.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.neversad.vidzerunok.feature.editor.ui.navigation.Editor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class EditorViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val filePath = savedStateHandle.toRoute<Editor>().file

    private val _state = MutableStateFlow(
        EditorState(
            file = filePath
        )
    )
    val state = _state.asStateFlow()


    fun onAction(action: EditorAction) {
        when (action) {
            else -> Unit
        }
    }








}