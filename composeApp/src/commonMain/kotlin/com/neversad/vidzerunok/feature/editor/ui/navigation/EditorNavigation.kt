package com.neversad.vidzerunok.feature.editor.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.neversad.vidzerunok.feature.editor.ui.EditorScreen
import kotlinx.serialization.Serializable

@Serializable
internal data class Editor(val file: String)

fun NavController.navigateToEditor(
    path: String,
    navOptions: NavOptions? = null
) = navigate(route =  Editor(path), navOptions)

fun NavGraphBuilder.editorGraph(
    navController: NavController,
) {
    composable<Editor> {
        EditorScreen(
            onBackClicked = {
                navController.popBackStack()
            }
        )
    }

}