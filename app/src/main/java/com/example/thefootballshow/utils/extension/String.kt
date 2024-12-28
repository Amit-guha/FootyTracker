package com.example.thefootballshow.utils.extension

import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

fun String.toAmPmFormat(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC") // Parse in UTC
    val date = formatter.parse(this)

    val outputFormatter = SimpleDateFormat("h:mm a", Locale.getDefault()) // Use locale's format
    return outputFormatter.format(date?:"")
}

fun String.toFriendlyDate(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    val date = formatter.parse(this)?: return ""


    val currentDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    val currentDateStart = currentDate.apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time

    // Get the start of the next day (tomorrow)
    val tomorrowDateStart = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
        time = currentDateStart
        add(Calendar.DAY_OF_YEAR, 1)
    }.time

    // Calculate the difference in days
    val dateStart = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
        time = date
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time

    return when {
        dateStart == currentDateStart -> "Today"
        dateStart == tomorrowDateStart -> "Tomorrow"
        else -> {
            // Format the date to "day month year"
            val outputFormatter = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
           // val dayNameFormatter = SimpleDateFormat("EEEE", Locale.getDefault())
            outputFormatter.format(date ?: currentDateStart) // Safely handle potential null date
        }
    }
}

fun String.toLocalDateAndMonth(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    val date = formatter.parse(this) ?: return ""

    val localTimeZone = TimeZone.getDefault()
    val localFormatter = SimpleDateFormat("d MMM", Locale.getDefault())
    localFormatter.timeZone = localTimeZone

    return localFormatter.format(date)
}

fun String.toLocalTime(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    val date = formatter.parse(this) ?: return ""

    val localTimeZone = TimeZone.getDefault()
    val localTimeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    localTimeFormatter.timeZone = localTimeZone

    // Return formatted time
    return localTimeFormatter.format(date)
}