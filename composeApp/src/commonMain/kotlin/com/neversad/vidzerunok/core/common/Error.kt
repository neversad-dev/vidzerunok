package com.neversad.vidzerunok.core.common

sealed interface Error {

    enum class Local: Error {
        UNKNOWN
    }
}