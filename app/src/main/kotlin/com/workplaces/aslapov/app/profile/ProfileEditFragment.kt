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
import com.workplaces.aslapov.data.util.convertToLocalDateViaInstant
import com.workplaces.aslapov.data.util.dateTimeFormatter
import com.workplaces.aslapov.di.DI
import java.util.*

class ProfileEditFragment : BaseFragment(R.layout.profile_edit_fragment) {

    private val profileEditViewModel: ProfileEditViewModel by viewModels { viewModelFactory }

    private val firstname: EditText get() = requireView().findViewById(R.id.profile_edit_firstname)
    private val lastname: EditText get() = requireView().findViewById(R.id.profile_edit_lastname)
    private val nickname: EditText get() = requireView().findViewById(R.id.profile_edit_nickname)
    private val birthday: EditText get() = requireView().findViewById(R.id.profile_edit_birthday)
    private val toolbar: MaterialToolbar get() = requireView().findViewById(R.id.profile_edit_toolbar)
    private val save: Button get() = requireView().findViewById(R.id.profile_edit_save)
    private val spinner: ProgressBar get() = requireView().findViewById(R.id.profile_edit_spinner)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        toolbar.setNavigationOnClickListener { profileEditViewModel.onBackClicked() }
        save.setOnClickListener { profileEditViewModel.onSaveClicked() }

        datePicker.addOnPositiveButtonClickListener {
            birthday.text = Date(it).convertToLocalDateViaInstant()
                .format(dateTimeFormatter)
                .toEditable()
        }
    }

    private fun onSaveButtonEnabledChanged(isEnabled: Boolean) {
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
        onSaveButtonEnabledChanged(state.isSaveButtonEnabled)
        firstname.setText(state.firstName.value)
        lastname.setText(state.lastName.value)
        nickname.setText(state.nickName.value)
        birthday.setText(state.birthDay.value)
    }
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
