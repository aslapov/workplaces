package com.workplaces.aslapov.app.base.fragment

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.redmadrobot.extensions.lifecycle.Event
import com.workplaces.aslapov.app.base.viewmodel.*
import javax.inject.Inject

open class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    fun onEvent(event: Event) {
        when (event) {
            is Navigate -> findNavController().navigate(event.direction)
            is NavigateAction -> findNavController().navigate(event.action)
            is NavigateUp -> findNavController().navigateUp()
            is MessageEvent -> showMessage(getString(event.message))
            is ErrorMessageEvent -> showMessage(event.errorMessage)
        }
    }

    private fun showMessage(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }
}
