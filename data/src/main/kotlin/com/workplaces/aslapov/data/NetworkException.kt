package com.workplaces.aslapov.data

import java.io.IOException

class NetworkException(override val message: String, val code: ErrorCode) : IOException(message) {
    val parseMessage: String = getParsedMessage()
    private fun getParsedMessage(): String {
        return when (code) {
            ErrorCode.INVALID_CREDENTIALS -> "Email или пароль указаны неверно"
            ErrorCode.INVALID_TOKEN -> "Проблема с access или refresh токеном"
            ErrorCode.DUPLICATE_USER_ERROR -> "Пользователь с введенной почтой уже зарегистрирован"
            ErrorCode.BAD_FILE_EXTENSION_ERROR -> "Изображение имеет недопустимый формат"
            ErrorCode.EMAIL_VALIDATION_ERROR -> "Email имеет недопустимый формат"
            ErrorCode.FILE_NOT_FOUND_ERROR -> "Запрошенное изображение не найдено"
            ErrorCode.PASSWORD_VALIDATION_ERROR -> "Пароль имеет недопустимый формат"
            ErrorCode.TOO_BIG_FILE_ERROR -> "Загружаемое изображение больше 5 Мб"
            ErrorCode.SERIALIZATION_ERROR -> "Входные данные не соответсвуют модели"
            ErrorCode.GENERIC_ERROR -> message
            ErrorCode.UNKNOWN_ERROR -> "Что-то пошло не так. \n$message"
        }
    }
}
