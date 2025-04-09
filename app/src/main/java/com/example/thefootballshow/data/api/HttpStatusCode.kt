package com.example.thefootballshow.data.api

object HttpStatusCode {
    const val BAD_REQUEST = 400
    const val UNAUTHORIZED = 401
    const val FORBIDDEN = 403
    const val NOT_FOUND = 404
    const val METHOD_NOT_ALLOWED = 405
    const val TOO_MANY_REQUESTS = 429

    // Optional: You can group ranges using constants too
    val CLIENT_ERROR_RANGE = 402..409
    val SERVER_ERROR_RANGE = 500..599
}
