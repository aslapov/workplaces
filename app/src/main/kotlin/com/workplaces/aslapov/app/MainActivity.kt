package com.workplaces.aslapov.app

import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.activity.BaseActivity
import com.workplaces.aslapov.di.DI
import com.workplaces.aslapov.utils.extension.dispatchApplyWindowInsetsToChild
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val mainViewModel: MainViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        DI.appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<FrameLayout>(R.id.activity_start_container_screens).dispatchApplyWindowInsetsToChild()
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as? NavHostFragment ?: return

        host.findNavController().run {
            bottomNavigation.setupWithNavController(this)
        }

        val navController = host.navController

        setNavControllerGraph(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isBottomNavigationVisible = destination.id == R.id.feed_empty_dest ||
                destination.id == R.id.new_feed_dest ||
                destination.id == R.id.profile_dest

            bottomNavigation.isVisible = isBottomNavigationVisible
        }
    }

    private fun setNavControllerGraph(navController: NavController) {
        val graphResId = if (mainViewModel.isUserLoggedIn()) {
            R.navigation.main_graph
        } else {
            R.navigation.auth_graph
        }
        val graph = navController.navInflater.inflate(graphResId)
        navController.graph = graph
    }
}
