package com.emm.betsy

import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import java.util.UUID

fun Int.randomUUIDWithId() = "$this -> ${UUID.randomUUID()}"

fun Modifier.log(color: Color = Color.Red) = then(Modifier.border(width = 1.dp, color = color))

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
    return "hace ${getFormat(seconds)}"
}

private fun getFormat(seconds: Long) = when {
    seconds < 60 -> "justo ahora"
    seconds < 120 -> "1 minuto"
    seconds < 3600 -> "${seconds / 60} minutos"
    seconds < 7200 -> "1 hora"
    seconds < 86400 -> "${seconds / 3600} horas"
    seconds < 172800 -> "1 dia"
    else -> "${seconds / 86400} dias"
}

fun currentTime(): Long = Instant.now().toEpochMilli()
