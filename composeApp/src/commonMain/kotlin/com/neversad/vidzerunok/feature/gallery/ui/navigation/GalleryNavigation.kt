package com.neversad.vidzerunok.feature.gallery.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.neversad.vidzerunok.feature.gallery.ui.collection.CollectionScreen
import com.neversad.vidzerunok.feature.gallery.ui.file_picker.FilePickerScreen
import kotlinx.serialization.Serializable

@Serializable
data object Gallery

@Serializable
internal data object CollectionScreen

@Serializable
internal data class FilePickerScreen(val path: String)

fun NavGraphBuilder.galleryGraph(
    navController: NavController,
    onImageClick: (String) -> Unit
) {
    navigation<Gallery>(
        startDestination = CollectionScreen
    ) {
        composable<CollectionScreen> {
            CollectionScreen(
                onImageClicked = onImageClick,
                onFileSelected = {
                    navController.navigate(FilePickerScreen(it))
                }
            )
        }

        composable<FilePickerScreen> {
            FilePickerScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
