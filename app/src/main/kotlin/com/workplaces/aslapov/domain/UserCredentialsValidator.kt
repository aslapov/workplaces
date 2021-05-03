package com.workplaces.aslapov.domain

import android.util.Patterns
import java.util.regex.Pattern

fun isEmailValid(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

fun isPasswordValid(password: String): Boolean {
    val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}"
    val pattern = Pattern.compile(passwordPattern)
    return pattern.matcher(password).matches()
}
