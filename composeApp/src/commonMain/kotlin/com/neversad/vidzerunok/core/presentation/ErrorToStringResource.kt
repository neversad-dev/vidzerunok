package com.neversad.vidzerunok.core.presentation

import com.neversad.vidzerunok.core.domain.VidzError
import vidzerunok.composeapp.generated.resources.Res
import vidzerunok.composeapp.generated.resources.error_unknown

fun VidzError.toUiText(): UiText{

    val stringRes = when (this) {
        VidzError.UNKNOWN -> Res.string.error_unknown
    }

    return UiText.StringResourceId(stringRes)
}