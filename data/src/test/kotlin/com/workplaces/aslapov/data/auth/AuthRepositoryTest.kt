package com.workplaces.aslapov.data.auth

import com.workplaces.aslapov.base.test.*
import com.workplaces.aslapov.data.auth.localstore.TokenStore
import com.workplaces.aslapov.data.auth.localstore.StubTokenStore
import com.workplaces.aslapov.data.auth.network.AuthApi
import com.workplaces.aslapov.domain.login.AuthRepository
import com.workplaces.aslapov.domain.login.UserCredentials
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import retrofit2.Response

class AuthRepositoryTest : FreeSpec({

    Feature("Logout") {

        lateinit var testScope: TestCoroutineScope
        lateinit var mockAuthApi: AuthApi
        lateinit var mockTokenSource: TokenStore
        lateinit var authRepository: AuthRepository
        lateinit var logoutFlowValue: Unit

        beforeEachScenario {
            testScope = TestCoroutineScope()
        }

        afterEachScenario {
            testScope.cleanupTestCoroutines()
        }

        Scenario("Logout") {

            val accessToken = "accessToken"

            Given("Initialization auth repository") {
                mockAuthApi = mockk {
                    coEvery { logout("Bearer $accessToken") } returns Response.success(Unit)
                }

                mockTokenSource = StubTokenStore()
                mockTokenSource.setTokens(accessToken, "refreshToken")
                authRepository = AuthRepositoryImpl(mockAuthApi, mockTokenSource)

                testScope.launch {
                    authRepository.logoutFlow.collect {
                        logoutFlowValue = it
                    }
                }
            }

            When("Logout is happened") {
                authRepository.logout()
            }

            Then("Logout api method has been called") {
                coVerify {
                    mockAuthApi.logout("Bearer $accessToken")
                }
            }

            And("Tokens in token store should be null") {
                assertSoftly {
                    authRepository.accessToken.shouldBeNull()
                    authRepository.refreshToken.shouldBeNull()
                }
            }

            And("Logout event should be emitted") {
                logoutFlowValue.shouldBe(Unit)
            }
        }

        Scenario("Logout with failed api request") {

            val accessToken = "accessToken"

            Given("Initialization auth repository") {
                mockAuthApi = mockk {
                    coEvery { logout("Bearer $accessToken") } throws Exception()
                }

                mockTokenSource = StubTokenStore()
                mockTokenSource.setTokens(accessToken, "refreshToken")
                authRepository = AuthRepositoryImpl(mockAuthApi, mockTokenSource)

                testScope.launch {
                    authRepository.logoutFlow.collect {
                        logoutFlowValue = it
                    }
                }
            }

            When("Logout is happened") {
                var isExceptionThrown = false
                try {
                    authRepository.logout()
                } catch (e: Exception) {
                    isExceptionThrown = true
                }
                isExceptionThrown.shouldBeTrue()
            }

            Then("Logout api method has been called") {
                coVerify {
                    mockAuthApi.logout("Bearer $accessToken")
                }
            }

            And("Tokens in token store should be null") {
                assertSoftly {
                    authRepository.accessToken.shouldBeNull()
                    authRepository.refreshToken.shouldBeNull()
                }
            }

            And("Logout event should be emitted") {
                logoutFlowValue.shouldBe(Unit)
            }
        }

        Scenario("Logout with null access token") {

            val accessToken: String? = null

            Given("Initialization auth repository") {
                mockAuthApi = mockk {
                    coEvery { logout("Bearer $accessToken") } returns Response.success(Unit)
                }

                mockTokenSource = StubTokenStore()
                authRepository = AuthRepositoryImpl(mockAuthApi, mockTokenSource)

                testScope.launch {
                    authRepository.logoutFlow.collect {
                        logoutFlowValue = it
                    }
                }
            }

            When("Logout is happened") {
                var isExceptionThrown = false
                try {
                    authRepository.logout()
                } catch (e: IllegalArgumentException) {
                    isExceptionThrown = true
                }
                isExceptionThrown.shouldBeTrue()
            }

            And("Tokens in token store should be null") {
                assertSoftly {
                    authRepository.accessToken.shouldBeNull()
                    authRepository.refreshToken.shouldBeNull()
                }
            }

            And("Logout event should be emitted") {
                logoutFlowValue.shouldBe(Unit)
            }
        }
    }
})
