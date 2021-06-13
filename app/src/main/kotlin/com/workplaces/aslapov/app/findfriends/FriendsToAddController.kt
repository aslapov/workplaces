package com.workplaces.aslapov.app.findfriends

import com.airbnb.epoxy.TypedEpoxyController
import com.workplaces.aslapov.R

class FriendsToAddController constructor(
    private val callbacks: AdapterCallbacks,
) : TypedEpoxyController<List<UserInfo>>() {

    interface AdapterCallbacks {
        fun onFriendAddClicked(user: UserInfo)
    }

    override fun buildModels(users: List<UserInfo>) {
        users.forEach {
            userView {
                id(it.id)
                fullName(it.fullName)
                nickName(it.nickName)
                action(R.drawable.icon_add)
                onActionClickListener { _ -> this@FriendsToAddController.callbacks.onFriendAddClicked(it) }
            }
        }
    }
}
