package com.neversad.vidzerunok.editor.presentation.editor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.neversad.vidzerunok.core.data.toPlatformFile
import io.github.vinceglb.filekit.coil.AsyncImage
import io.github.vinceglb.filekit.name
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ImageEditorScreen(
    viewModel: EditorViewModel = koinViewModel(),
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val file = state.file.toPlatformFile()

        AsyncImage(
            file,
            contentDescription = file.name,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .sizeIn(maxWidth = 400.dp)

        )
    }

}
