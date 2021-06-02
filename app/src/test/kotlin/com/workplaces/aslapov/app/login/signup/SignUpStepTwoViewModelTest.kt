package com.workplaces.aslapov.app.login.signup

import com.redmadrobot.extensions.lifecycle.Event
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.TestLiveDataListener
import com.workplaces.aslapov.app.base.viewmodel.MessageEvent
import com.workplaces.aslapov.app.base.viewmodel.Navigate
import com.workplaces.aslapov.base.test.*
import com.workplaces.aslapov.domain.login.UserCredentials
import com.workplaces.aslapov.domain.login.signup.SignUpException
import com.workplaces.aslapov.domain.login.signup.SignUpUseCase
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlin.time.ExperimentalTime

@ExperimentalTime
class SignUpStepTwoViewModelTest : FreeSpec({
    listener(TestLiveDataListener())

    Feature("Sign Up step 2") {

        lateinit var testDispatcher: TestCoroutineDispatcher
        lateinit var mockSignUpUseCase: SignUpUseCase
        lateinit var signUpStepTwoViewModel: SignUpStepTwoViewModel
        lateinit var signUpViewModel: SignUpViewModel

        val validEmail = "e@m.ru"
        val validPassword = "123456aA"
        val validFirstName = "egor"
        val validLastName = "egorov"
        val validNickName = "egorius"
        val validBirthDay = "1994-02-19"

        beforeEachScenario {
            testDispatcher = TestCoroutineDispatcher()
            Dispatchers.setMain(testDispatcher)
        }

        afterEachScenario {
            testDispatcher.cleanupTestCoroutines()
            Dispatchers.resetMain()
        }

        Scenario("User entered first name") {

            val notValidFirstName = "123"

            Given("Initialization Step 2 Sign-up ViewModel") {
                signUpStepTwoViewModel = SignUpStepTwoViewModel()
                signUpStepTwoViewModel.firstName.observeForever {}
            }

            When("Valid first name entered") {
                signUpStepTwoViewModel.onFirstNameEntered(validFirstName)
            }

            Then("First name field state is valid") {
                val validFirstNameFieldState = SignUpTwoFieldState(
                    value = validFirstName,
                    isValid = true,
                    errorId = null
                )
                signUpStepTwoViewModel.firstName.value
                    .shouldBe(validFirstNameFieldState)
            }

            When("Not valid first name entered") {
                signUpStepTwoViewModel.onFirstNameEntered(notValidFirstName)
            }

            Then("First name field state is not valid state with errorId") {
                val notValidFirstNameFieldState = SignUpTwoFieldState(
                    value = notValidFirstName,
                    isValid = false,
                    errorId = R.string.sign_up_firstname_error
                )
                signUpStepTwoViewModel.firstName.value
                    .shouldBe(notValidFirstNameFieldState)
            }
        }

        Scenario("User entered all user data on the form of sign up step 2") {

            val notValidFirstName = "123"

            Given("Initialization Step 2 Sign-up ViewModel") {
                signUpStepTwoViewModel = SignUpStepTwoViewModel()
                signUpStepTwoViewModel.firstName.observeForever {}
                signUpStepTwoViewModel.lastName.observeForever {}
                signUpStepTwoViewModel.nickName.observeForever {}
                signUpStepTwoViewModel.birthDay.observeForever {}
                signUpStepTwoViewModel.isRegisterButtonEnabled.observeForever {}
            }

            When("Valid first name entered") {
                signUpStepTwoViewModel.onFirstNameEntered(validFirstName)
            }

            Then("Register button is not enabled") {
                signUpStepTwoViewModel.isRegisterButtonEnabled.value
                    .shouldBe(false)
            }

            When("Valid last name entered") {
                signUpStepTwoViewModel.onLastNameEntered(validLastName)
            }

            Then("Register button is not enabled") {
                signUpStepTwoViewModel.isRegisterButtonEnabled.value
                    .shouldBe(false)
            }

            When("Valid nick name entered") {
                signUpStepTwoViewModel.onNickNameEntered(validNickName)
            }

            Then("Register button is not enabled") {
                signUpStepTwoViewModel.isRegisterButtonEnabled.value
                    .shouldBe(false)
            }

            When("Valid birthday entered") {
                signUpStepTwoViewModel.onBirthDayEntered(validBirthDay)
            }

            Then("Register button is enabled") {
                signUpStepTwoViewModel.isRegisterButtonEnabled.value
                    .shouldBe(true)
            }

            When("Not valid first name entered") {
                signUpStepTwoViewModel.onFirstNameEntered(notValidFirstName)
            }

            Then("Register button is not enabled") {
                signUpStepTwoViewModel.isRegisterButtonEnabled.value
                    .shouldBe(false)
            }
        }

        Scenario("User clicked register button") {

            var incomingEvent: Event? = null
            val signUpToWelcomeAction = SignUpStepTwoFragmentDirections.signUpToWelcomeAction()

            Given("Initialization Sign up ViewModel") {
                mockSignUpUseCase = mockk {
                    coEvery {
                        signUp(
                            userCredentials = UserCredentials(validEmail, validPassword),
                            firstName = validFirstName,
                            lastName = validLastName,
                            nickName = validNickName,
                            birthDay = validBirthDay,
                        )
                    } coAnswers { delay(1000) }
                }

                signUpViewModel = SignUpViewModel(mockSignUpUseCase)
                signUpViewModel.eventsQueue.observeForever { incomingEvent = it }
                signUpViewModel.isLoading.observeForever {}
            }

            When("All user data is valid on step 1") {
                signUpViewModel.onGoNextClicked(validEmail, validPassword)
            }

            And("All user data is valid on step 2 and register button clicked") {
                signUpViewModel.onSignUpClicked(validFirstName, validLastName, validNickName, validBirthDay)
            }

            Then("Loading has been shown") {
                signUpViewModel.isLoading.value
                    .shouldBe(true)
            }

            When("Success response received") {
                testDispatcher.advanceUntilIdle()
            }

            Then("Income event of navigating to welcome screen") {
                val expectedEvent = Navigate(signUpToWelcomeAction)
                incomingEvent.shouldBe(expectedEvent)
            }

            And("Loading has been removed") {
                signUpViewModel.isLoading.value
                    .shouldBe(false)
            }

            And("Sign up method in sign up use case has been called") {
                coVerify {
                    mockSignUpUseCase.signUp(
                        userCredentials = UserCredentials(validEmail, validPassword),
                        firstName = validFirstName,
                        lastName = validLastName,
                        nickName = validNickName,
                        birthDay = validBirthDay,
                    )
                }
            }
        }

        Scenario("User clicked register button but sign up fails!") {

            var incomingEvent: Event? = null
            val errorMessageId = 0

            Given("Initialization Sign up ViewModel") {
                mockSignUpUseCase = mockk {
                    coEvery {
                        signUp(
                            userCredentials = UserCredentials(validEmail, validPassword),
                            firstName = validFirstName,
                            lastName = validLastName,
                            nickName = validNickName,
                            birthDay = validBirthDay,
                        )
                    } coAnswers {
                        delay(1000)
                        throw SignUpException(errorMessageId)
                    }
                }

                signUpViewModel = SignUpViewModel(mockSignUpUseCase)
                signUpViewModel.eventsQueue.observeForever { incomingEvent = it }
                signUpViewModel.isLoading.observeForever {}
            }

            When("All user data is valid on step 1") {
                signUpViewModel.onGoNextClicked(validEmail, validPassword)
            }

            And("All user data is valid on step 2 and register button clicked") {
                signUpViewModel.onSignUpClicked(validFirstName, validLastName, validNickName, validBirthDay)
            }

            Then("Loading has been shown") {
                signUpViewModel.isLoading.value
                    .shouldBe(true)
            }

            When("Fail response received") {
                testDispatcher.advanceUntilIdle()
            }

            Then("Sign up exception is thrown") {
                val expectedEvent = MessageEvent(errorMessageId)
                incomingEvent.shouldBe(expectedEvent)
            }

            And("Loading has been removed") {
                signUpViewModel.isLoading.value
                    .shouldBe(false)
            }

            And("Sign up method in sign up use case has been called") {
                coVerify {
                    mockSignUpUseCase.signUp(
                        userCredentials = UserCredentials(validEmail, validPassword),
                        firstName = validFirstName,
                        lastName = validLastName,
                        nickName = validNickName,
                        birthDay = validBirthDay,
                    )
                }
            }
        }
    }
})
