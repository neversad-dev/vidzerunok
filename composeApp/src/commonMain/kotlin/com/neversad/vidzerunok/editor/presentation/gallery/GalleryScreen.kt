package com.neversad.vidzerunok.editor.presentation.gallery

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
fun GalleryScreen(
    viewModel: GalleryViewModel = koinViewModel(),
    onImageSelected: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    GalleryScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is GalleryAction.OnImageSelected -> {
                    onImageSelected(action.file)
                }

                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}


@Composable
fun GalleryScreen(
    state: GalleryState,
    onAction: (GalleryAction) -> Unit
) {

    var showDialog by remember { mutableStateOf<Pair<Boolean, String?>>(false to null) }

    if (state.files.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No files available",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    } else {

        LazyVerticalGrid(
            columns = GridCells.Adaptive(200.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(state.files, key = { it }) {
                val file = it.toPlatformFile()


                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    AsyncImage(
                        file,
                        contentDescription = file.name,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .matchParentSize()
                            .border(BorderStroke(1.dp, MaterialTheme.colorScheme.outline))
                            .clickable {
                                onAction(GalleryAction.OnImageSelected(file.path))
                            },
                    )

                    IconButton(
                        onClick = {
                            showDialog = true to file.path
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 12.dp, y = (-12).dp)
                            .background(
                                MaterialTheme.colorScheme.primary,
                                shape = MaterialTheme.shapes.small.copy(all = CornerSize(100.dp))
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete image",
                            tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                }
            }
        }
        if (showDialog.first) {
            AlertDialog(
                onDismissRequest = { showDialog = false to null },
                confirmButton = {
                    TextButton(onClick = {
                        onAction(GalleryAction.OnImageDelete(showDialog.second!!))
                        showDialog = false to null
                    }) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDialog = false to null
                    }) {
                        Text("Cancel")
                    }
                },
                title = { Text("Confirm Delete") },
                text = { Text("Are you sure you want to delete this image?") },
            )
        }
    }

}