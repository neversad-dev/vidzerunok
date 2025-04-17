package com.neversad.vidzerunok.feature.gallery.ui.collection

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.neversad.vidzerunok.core.presentation.ui.VidzerunokTopBar
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.coil.AsyncImage
import io.github.vinceglb.filekit.dialogs.FileKitMode
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.name
import io.github.vinceglb.filekit.path
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CollectionScreen(
    viewModel: CollectionViewModel = koinViewModel(),
    onImageClicked: (String) -> Unit,
    onFileSelected: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CollectionScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is CollectionAction.OnImageClicked -> {
                    onImageClicked(action.file)
                }
                is CollectionAction.OnFileSelected -> {
                    onFileSelected(action.path)
                }

                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionScreen(
    state: CollectionState,
    onAction: (CollectionAction) -> Unit
) {
    val filePickerDialog = rememberFilePickerLauncher(
        mode = FileKitMode.Single,
        type = FileKitType.ImageAndVideo
    ) { file ->
        if(file != null) {
            onAction(CollectionAction.OnFileSelected(file.path))
        } else {
            onAction(CollectionAction.OnFilePickerCanceled)
        }
    }

    LaunchedEffect(state.isFilePickerDialogActive){
        if (state.isFilePickerDialogActive) {
            filePickerDialog.launch()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { VidzerunokTopBar(
            title = { Text(
                if (state.isEditMode) "Delete Files" else "Gallery")},
            actions = {
                if (state.isEditMode) {
                    Button(
                        onClick = {onAction(CollectionAction.OnEditModeDismiss)}
                    ) {
                        Text("Cancel")
                    }
                } else {
                    IconButton(onClick = {onAction(CollectionAction.OnEditModeClick)}) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete selected images"
                        )
                    }
                }
            }
        ) },
        floatingActionButton = {
            if(!state.isEditMode) {
                FloatingActionButton(
                    onClick = { onAction(CollectionAction.OnOpenFilePickerDialog) },
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add image"
                    )
                }
            }
        }
    ) { paddingValues ->

        if (state.files.isEmpty()) {
            CollectionEmptyContent(
                modifier = Modifier.padding(paddingValues)
            )
        } else {
            CollectionContent(
                modifier = Modifier.padding(paddingValues),
                files = state.files,
                onImageSelected = { onAction(CollectionAction.OnImageClicked(it)) },
                onImageDelete = { onAction(CollectionAction.OnImageDelete(it)) },
                editing = state.isEditMode,
            )
        }
    }

}

@Composable
fun CollectionContent(
    modifier: Modifier = Modifier,
    files: List<String>,
    onImageSelected: (String) -> Unit,
    onImageDelete: (String) -> Unit,
    editing: Boolean = false,
) {
    var showDialog by remember { mutableStateOf<Pair<Boolean, String?>>(false to null) }

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(200.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(files, key = { it }) {
            val file = PlatformFile(it)


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
                            if (editing) {
                                showDialog = true to file.path
                            } else {
                                onImageSelected(file.path)
                            }
                        },
                )

            }
        }
    }

    DeleteConfirmationDialog(
        showDialog.first,
        onDismiss = { showDialog = false to null },
        onAction = {
            onImageDelete(showDialog.second!!)
            showDialog = false to null
        })


}

@Composable
fun CollectionEmptyContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No files available",
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}