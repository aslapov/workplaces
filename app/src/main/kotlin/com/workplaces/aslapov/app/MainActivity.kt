package com.workplaces.aslapov.app

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.FragmentFactory
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
    lateinit var fragmentFactory: FragmentFactory

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        DI.appComponent.inject(this)
        setFragmentFactory()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<FrameLayout>(R.id.activity_start_container_screens).dispatchApplyWindowInsetsToChild()
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as? NavHostFragment ?: return

        host.findNavController().let {
            bottomNavigation.setupWithNavController(it)
        }

        val navController = host.navController

        setNavControllerGraph(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val bottomNavigationGoneCondition = destination.id == R.id.login_dest ||
                destination.id == R.id.sign_in_dest ||
                destination.id == R.id.sign_up_first_dest ||
                destination.id == R.id.sign_up_second_dest ||
                destination.id == R.id.welcome_dest

            if (bottomNavigationGoneCondition) {
                bottomNavigation.visibility = View.GONE
            } else {
                bottomNavigation.visibility = View.VISIBLE
            }
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

    private fun setFragmentFactory() {
        supportFragmentManager.fragmentFactory = fragmentFactory
    }
}
