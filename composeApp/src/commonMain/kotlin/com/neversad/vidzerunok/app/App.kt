package com.neversad.vidzerunok.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.neversad.vidzerunok.navigation.VidzerunokNavGraph

@Composable
fun App() {
    MaterialTheme {
        VidzerunokNavGraph()
    }
}