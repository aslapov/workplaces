package com.workplaces.aslapov.app

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

class DummyFragment @Inject constructor() : BaseFragment(R.layout.dummy_fragment) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val dummyViewModel: DummyViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(dummyViewModel.dummyFormState, ::renderState)
        view.findViewById<Button>(R.id.logout).setOnClickListener {
            dummyViewModel.onLogout()
        }
    }

    private fun renderState(state: DummyViewState) {
        when (state) {
            is DummySuccess -> {
                findNavController().navigate(R.id.login_dest)
            }
            is DummyError -> {
                Snackbar.make(requireView(), getString(state.errorStringId), Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
