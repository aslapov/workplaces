package com.workplaces.aslapov.data.util.extensions

import retrofit2.HttpException
import retrofit2.Response

fun <T> Response<T>.checkIsSuccessful(): Response<T> {
    if (isSuccessful) {
        return this
    } else {
        throw HttpException(this)
    }
}
