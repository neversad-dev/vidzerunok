package com.neversad.vidzerunok.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.neversad.vidzerunok.feature.editor.ui.navigation.editorGraph
import com.neversad.vidzerunok.feature.editor.ui.navigation.navigateToEditor
import com.neversad.vidzerunok.feature.gallery.ui.navigation.Gallery
import com.neversad.vidzerunok.feature.gallery.ui.navigation.galleryGraph

@Composable
fun VidzerunokNavGraph(
    modifier: Modifier = Modifier,
    startDestination: Any = Gallery,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        galleryGraph(
            navController = navController,
            onImageClick = {
                navController.navigateToEditor(it)
            }
        )
        editorGraph(navController = navController)

    }
}