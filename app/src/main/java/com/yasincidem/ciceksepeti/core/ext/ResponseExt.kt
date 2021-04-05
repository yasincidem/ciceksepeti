package com.yasincidem.ciceksepeti.core.ext

import com.yasincidem.ciceksepeti.core.util.ResultOf
import com.yasincidem.ciceksepeti.network.Errors
import retrofit2.Response
import java.io.IOException

suspend fun <T> processResponse(request: suspend () -> Response<T>): ResultOf<T> {
    return try {
        val response = request()
        val responseCode = response.code()
        val responseMessage = response.message()
        if (response.isSuccessful) {
            ResultOf.success(response.body()!!)
        } else {
            ResultOf.failure(Errors.NetworkError(responseCode, responseMessage))
        }
    } catch (e: IOException) {
        ResultOf.failure(Errors.NetworkError(desc = e.message))
    }
}