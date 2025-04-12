package com.neversad.vidzerunok.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.neversad.vidzerunok.editor.presentation.editor.ImageEditorScreen
import com.neversad.vidzerunok.editor.presentation.file_picker.FilePickerScreen
import com.neversad.vidzerunok.editor.presentation.gallery.GalleryScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Route.EditorGraph
        ){
            navigation<Route.EditorGraph>(
                startDestination = Route.Gallery
            ) {
                composable<Route.Gallery> {
                    GalleryScreen (
                        viewModel = koinViewModel(),
                        onRequestAddFile = {
                            navController.navigate(Route.FilePicker)
                        },
                        onImageSelected = { file ->
                            navController.navigate(Route.Editor(file))
                        }
                    )
                }
                composable<Route.FilePicker>(){
                    FilePickerScreen(
                        viewModel = koinViewModel(),
                        onFileSelected = {
                            navController.popBackStack()
                        }
                    )
                }
                composable<Route.Editor> {
                    ImageEditorScreen(
                        onNavigateBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }

    }
}