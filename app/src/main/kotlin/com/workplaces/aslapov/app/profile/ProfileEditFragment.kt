package com.workplaces.aslapov.app.profile

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.datepicker.MaterialDatePicker
import com.redmadrobot.extensions.lifecycle.observe
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import com.workplaces.aslapov.di.DI
import com.workplaces.aslapov.domain.userBirthdayFormatter
import java.util.*
import javax.inject.Inject

class ProfileEditFragment @Inject constructor() : BaseFragment(R.layout.profile_edit_fragment) {
    private val profileEditViewModel: ProfileEditViewModel by viewModels { viewModelFactory }
    private lateinit var firstname: EditText
    private lateinit var lastname: EditText
    private lateinit var nickname: EditText
    private lateinit var birthday: EditText
    private lateinit var toolbar: MaterialToolbar
    private lateinit var save: Button
    private lateinit var spinner: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firstname = view.findViewById(R.id.profile_edit_firstname)
        lastname = view.findViewById(R.id.profile_edit_lastname)
        nickname = view.findViewById(R.id.profile_edit_nickname)
        birthday = view.findViewById(R.id.profile_edit_birthday)
        toolbar = view.findViewById(R.id.profile_edit_toolbar)
        save = view.findViewById(R.id.profile_edit_save)
        spinner = view.findViewById(R.id.profile_edit_spinner)
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.profile_edit_calendar_title)
            .setSelection(Date().time)
            .build()

        observe(profileEditViewModel.viewState, ::onStateChanged)
        observe(profileEditViewModel.eventsQueue, ::onEvent)

        firstname.doAfterTextChanged { profileEditViewModel.onFirstNameEntered(it.toString()) }
        lastname.doAfterTextChanged { profileEditViewModel.onLastNameEntered(it.toString()) }
        nickname.doAfterTextChanged { profileEditViewModel.onNickNameEntered(it.toString()) }
        birthday.doAfterTextChanged { profileEditViewModel.onBirthDayEntered(it.toString()) }
        birthday.setOnClickListener { datePicker.show(parentFragmentManager, "tag") }
        datePicker.addOnPositiveButtonClickListener {
            birthday.text = userBirthdayFormatter.format(Date(it)).toEditable()
        }
        toolbar.setNavigationOnClickListener { profileEditViewModel.onBackClicked() }
        save.setOnClickListener {
            profileEditViewModel.onSaveClicked(
                firstname = firstname.text.toString(),
                lastname = lastname.text.toString(),
                nickname = nickname.text.toString(),
                birthday = birthday.text.toString()
            )
        }
    }

    private fun onSaveButtonEnableChanged(isEnabled: Boolean) {
        save.isEnabled = isEnabled
    }

    private fun onLoading(isLoading: Boolean) {
        spinner.isVisible = isLoading
        firstname.isEnabled = !isLoading
        lastname.isEnabled = !isLoading
        nickname.isEnabled = !isLoading
        birthday.isEnabled = !isLoading
        toolbar.isEnabled = !isLoading
        save.isEnabled = !isLoading
    }

    private fun onStateChanged(state: ProfileEditViewState) {
        onLoading(state.isLoading)
        onSaveButtonEnableChanged(state.isSaveButtonEnabled)
        firstname.text = state.firstName.toEditable()
        lastname.text = state.lastName.toEditable()
        nickname.text = state.nickName.toEditable()
        birthday.text = state.birthDay.toEditable()
    }
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
