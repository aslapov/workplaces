package com.workplaces.aslapov.app.profile

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.datepicker.MaterialDatePicker
import com.redmadrobot.extensions.lifecycle.Event
import com.redmadrobot.extensions.lifecycle.observe
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import com.workplaces.aslapov.data.util.helpers.convertToLocalDateViaInstant
import com.workplaces.aslapov.di.DI
import com.workplaces.aslapov.domain.util.dateTimeFormatter
import java.util.*

class ProfileEditFragment : BaseFragment(R.layout.profile_edit_fragment) {

    private val profileEditViewModel: ProfileEditViewModel by viewModels { viewModelFactory }

    private val firstname: EditText get() = requireView().findViewById(R.id.profile_edit_firstname)
    private val lastname: EditText get() = requireView().findViewById(R.id.profile_edit_lastname)
    private val nickname: EditText get() = requireView().findViewById(R.id.profile_edit_nickname)
    private val birthday: EditText get() = requireView().findViewById(R.id.profile_edit_birthday)
    private val toolbar: MaterialToolbar get() = requireView().findViewById(R.id.profile_edit_toolbar)
    private val save: Button get() = requireView().findViewById(R.id.profile_edit_save)
    private val progress: LinearLayout get() = requireView().findViewById(R.id.profile_progress_layout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewModelObservers()
        setEditTextWatchers()

        birthday.setOnClickListener { showDatePicker() }
        toolbar.setNavigationOnClickListener { profileEditViewModel.onBackClicked() }
        save.setOnClickListener { profileEditViewModel.onSaveClicked() }
    }

    private fun setViewModelObservers() {
        observe(profileEditViewModel.firstName) { setEditTextError(firstname, it) }
        observe(profileEditViewModel.lastName) { setEditTextError(lastname, it) }
        observe(profileEditViewModel.nickName) { setEditTextError(nickname, it) }
        observe(profileEditViewModel.birthDay) { setEditTextError(birthday, it) }
        observe(profileEditViewModel.isSaveButtonEnabled, ::onSaveButtonEnabledChanged)
        observe(profileEditViewModel.isLoading, ::onLoading)
        observe(profileEditViewModel.eventsQueue, ::onEvent)
    }

    private fun setEditTextWatchers() {
        firstname.doAfterTextChanged { profileEditViewModel.onFirstNameEntered(it.toString()) }
        lastname.doAfterTextChanged { profileEditViewModel.onLastNameEntered(it.toString()) }
        nickname.doAfterTextChanged { profileEditViewModel.onNickNameEntered(it.toString()) }
        birthday.doAfterTextChanged { profileEditViewModel.onBirthDayEntered(it.toString()) }
    }

    private fun onSaveButtonEnabledChanged(isEnabled: Boolean) {
        save.isEnabled = isEnabled
    }

    private fun onLoading(isLoading: Boolean) {
        progress.isVisible = isLoading
    }

    private fun setEditTextError(editText: EditText, fieldState: ProfileFieldState) {
        editText.error = if (fieldState.isValid) {
            null
        } else {
            fieldState.errorId?.let { getString(it) }
        }
    }

    private fun showDatePicker() {
        MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.profile_edit_calendar_title)
            .setSelection(Date().time)
            .build()
            .apply {
                addOnPositiveButtonClickListener {
                    birthday.text = Date(it).convertToLocalDateViaInstant()
                        .format(dateTimeFormatter)
                        .toEditable()
                }
            }
            .show(parentFragmentManager, "tag")
    }

    override fun onEvent(event: Event) {
        if (event is SetProfileFieldsEvent) {
            firstname.setText(event.firstName)
            lastname.setText(event.lastName)
            nickname.setText(event.nickName)
            birthday.setText(event.birthDay)
        } else {
            super.onEvent(event)
        }
    }
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
