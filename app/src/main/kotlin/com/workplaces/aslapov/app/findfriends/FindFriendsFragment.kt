package com.workplaces.aslapov.app.findfriends

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.redmadrobot.extensions.lifecycle.Event
import com.redmadrobot.extensions.lifecycle.observe
import com.redmadrobot.extensions.viewbinding.viewBinding
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import com.workplaces.aslapov.databinding.FindFriendsFragmentBinding
import com.workplaces.aslapov.di.DI

class FindFriendsFragment : BaseFragment(R.layout.find_friends_fragment), FriendsToAddController.AdapterCallbacks {

    companion object {
        private const val DRAWABLE_RIGHT = 2
    }

    private val viewModel: FindFriendsViewModel by viewModels { viewModelFactory }
    private val binding: FindFriendsFragmentBinding by viewBinding()

    private lateinit var friendsToAddController: FriendsToAddController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.appComponent.inject(this)
        friendsToAddController = FriendsToAddController(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.viewState, ::onStateChanged)
        observe(viewModel.eventsQueue, ::onEvent)

        binding.apply {
            findFriendsToolbar.setOnClickListener { viewModel.onBackClicked() }
            findFriendsResult.setController(friendsToAddController)

            findFriendsSearch.apply {
                requestFocus()
                doAfterTextChanged { viewModel.onSearchWordChanged(it.toString()) }

                setOnTouchListener { view, event ->
                    if (event.action == MotionEvent.ACTION_UP) {
                        if (event.rawX >= (view.right - findFriendsSearch.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
                            viewModel.onCancelClicked()
                            return@setOnTouchListener true
                        }
                    }
                    return@setOnTouchListener false
                }
            }
        }
    }

    override fun onEvent(event: Event) {
        if (event is SetEmptySearchWordEvent) {
            binding.findFriendsSearch.setText("")
        } else {
            super.onEvent(event)
        }
    }

    override fun onFriendAddClicked(user: UserInfo) {
        viewModel.onAddFriendClicked(user.id)
    }

    private fun onStateChanged(state: FindFriendsViewState) {
        binding.apply {
            findFriendsProgressLayout.root.isVisible = state.isLoading
            findFriendsResult.isVisible = !state.isLoading
        }
        friendsToAddController.setData(state.friends)
    }
}
