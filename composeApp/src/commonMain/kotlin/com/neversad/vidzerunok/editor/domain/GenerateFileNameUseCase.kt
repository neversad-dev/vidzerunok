package com.neversad.vidzerunok.editor.domain

private const val PREFIX = "vidzerunok"

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