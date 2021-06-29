package com.workplaces.aslapov.app.profile

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.redmadrobot.extensions.lifecycle.Event
import com.redmadrobot.extensions.lifecycle.observe
import com.redmadrobot.extensions.viewbinding.viewBinding
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import com.workplaces.aslapov.data.util.helpers.convertToLocalDateViaInstant
import com.workplaces.aslapov.databinding.ProfileEditFragmentBinding
import com.workplaces.aslapov.di.DI
import com.workplaces.aslapov.domain.util.dateTimeFormatter
import java.util.*

class ProfileEditFragment : BaseFragment(R.layout.profile_edit_fragment) {

    private val profileEditViewModel: ProfileEditViewModel by viewModels { viewModelFactory }

    private val binding: ProfileEditFragmentBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewModelObservers()
        setEditTextWatchers()

        with(binding) {
            profileEditBirthday.setOnClickListener { showDatePicker() }
            profileEditToolbar.setNavigationOnClickListener { profileEditViewModel.onBackClicked() }
            profileEditSave.setOnClickListener { profileEditViewModel.onSaveClicked() }
        }
    }

    private fun setViewModelObservers() {
        observe(profileEditViewModel.firstName) { setEditTextError(binding.profileEditFirstname, it) }
        observe(profileEditViewModel.lastName) { setEditTextError(binding.profileEditLastname, it) }
        observe(profileEditViewModel.nickName) { setEditTextError(binding.profileEditNickname, it) }
        observe(profileEditViewModel.birthDay) { setEditTextError(binding.profileEditBirthday, it) }
        observe(profileEditViewModel.isSaveButtonEnabled, ::onSaveButtonEnabledChanged)
        observe(profileEditViewModel.isLoading, ::onLoading)
        observe(profileEditViewModel.eventsQueue, ::onEvent)
    }

    private fun setEditTextWatchers() {
        with(binding) {
            profileEditFirstname.doAfterTextChanged { profileEditViewModel.onFirstNameEntered(it.toString()) }
            profileEditLastname.doAfterTextChanged { profileEditViewModel.onLastNameEntered(it.toString()) }
            profileEditNickname.doAfterTextChanged { profileEditViewModel.onNickNameEntered(it.toString()) }
            profileEditBirthday.doAfterTextChanged { profileEditViewModel.onBirthDayEntered(it.toString()) }
        }
    }

    private fun onSaveButtonEnabledChanged(isEnabled: Boolean) {
        binding.profileEditSave.isEnabled = isEnabled
    }

    private fun onLoading(isLoading: Boolean) {
        binding.profileProgressLayout.root.isVisible = isLoading
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
                    binding.profileEditBirthday.text = Date(it).convertToLocalDateViaInstant()
                        .format(dateTimeFormatter)
                        .toEditable()
                }
            }
            .show(parentFragmentManager, "tag")
    }

    override fun onEvent(event: Event) {
        if (event is SetProfileFieldsEvent) {
            with(binding) {
                profileEditFirstname.setText(event.firstName)
                profileEditLastname.setText(event.lastName)
                profileEditNickname.setText(event.nickName)
                profileEditBirthday.setText(event.birthDay)
            }
        } else {
            super.onEvent(event)
        }
    }
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
