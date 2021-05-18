package com.workplaces.aslapov.app

import io.kotest.core.listeners.TestListener
import io.kotest.core.test.Description
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult

class TestLiveDataListener(
    private val featureDescription: Description,
    private val beforeEachScenario: (() -> Unit)? = null,
    private val afterEachScenario: (() -> Unit)? = null
) : TestListener {

    override suspend fun beforeContainer(testCase: TestCase) {

    }

    override suspend fun afterContainer(testCase: TestCase, result: TestResult) {

    }
}
