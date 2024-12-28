package com.example.thefootballshow.utils.extension

import android.util.Log

fun Any.showLog(tag: String = "AppLogger", message: String) {
    val className = this::class.simpleName ?: "UnknownClass"
    Log.d(tag, "$className: $message")
}