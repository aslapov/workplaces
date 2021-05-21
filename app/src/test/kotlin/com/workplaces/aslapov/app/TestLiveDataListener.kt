package com.workplaces.aslapov.app

import io.kotest.core.listeners.TestListener
import io.kotest.core.spec.Spec

class TestLiveDataListener : TestListener {

    override suspend fun beforeSpec(spec: Spec) {
        TestLiveDataExecutionController.enableTestMode()
    }

    override suspend fun afterSpec(spec: Spec) {
        TestLiveDataExecutionController.disableTestMode()
    }
}
