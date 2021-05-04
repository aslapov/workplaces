package com.workplaces.aslapov.app

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.navigation.Navigation
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import javax.inject.Inject

class WelcomeFragment @Inject constructor() : BaseFragment(R.layout.welcome_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.goToFeed).setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.login_to_main_action)
        )
    }
}
