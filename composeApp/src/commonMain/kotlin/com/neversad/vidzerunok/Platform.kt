package com.neversad.vidzerunok

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform