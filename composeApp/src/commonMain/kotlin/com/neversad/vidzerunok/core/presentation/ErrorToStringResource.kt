package com.neversad.vidzerunok.core.presentation

import com.neversad.vidzerunok.core.common.Error
import vidzerunok.composeapp.generated.resources.Res
import vidzerunok.composeapp.generated.resources.error_unknown

fun Error.toUiText(): UiText{

    val stringRes = when (this) {
        Error.Local.UNKNOWN -> Res.string.error_unknown
    }

    return UiText.StringResourceId(stringRes)
}