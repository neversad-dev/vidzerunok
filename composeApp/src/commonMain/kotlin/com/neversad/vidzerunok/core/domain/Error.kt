package com.neversad.vidzerunok.core.domain

sealed interface Error {

    enum class Local: Error {
        UNKNOWN
    }
}