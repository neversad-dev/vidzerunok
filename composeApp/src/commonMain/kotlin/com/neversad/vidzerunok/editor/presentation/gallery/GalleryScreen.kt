package com.neversad.vidzerunok.editor.presentation.gallery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.neversad.vidzerunok.core.data.toPlatformFile
import io.github.vinceglb.filekit.coil.AsyncImage
import io.github.vinceglb.filekit.name
import io.github.vinceglb.filekit.path
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GalleryScreen (
    viewModel: GalleryViewModel = koinViewModel(),
    onImageSelected: (String) -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    GalleryScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is GalleryAction.OnImageSelected -> {
                    onImageSelected(action.file)
                }
            }
        }
    )
}

@Composable
fun GalleryScreen(
    state: GalleryState,
    onAction: (GalleryAction) -> Unit
){

    LazyVerticalGrid(columns = GridCells.Adaptive(800.dp) ){
        items(state.files) {
            val file    = it.toPlatformFile()

            AsyncImage(
                file,
                contentDescription = file.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .sizeIn(maxWidth = 400.dp)
                    .clickable {
                        onAction(GalleryAction.OnImageSelected(file.path))
                    },
            )
        }
    }

}