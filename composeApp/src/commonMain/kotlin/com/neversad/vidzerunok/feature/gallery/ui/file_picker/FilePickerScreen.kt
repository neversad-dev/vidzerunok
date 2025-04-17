package com.neversad.vidzerunok.feature.gallery.ui.file_picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.neversad.vidzerunok.core.data.toPlatformFile
import io.github.vinceglb.filekit.coil.AsyncImage
import io.github.vinceglb.filekit.name
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FilePickerScreen(
    viewModel: FilePickerViewModel = koinViewModel(),
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isFileImported, state.filePath){
        if (state.isFileImported || state.filePath == null) {
            onNavigateBack()
        }
    }

    FilePickerScreen (
        state = state,
        onAction = { action ->
            when(action) {
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun FilePickerScreen (
    state: FilePickerState,
    onAction: (FilePickerAction) -> Unit
) {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else {

            if (state.errorMessage != null) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = state.errorMessage.asString(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.error
                    )
                    Button(onClick = {
                        onAction(FilePickerAction.OnErrorMessageDismiss)
                    }) {
                        Text("OK")
                    }
                }

            }

            if (state.filePath != null) {
                val file = state.filePath.toPlatformFile()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = {
                            onAction(FilePickerAction.OnClearSelection)
                        }
                    ) {
                        Text("Clear Selection")
                    }

                    Button(
                        onClick = {
                            onAction(FilePickerAction.OnSaveImageToGallery)
                        }
                    ) {
                        Text("Confirm Selection")
                    }
                }
                AsyncImage(
                    file,
                    contentDescription = file.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}