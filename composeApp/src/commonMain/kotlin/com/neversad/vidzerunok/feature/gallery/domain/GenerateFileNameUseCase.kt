package com.neversad.vidzerunok.feature.gallery.domain


private const val PREFIX = "vidz"

class GenerateFileNameUseCase(
    private val getTimeStampUseCase: GetTimestampUseCase
) {

    operator fun invoke(randomSuffixLength: Int  = 6):String {
        val timestamp = getTimeStampUseCase()
        val randomSuffix = (1..randomSuffixLength)
            .map { ('a'..'z').random() }
            .joinToString("")
        return "${PREFIX}_${timestamp}_${randomSuffix}"
    }
}