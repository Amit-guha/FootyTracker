package com.example.thefootballshow.utils.extension

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Long.toTodayAndTomorrow(pattern: String = "yyyy-MM-dd"): Pair<String, String> {
    val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    val calendar = Calendar.getInstance().apply { timeInMillis = this@toTodayAndTomorrow }

    val today = dateFormat.format(calendar.time)
    calendar.add(Calendar.DAY_OF_YEAR, 1) // Move to tomorrow
    val tomorrow = dateFormat.format(calendar.time)

    return Pair(today, tomorrow)
}
