package com.workplaces.aslapov.app.login.signup

import com.redmadrobot.extensions.lifecycle.Event
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.TestLiveDataListener
import com.workplaces.aslapov.app.base.viewmodel.Navigate
import com.workplaces.aslapov.domain.login.signup.SignUpUseCase
import com.workplaces.test.base.*
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlin.time.ExperimentalTime

@ExperimentalTime
class TestSignUpTwoViewModel : FreeSpec({
    listener(TestLiveDataListener())

    Feature("Sign Up step 2 testing") {
        lateinit var testDispatcher: TestCoroutineDispatcher
        lateinit var mockSignUpUseCase: SignUpUseCase
        lateinit var signUpStepTwoViewModel: SignUpStepTwoViewModel
        lateinit var signUpViewModel: SignUpViewModel

        beforeEachScenario {
            testDispatcher = TestCoroutineDispatcher()
            Dispatchers.setMain(testDispatcher)
        }

        afterEachScenario {
            testDispatcher.cleanupTestCoroutines()
            Dispatchers.resetMain()
        }

        Scenario("User entered first name") {
            val validFirstName = "egor"
            val notValidFirstName = "123"

            Given("Initialization Step 2 Sign-up ViewModel") {
                signUpStepTwoViewModel = SignUpStepTwoViewModel()
                signUpStepTwoViewModel.firstName.observeForever {}
            }

            When("First name entered") {
                signUpStepTwoViewModel.onFirstNameEntered(validFirstName)
            }

            Then("First name livedata triggered to be value of first name input value") {
                signUpStepTwoViewModel.firstName.value?.value shouldBe validFirstName
            }

            And("should be valid") {
                signUpStepTwoViewModel.firstName.value?.isValid shouldBe true
            }

            When("Not valid first name entered") {
                signUpStepTwoViewModel.onFirstNameEntered(notValidFirstName)
            }

            Then("First name livedata triggered to be value of email input value") {
                signUpStepTwoViewModel.firstName.value?.value shouldBe notValidFirstName
            }

            And("should be not valid") {
                signUpStepTwoViewModel.firstName.value?.isValid shouldBe false
            }

            And("show error message") {
                signUpStepTwoViewModel.firstName.value?.errorId shouldBe R.string.sign_up_firstname_error
            }
        }

        Scenario("User entered all user data on the form of step 2 of sign up") {

            val validFirstName = "egor"
            val validLastName = "egorov"
            val validNickName = "egorius"
            val validBirthDay = "1994-02-19"

            val notValidFirstName = "123"

            Given("Initialization Step 2 Sign-up ViewModel") {
                signUpStepTwoViewModel = SignUpStepTwoViewModel()
                signUpStepTwoViewModel.firstName.observeForever {}
                signUpStepTwoViewModel.lastName.observeForever {}
                signUpStepTwoViewModel.nickName.observeForever {}
                signUpStepTwoViewModel.birthDay.observeForever {}
                signUpStepTwoViewModel.isRegisterButtonEnabled.observeForever {}
            }

            When("User entered all valid user data on the form") {
                signUpStepTwoViewModel.onFirstNameEntered(validFirstName)
                signUpStepTwoViewModel.onLastNameEntered(validLastName)
                signUpStepTwoViewModel.onNickNameEntered(validNickName)
                signUpStepTwoViewModel.onBirthDayEntered(validBirthDay)
            }

            Then("Register button is enabled") {
                signUpStepTwoViewModel.isRegisterButtonEnabled.value shouldBe true
            }

            When("User entered all user data on the form and first name field is not valid") {
                signUpStepTwoViewModel.onFirstNameEntered(notValidFirstName)
                signUpStepTwoViewModel.onLastNameEntered(validLastName)
                signUpStepTwoViewModel.onNickNameEntered(validNickName)
                signUpStepTwoViewModel.onBirthDayEntered(validBirthDay)
            }

            Then("Register button is not enabled") {
                signUpStepTwoViewModel.isRegisterButtonEnabled.value shouldBe false
            }
        }

        Scenario("User clicked register button") {
            lateinit var incomingEvent: Event
            val signUpToWelcomeActionId = SignUpStepTwoFragmentDirections.signUpToWelcomeAction()

            Given("Initialization Sign up ViewModel") {
                mockSignUpUseCase = mockk(relaxed = true)
                signUpViewModel = SignUpViewModel(mockSignUpUseCase)
                signUpViewModel.eventsQueue.observeForever {
                    incomingEvent = it
                }
            }

            When("All user data is valid and register button clicked") {
                signUpViewModel.onGoNextClicked("e@m.ru", "123456aA")
                signUpViewModel.onSignUpClicked("egor", "egorov", "egorius", "1994-02-19")
                testDispatcher.advanceUntilIdle()
            }

            Then("Income event of navigating to welcome screen") {
                val expectedEvent = Navigate(signUpToWelcomeActionId)
                incomingEvent shouldBe expectedEvent
            }
        }
    }
})
