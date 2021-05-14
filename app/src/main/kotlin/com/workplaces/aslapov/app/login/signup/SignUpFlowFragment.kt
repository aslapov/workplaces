package com.workplaces.aslapov.app.login.signup

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.navigation.fragment.NavHostFragment
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import com.workplaces.aslapov.di.DI
import com.workplaces.aslapov.utils.extension.dispatchApplyWindowInsetsToChild

class SignUpFlowFragment : BaseFragment(R.layout.signup_flow_fragment) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<FrameLayout>(R.id.fragment_start_container_screens).dispatchApplyWindowInsetsToChild()

        val host: NavHostFragment = childFragmentManager
            .findFragmentById(R.id.sign_up_nav_host_fragment) as? NavHostFragment ?: return

        val navController = host.navController

        val graph = navController.navInflater.inflate(R.navigation.sign_up_graph)
        navController.graph = graph
    }
}
