package com.workplaces.aslapov.app.feed

import android.os.Bundle
import android.view.View
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import javax.inject.Inject

class FeedEmptyFragment @Inject constructor() : BaseFragment(R.layout.feed_empty_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}