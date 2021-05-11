package com.workplaces.aslapov.domain

import android.util.Patterns
import java.util.regex.Pattern

fun isEmailValid(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

fun isPasswordValid(password: String): Boolean {
    val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}"
    val pattern = Pattern.compile(passwordPattern)
    return pattern.matcher(password).matches()
}

fun isNicknameValid(nickname: String): Boolean {
    val nicknamePattern = "^[a-zA-Z0-9]+$"
    val pattern = Pattern.compile(nicknamePattern)
    return pattern.matcher(nickname).matches()
}

fun isFirstnameValid(firstname: String): Boolean {
    val firstnamePattern = "^[a-zA-Z]+$"
    val pattern = Pattern.compile(firstnamePattern)
    return pattern.matcher(firstname).matches()
}

fun isLastnameValid(lastname: String): Boolean {
    val lastnamePattern = "^[a-zA-Z]+\$"
    val pattern = Pattern.compile(lastnamePattern)
    return pattern.matcher(lastname).matches()
}

fun isBirthdayValid(birthday: String): Boolean {
    val birthdayPattern = "(19|20)\\d\\d-((0[1-9]|1[012])-(0[1-9]|[12]\\d)|(0[13-9]|1[012])-30|(0[13578]|1[02])-31)"
    val pattern = Pattern.compile(birthdayPattern)
    return pattern.matcher(birthday).matches()
}
