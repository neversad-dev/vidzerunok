package com.neversad.vidzerunok.app

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.neversad.vidzerunok.editor.presentation.editor.ImageEditorScreen
import com.neversad.vidzerunok.editor.presentation.file_picker.FilePickerScreen
import com.neversad.vidzerunok.editor.presentation.gallery.GalleryScreen
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSerializationApi::class)
@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        var canNavigateBack by remember { mutableStateOf(false) }
        navController.addOnDestinationChangedListener { _, _, _ ->
            canNavigateBack = navController.previousBackStackEntry != null
        }
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = { Text("Vidzerunok") },
                    navigationIcon = {

                        if (canNavigateBack) {
                            IconButton(onClick = {
                                navController.navigateUp()
                            }) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    }
                )
            },
            floatingActionButton = {
                if (currentRoute == Route.Gallery.serializer().descriptor.serialName) {
                    FloatingActionButton(
                        onClick = { navController.navigate(Route.FilePicker) }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add File"
                        )
                    }
                }
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Route.MainGraph,
                modifier = Modifier.padding(paddingValues)
            ) {
                navigation<Route.MainGraph>(
                    startDestination = Route.Gallery
                ) {
                    composable<Route.Gallery> {
                        GalleryScreen(
                            viewModel = koinViewModel(),
                            onImageSelected = { file ->
                                navController.navigate(Route.Editor(file))
                            }
                        )
                    }
                    composable<Route.FilePicker>() {
                        FilePickerScreen(
                            viewModel = koinViewModel(),
                            onFileSelected = {
                                navController.popBackStack()
                            }
                        )
                    }
                    composable<Route.Editor> {
                        ImageEditorScreen()
                    }
                }
            }
        }
    }
}