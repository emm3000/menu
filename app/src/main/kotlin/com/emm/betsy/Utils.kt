package com.emm.betsy

import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import java.util.UUID

fun Int.randomUUIDWithId() = "$this -> ${UUID.randomUUID()}"

fun Long.dateToLegibleDate(): String {
    val localDateTime = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    )
    return localDateTime
        .format(
            DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
                .withLocale(Locale("es"))
        )
}

fun Long.formatDuration(): String {
    val duration = Duration.between(
        LocalDateTime.ofInstant(
            Instant.ofEpochMilli(this),
            ZoneId.systemDefault()
        ),
        LocalDateTime.now()
    )
    val seconds = duration.seconds
    return when {
        seconds < 60 -> "just now"
        seconds < 120 -> "1 minute"
        seconds < 3600 -> "${seconds / 60} minutes"
        seconds < 7200 -> "1 hour"
        seconds < 86400 -> "${seconds / 3600} hours"
        seconds < 172800 -> "1 day"
        else -> "${seconds / 86400} days"
    }
}