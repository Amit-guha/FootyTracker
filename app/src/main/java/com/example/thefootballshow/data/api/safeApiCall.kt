package com.example.thefootballshow.data.api

import com.example.thefootballshow.ui.base.Resource
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

fun <T, R> safeApiCall(
    response: Response<T>,
    transform: ((T) -> R)? = null
): Resource<R> where T : Any, R : Any {
    return try {
        if (response.isSuccessful) {
            response.body()?.let { body ->
                val result = transform?.invoke(body) ?: body as R
                Resource.Success(result)
            } ?: Resource.Error(
                message = "Empty response body"
            )
        } else {
            val errorMessage = response.errorBody()?.string().orEmpty()
            Resource.Error(
                message = mapHttpCodeToMessage(response.code())
            )
        }
    } catch (e: SocketTimeoutException) {
        Resource.Error("Request timed out.")
    } catch (e: IOException) {
        Resource.Error("No internet connection.")
    } catch (e: Exception) {
        Resource.Error("Something went wrong.")
    }

}


fun mapHttpCodeToMessage(httpCode: Int): String = when (httpCode) {
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