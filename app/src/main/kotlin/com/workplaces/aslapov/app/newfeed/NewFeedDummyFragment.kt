package com.workplaces.aslapov.app.newfeed

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.viewModels
import com.redmadrobot.extensions.lifecycle.observe
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import com.workplaces.aslapov.di.DI
import javax.inject.Inject

class NewFeedDummyFragment @Inject constructor() : BaseFragment(R.layout.new_feed_dummy_fragment) {

    private val newFeedDummyViewModel: NewFeedDummyViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.appComponent.inject(this)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(newFeedDummyViewModel.eventsQueue, ::onEvent)
        view.findViewById<Button>(R.id.logout).setOnClickListener {
            newFeedDummyViewModel.onLogout()
        }
    }
}
