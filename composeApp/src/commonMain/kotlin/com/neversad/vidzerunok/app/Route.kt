package com.neversad.vidzerunok.app

import kotlinx.serialization.Serializable


sealed interface Route {

    @Serializable
    data object MainGraph: Route

    @Serializable
    data object FilePicker: Route

    @Serializable
    data object Gallery: Route

    @Serializable
    data class Editor(val file: String): Route

}