package com.workplaces.aslapov.domain.profile

import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

@ExperimentalKotest
class UserInputValidatorTest : FunSpec({

    context("Email input validations") {
        withData<EmailValidPair>(
            { if (it.isValid) "${it.email} is valid" else "${it.email} is not valid" },
            EmailValidPair("ololo@ololo.ru", true),
            EmailValidPair("e@m.ru", true),
            EmailValidPair("e@ru", false),
            EmailValidPair("e@m.r", true),
            EmailValidPair("e@m.", false),
            EmailValidPair("123@123.123", true),
            EmailValidPair("123.12", false),
            EmailValidPair("123@.12", false),
        ) { (email, isValid) ->
            isEmailValid(email) shouldBe isValid
        }
    }

    context("Password input validations") {
        withData<PasswordValidPair>(
            { if (it.isValid) "\"${it.password}\" is valid" else "\"${it.password}\" is not valid" },
            PasswordValidPair("123456aA", true),
            PasswordValidPair("oLoLo12345", true),
            PasswordValidPair("12345", false),
            PasswordValidPair("olololol", false),
            PasswordValidPair("Aa1", false),
            PasswordValidPair("ololo", false),
            PasswordValidPair("oLoLo", false),
            PasswordValidPair("Aa1@.123", false),
        ) { (password, isValid) ->
            isPasswordValid(password) shouldBe isValid
        }
    }

    context("First name input validations") {
        withData<FirstNameValidPair>(
            { if (it.isValid) "\"${it.firstname}\" is valid" else "\"${it.firstname}\" is not valid" },
            FirstNameValidPair("Egor", true),
            FirstNameValidPair("EGOR", true),
            FirstNameValidPair("egor", true),
            FirstNameValidPair("Egor Egorov", false),
            FirstNameValidPair("Egor123", false),
            FirstNameValidPair("Egor!", false),
            FirstNameValidPair("egor@m.ru", false),
            FirstNameValidPair(" egor ", false),
        ) { (firstname, isValid) ->
            isFirstnameValid(firstname) shouldBe isValid
        }
    }

    context("Last name input validations") {
        withData<LastNameValidPair>(
            { if (it.isValid) "\"${it.lastname}\" is valid" else "\"${it.lastname}\" is not valid" },
            LastNameValidPair("Aslapov", true),
            LastNameValidPair("ASLAPOV", true),
            LastNameValidPair("aslapov", true),
            LastNameValidPair("Aslapov-Aslapov", false),
            LastNameValidPair("Aslapov Aslapov", false),
            LastNameValidPair("Aslapov!", false),
            LastNameValidPair("Aslapov12", false),
            LastNameValidPair(" Aslapov ", false),
        ) { (lastname, isValid) ->
            isLastnameValid(lastname) shouldBe isValid
        }
    }

    context("Nick name input validations") {
        withData<NickNameValidPair>(
            { if (it.isValid) "\"${it.nickname}\" is valid" else "\"${it.nickname}\" is not valid" },
            NickNameValidPair("egorius", true),
            NickNameValidPair("123456aA", true),
            NickNameValidPair("oLoLo12345", true),
            NickNameValidPair("@barabashka", false),
            NickNameValidPair("the best of the best", false),
            NickNameValidPair("Aa!", false),
            NickNameValidPair("ololo 123", false),
            NickNameValidPair("oLoLo_123", false),
        ) { (nickname, isValid) ->
            isNicknameValid(nickname) shouldBe isValid
        }
    }

    context("Birthday input validations") {
        withData<BirthDayValidPair>(
            { if (it.isValid) "\"${it.birthday}\" is valid" else "\"${it.birthday}\" is not valid" },
            BirthDayValidPair("1994-02-19", true),
            BirthDayValidPair("1994/02/19", false),
            BirthDayValidPair("19-02-1994", false),
            BirthDayValidPair("19/02/1994", false),
            BirthDayValidPair("19.02.1994", false),
            BirthDayValidPair("19.02.94", false),
            BirthDayValidPair("19 Feb 1994", false),
            BirthDayValidPair("2100-01-01", false),
            BirthDayValidPair("1899-12-31", false),
        ) { (birthday, isValid) ->
            isBirthdayValid(birthday) shouldBe isValid
        }
    }
})

data class EmailValidPair(val email: String, val isValid: Boolean)
data class PasswordValidPair(val password: String, val isValid: Boolean)
data class FirstNameValidPair(val firstname: String, val isValid: Boolean)
data class LastNameValidPair(val lastname: String, val isValid: Boolean)
data class NickNameValidPair(val nickname: String, val isValid: Boolean)
data class BirthDayValidPair(val birthday: String, val isValid: Boolean)
