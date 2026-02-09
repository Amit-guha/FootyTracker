package com.example.thefootballshow.utils.extension

import java.text.ParseException
import java.text.SimpleDateFormat
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
    // return localFormatter.format(date)
    val formattedDate = localFormatter.format(date)
    val dayOfMonth = formattedDate.split(" ")[0].toInt()

    // Adding ordinal suffix (1st, 2nd, 3rd, 4th, etc.)
    val suffix = when (dayOfMonth % 10) {
        1 -> if (dayOfMonth != 11) "st" else "th"
        2 -> if (dayOfMonth != 12) "nd" else "th"
        3 -> if (dayOfMonth != 13) "rd" else "th"
        else -> "th"
    }

    return "${dayOfMonth}$suffix ${formattedDate.split(" ")[1]}"

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

fun String.ageText(pattern: String = "yyyy-MM-dd"): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())

    val dobDate = try {
        sdf.parse(this)
    } catch (e: ParseException) {
        return ""
    } ?: return ""

    val dob = Calendar.getInstance().apply { time = dobDate }
    val today = Calendar.getInstance()

    var years = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
    var months = today.get(Calendar.MONTH) - dob.get(Calendar.MONTH)

    if (months < 0) {
        years--
        months += 12
    }

    return buildString {
        if (years > 0) append("$years yrs ")
        if (months > 0) append("$months mon")
        if (years == 0 && months == 0) append("0 mon")
    }.trim()
}