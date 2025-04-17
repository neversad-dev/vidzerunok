package com.neversad.vidzerunok.feature.gallery.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object GetTimestampUseCase {

    operator fun invoke(): String{
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

        val datePart = now.year.toString().padStart(4, '0') +
                now.monthNumber.toString().padStart(2, '0') +
                now.dayOfMonth.toString().padStart(2, '0')

        val timePart = now.hour.toString().padStart(2, '0') +
                now.minute.toString().padStart(2, '0') +
                now.second.toString().padStart(2, '0')

        return "${datePart}_${timePart}"
    }
}