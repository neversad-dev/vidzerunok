package com.neversad.vidzerunok.feature.editor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.neversad.vidzerunok.core.data.toPlatformFile
import com.neversad.vidzerunok.core.presentation.components.VidzerunokTopBar
import io.github.vinceglb.filekit.coil.AsyncImage
import io.github.vinceglb.filekit.name
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageEditorScreen(
    viewModel: EditorViewModel = koinViewModel(),
    onBackClicked: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold (
        topBar = {
            VidzerunokTopBar(
                title = { Text("Edit image") },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackClicked()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ){ paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
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

}
