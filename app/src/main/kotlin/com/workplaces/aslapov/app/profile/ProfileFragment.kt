package com.workplaces.aslapov.app.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.redmadrobot.extensions.lifecycle.observe
import com.redmadrobot.extensions.viewbinding.viewBinding
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import com.workplaces.aslapov.databinding.ProfileFragmentBinding
import com.workplaces.aslapov.di.DI

class ProfileFragment : BaseFragment(R.layout.profile_fragment) {

    private val profileViewModel: ProfileViewModel by viewModels { viewModelFactory }

    private val binding: ProfileFragmentBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(profileViewModel.viewState, ::onStateChanged)
        observe(profileViewModel.eventsQueue, ::onEvent)

        binding.apply {
            profileEdit.setOnClickListener { profileViewModel.onEditClicked() }
            profileLogout.setOnClickListener { profileViewModel.onLogout() }
        }
    }

    private fun onStateChanged(state: ProfileViewState) {
        binding.apply {
            profileName.text = state.name
            profileNickname.text = state.nickName
            profileAge.text = state.age
        }
    }
}
