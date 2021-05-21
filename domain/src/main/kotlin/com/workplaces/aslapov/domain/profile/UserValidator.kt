package com.workplaces.aslapov.domain.profile

import java.util.regex.Pattern

fun isEmailValid(email: String): Boolean {
    val emailPattern = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
        "\\@" +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
        "(" +
        "\\." +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
        ")+"
    val pattern = Pattern.compile(emailPattern)
    return pattern.matcher(email).matches()
}

fun isPasswordValid(password: String): Boolean {
    val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{8,}"
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
