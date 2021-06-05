package com.workplaces.aslapov.app.profile

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.redmadrobot.extensions.lifecycle.observe
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import com.workplaces.aslapov.di.DI

class ProfileFragment : BaseFragment(R.layout.profile_fragment) {

    private val profileViewModel: ProfileViewModel by viewModels { viewModelFactory }

    private val name: TextView get() = requireView().findViewById(R.id.profile_name)
    private val nickname: TextView get() = requireView().findViewById(R.id.profile_nickname)
    private val age: TextView get() = requireView().findViewById(R.id.profile_age)
    private val progress: LinearLayout get() = requireView().findViewById(R.id.profile_progress_layout)
    private val edit: ImageView get() = requireView().findViewById(R.id.profile_edit)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(profileViewModel.viewState, ::onStateChanged)
        observe(profileViewModel.eventsQueue, ::onEvent)

        edit.setOnClickListener { profileViewModel.onEditClicked() }
        view.findViewById<ImageView>(R.id.profile_logout).setOnClickListener { profileViewModel.onLogout() }
    }

    private fun onStateChanged(state: ProfileViewState) {
        name.text = state.name
        nickname.text = state.nickName
        age.text = state.age
        progress.isVisible = state.isLoading
        edit.isEnabled = !state.isErrorState
    }
}
