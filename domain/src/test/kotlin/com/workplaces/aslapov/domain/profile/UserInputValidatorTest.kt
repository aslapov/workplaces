package com.workplaces.aslapov.domain.profile

import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

@ExperimentalKotest
class UserInputValidatorTest : FunSpec({

    context("Email input validations") {
        withData<ValueValidityPair>(
            { if (it.isValid) "${it.value} is valid" else "${it.value} is not valid" },
            "ololo@ololo.ru" toValidity true,
            "e@m.ru" toValidity true,
            "e@m.r" toValidity true,
            "123@123.123" toValidity true,
            "e@ru" toValidity false,
            "e@m." toValidity false,
            "123.12" toValidity false,
            "123@.12" toValidity false,
        ) { (email, isValid) ->
            isEmailValid(email) shouldBe isValid
        }
    }

    context("Password input validations") {
        withData<ValueValidityPair>(
            { if (it.isValid) "\"${it.value}\" is valid" else "\"${it.value}\" is not valid" },
            "123456aA" toValidity true,
            "oLoLo12345" toValidity true,
            "12345" toValidity false,
            "olololol" toValidity false,
            "Aa1" toValidity false,
            "ololo" toValidity false,
            "oLoLo" toValidity false,
            "Aa1@.123" toValidity false,
        ) { (password, isValid) ->
            isPasswordValid(password) shouldBe isValid
        }
    }

    context("First name input validations") {
        withData<ValueValidityPair>(
            { if (it.isValid) "\"${it.value}\" is valid" else "\"${it.value}\" is not valid" },
            "Egor" toValidity true,
            "EGOR" toValidity true,
            "egor" toValidity true,
            "Egor Egorov" toValidity false,
            "Egor123" toValidity false,
            "Egor!" toValidity false,
            "egor@m.ru" toValidity false,
            " egor " toValidity false,
        ) { (firstname, isValid) ->
            isFirstnameValid(firstname) shouldBe isValid
        }
    }

    context("Last name input validations") {
        withData<ValueValidityPair>(
            { if (it.isValid) "\"${it.value}\" is valid" else "\"${it.value}\" is not valid" },
            "Aslapov" toValidity true,
            "ASLAPOV" toValidity true,
            "aslapov" toValidity true,
            "Aslapov-Aslapov" toValidity false,
            "Aslapov Aslapov" toValidity false,
            "Aslapov!" toValidity false,
            "Aslapov12" toValidity false,
            " Aslapov " toValidity false,
        ) { (lastname, isValid) ->
            isLastnameValid(lastname) shouldBe isValid
        }
    }

    context("Nick name input validations") {
        withData<ValueValidityPair>(
            { if (it.isValid) "\"${it.value}\" is valid" else "\"${it.value}\" is not valid" },
            "egorius" toValidity true,
            "123456aA" toValidity true,
            "oLoLo12345" toValidity true,
            "@barabashka" toValidity false,
            "the best of the best" toValidity false,
            "Aa!" toValidity false,
            "ololo 123" toValidity false,
            "oLoLo_123" toValidity false,
        ) { (nickname, isValid) ->
            isNicknameValid(nickname) shouldBe isValid
        }
    }

    context("Birthday input validations") {
        withData<ValueValidityPair>(
            { if (it.isValid) "\"${it.value}\" is valid" else "\"${it.value}\" is not valid" },
            "1994-02-19" toValidity true,
            "1994/02/19" toValidity false,
            "19-02-1994" toValidity false,
            "19/02/1994" toValidity false,
            "19.02.1994" toValidity false,
            "19.02.94" toValidity false,
            "19 Feb 1994" toValidity false,
            "2100-01-01" toValidity false,
            "1899-12-31" toValidity false,
        ) { (birthday, isValid) ->
            isBirthdayValid(birthday) shouldBe isValid
        }
    }
})

data class ValueValidityPair(val value: String, val isValid: Boolean)
infix fun String.toValidity(that: Boolean): ValueValidityPair = ValueValidityPair(this, that)
