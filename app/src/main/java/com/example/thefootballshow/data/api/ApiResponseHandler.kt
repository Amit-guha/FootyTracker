package com.example.thefootballshow.data.api

import com.example.thefootballshow.ui.base.UiState
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

fun <T, R> handleApiResponse(
    response: Response<T>,
    transform: ((T) -> R)? = null
): UiState<R> where T : Any, R : Any {
    return try {
        if (response.isSuccessful) {
            response.body()?.let { body ->
                val result = transform?.invoke(body) ?: body as R
                UiState.Success(result)
            } ?: UiState.Error(
                message = "Empty response body"
            )
        } else {
            val errorMessage = response.errorBody()?.string().orEmpty()
            UiState.Error(
                message = getErrorMessageFromHttpCode(response.code())
            )
        }
    } catch (e: SocketTimeoutException) {
        // Handle timeout error
        UiState.Error("Request timed out.")
    } catch (e: IOException) {
        // Handle no internet connection or general network error
        UiState.Error("No internet connection.")
    } catch (e: Exception) {
        // Handle any other exceptions
        UiState.Error("Something went wrong.")
    }

}


fun getErrorMessageFromHttpCode(httpCode: Int): String = when (httpCode) {
    HttpStatusCode.BAD_REQUEST -> "Bad Request"
    HttpStatusCode.UNAUTHORIZED -> "Unauthorized"
    HttpStatusCode.FORBIDDEN -> "Forbidden"
    HttpStatusCode.NOT_FOUND -> "Not Found"
    HttpStatusCode.METHOD_NOT_ALLOWED -> "Method Not Allowed"
    HttpStatusCode.TOO_MANY_REQUESTS -> "Too Many Requests"
    in HttpStatusCode.SERVER_ERROR_RANGE -> "Server Error"
    in HttpStatusCode.SERVER_ERROR_RANGE -> "Can't connect to the server"
    else -> "Something went wrong."
}


