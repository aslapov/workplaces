package com.workplaces.aslapov.app.profile

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.redmadrobot.extensions.lifecycle.observe
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import com.workplaces.aslapov.di.DI
import javax.inject.Inject

class ProfileFragment @Inject constructor() : BaseFragment(R.layout.profile_fragment) {

    private val profileViewModel: ProfileViewModel by viewModels { viewModelFactory }

    private lateinit var name: TextView
    private lateinit var nickname: TextView
    private lateinit var age: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.appComponent.inject(this)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileViewModel.initProfile()

        name = view.findViewById(R.id.profile_name)
        nickname = view.findViewById(R.id.profile_nickname)
        age = view.findViewById(R.id.profile_age)
        val edit = view.findViewById<ImageView>(R.id.profile_edit)

        observe(profileViewModel.state, ::onStateChanged)
        observe(profileViewModel.eventsQueue, ::onEvent)
        edit.setOnClickListener {
            profileViewModel.onEdit()
        }
    }

    private fun onStateChanged(state: ProfileViewState) {
        name.text = state.name
        nickname.text = state.nickName
        age.text = state.age
    }
}
