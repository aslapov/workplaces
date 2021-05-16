package com.workplaces.aslapov.app.login.welcome

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.viewModels
import com.redmadrobot.extensions.lifecycle.observe
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import com.workplaces.aslapov.di.DI

class WelcomeFragment : BaseFragment(R.layout.welcome_fragment) {

    private val welcomeViewModel: WelcomeViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(welcomeViewModel.eventsQueue, ::onEvent)
        view.findViewById<Button>(R.id.welcome_go_to_feed).setOnClickListener { welcomeViewModel.onGoToFeedClicked() }
    }
}
