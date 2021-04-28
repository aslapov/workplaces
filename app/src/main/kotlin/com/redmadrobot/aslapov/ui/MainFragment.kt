package com.redmadrobot.aslapov.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.redmadrobot.aslapov.R
import com.redmadrobot.aslapov.ui.base.fragment.BaseFragment
import javax.inject.Inject

class MainFragment @Inject constructor() : BaseFragment(R.layout.fragment_main) {

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val mainActivity = activity as MainActivity

        mainViewModel.mainFormState.observe(
            mainActivity,
            {
                state ->
                when (state) {
                    is MainSuccess -> mainActivity.onLogout()
                }
            }
        )

        view?.findViewById<Button>(R.id.logout)?.setOnClickListener {
            mainViewModel.logout()
        }
        return view
    }
}
