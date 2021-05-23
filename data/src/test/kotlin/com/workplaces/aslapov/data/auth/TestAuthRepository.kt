package com.workplaces.aslapov.data.auth

import com.workplaces.aslapov.data.auth.localstore.TokenStore
import com.workplaces.aslapov.data.auth.localstore.TokenStoreStub
import com.workplaces.aslapov.data.auth.network.AuthApi
import com.workplaces.aslapov.domain.login.AuthRepository
import com.workplaces.test.base.*
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class TestAuthRepository : FreeSpec({
    Feature("Logout") {
        lateinit var mockAuthApi: AuthApi
        lateinit var mockTokenSource: TokenStore
        lateinit var authRepository: AuthRepository

        Scenario("Logout") {

            Given("Initialization auth repository") {
                mockAuthApi = mockk(relaxed = true)
                mockTokenSource = TokenStoreStub()
                mockTokenSource.setTokens("accessToken", "refreshToken")
                authRepository = AuthRepositoryImpl(mockAuthApi, mockTokenSource)
            }

            When("Logout is happened") {
                authRepository.logout()
            }

            Then("Tokens in token store should be null") {
                authRepository.accessToken shouldBe null
                authRepository.refreshToken shouldBe null
            }

            And("Logout event should be emitted") {
                val logoutFlowValue = authRepository.logoutEvent.value
                logoutFlowValue shouldBe true
            }
        }
    }
})
