package com.workplaces.aslapov.app.newfeed

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.redmadrobot.extensions.lifecycle.observe
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import javax.inject.Inject

class NewFeedDummyFragment @Inject constructor() : BaseFragment(R.layout.new_feed_dummy_fragment) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val newFeedDummyViewModel: NewFeedDummyViewModel by viewModels { viewModelFactory }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(newFeedDummyViewModel.newFeedDummyFormState, ::renderState)
        view.findViewById<Button>(R.id.logout).setOnClickListener {
            newFeedDummyViewModel.onLogout()
        }
    }

    private fun renderState(state: NewFeedDummyViewState) {
        when (state) {
            is NewFeedDummySuccess -> {
                findNavController().navigate(R.id.logout_action)
            }
            is NewFeedDummyError -> {
                Snackbar.make(requireView(), getString(state.errorStringId), Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
